import { Injectable, Signal, signal, WritableSignal } from '@angular/core';
import { BoardCreationDTO, BoardDTO, TaskCreationDTO, TaskDTO } from '../model/board';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { catchError, EMPTY, firstValueFrom, map, of } from 'rxjs';

/**
 * Used for all operations related to boards and columns, i.e., fetching, modifing and deleting instances.
 */
@Injectable({
  providedIn: 'root'
})
export class BoardService {

  private readonly boardsSignal: WritableSignal<BoardDTO[]> = signal<BoardDTO[]>([]);
  /**
   * Holds an in-sync instance for all boards and their columns.
   */
  public readonly boards: Signal<BoardDTO[]> = this.boardsSignal.asReadonly();

  private readonly tasksSignal: WritableSignal<Map<number, Set<TaskDTO>>> =
    signal<Map<number, Set<TaskDTO>>>(new Map(), { equal: () => false });
  /**
   * Holds an in-sync tasks for all boards. Key: boardId, value: task
   */
  public readonly tasks: Signal<Map<number, Set<TaskDTO>>> = this.tasksSignal.asReadonly();

  constructor(
    private httpClient: HttpClient
  ) {
  }

  /**
   * Fetches the latest board data from the backend and updates the board signal with the received value.
   */
  public updateBoardsWithLatestData(): void {
    this.httpClient.get<BoardDTO[]>('api/v1/board')
      .pipe(
        map((boards: BoardDTO[]) => this.boardsSignal.set(boards)),
        catchError((err: HttpErrorResponse) => {
          console.log('An error occurred while fetching boards', err);
          return EMPTY;
        })
      ).subscribe();
  }

  /**
   * Adds a board. If successful, the board is also added to the boards signal.
   */
  public addBoard(name: string, description: string): Promise<BoardDTO | number> {
    return firstValueFrom(
      this.httpClient.post<BoardDTO>('api/v1/board', { name, description } as BoardCreationDTO, { observe: 'response' })
        .pipe(
          map((boardResponse: HttpResponse<BoardDTO>) => {
            const board = boardResponse.body as BoardDTO;
            this.mergeBoardIntoSignal(board);
            return board;
          }),
          catchError((err: HttpErrorResponse) => {
            console.log('An error occurred for the request', err);
            return of(err.status);
          })
        )
    );
  }

  private mergeBoardIntoSignal(board: BoardDTO) {
    const boards = this.boardsSignal();
    const idx = boards.findIndex(b => b.id === board.id);
    if (idx === -1) { // Board does not already exist
      boards.push(board);
      console.log('Added board to signal', board);
    } else {
      boards[idx] = board;
      console.log('Updated board in signal', board);
    }
    this.boardsSignal.set([...boards]);
  }

  /**
   * Deletes a board by id. If successful, the board is also removed from the board signal.
   */
  public deleteBoard(boardId: number): Promise<boolean | number> {
    console.log('Deleting board...')
    return firstValueFrom(
      this.httpClient.delete<void>('api/v1/board', { params: { boardId }, observe: 'response' })
        .pipe(
          map(() => {
            this.removeBoardFromSignal(boardId);
            return true;
          }),
          catchError((err: HttpErrorResponse) => {
            console.log('An error occurred for the request', err);
            return of(err.status);
          })
        )
    );
  }

  private removeBoardFromSignal(boardId: number) {
    const boards = this.boardsSignal();
    const idx = boards.findIndex(b => b.id === boardId);
    if (idx === -1) {
      console.log("Could not find board to delete by its id", boardId);
    } else {
      boards.splice(idx, 1);
      console.log('Removed board from signal', boardId);
    }
    this.boardsSignal.set([...boards]);
  }

  /**
   * Fetches all tasks by board id. If successful, the tasks are also merged into the tasks signal.
   */
  public getTasksByBoard(boardId: number): Promise<boolean | number> {
    return firstValueFrom(
      this.httpClient.get<TaskDTO[]>('api/v1/board/' + boardId + '/task')
        .pipe(
          map((tasks: TaskDTO[]) => {
            this.mergeTasksIntoSignal(tasks);
            return true;
          }),
          catchError((err: HttpErrorResponse) => {
            console.log('An error occurred for the request', err);
            return of(err.status);
          })
        )
    );
  }

  /**
   * Add task to board. If sucessful, the task is also merged into the task signal
   */
  public addTaskToBoardColumn(columnId: number, name: string): Promise<boolean | number> {
    return firstValueFrom(
      this.httpClient.post<TaskDTO>('api/v1/board/column/' + columnId + '/task', { name } as TaskCreationDTO, { observe: 'response' })
        .pipe(
          map((taskResponse: HttpResponse<TaskDTO>) => {
            const task = taskResponse.body as TaskDTO;
            this.mergeTasksIntoSignal([task]);
            return true;
          }),
          catchError((err: HttpErrorResponse) => {
            console.log('An error occurred for the request', err);
            return of(err.status);
          })
        )
    );
  }

  private mergeTasksIntoSignal(tasks: TaskDTO[]) {
    if (tasks.length === 0) {
      return;
    }

    // We assume all tasks are for the same board
    const boardId = tasks[0].boardId;
    const allTasksInSignal = this.tasksSignal();

    let tasksInBoard = allTasksInSignal.get(boardId);
    if (tasksInBoard === undefined) {
      tasksInBoard = new Set();
    }

    for (const task of tasks) {
      if (!tasksInBoard.has(task)) {
        tasksInBoard.add(task);
        console.log('Added board to signal', task);
      } else {
        tasksInBoard.add(task);
        console.log('Updated board in signal', task);
      }
    }

    allTasksInSignal.set(boardId, tasksInBoard);
    this.tasksSignal.set(allTasksInSignal);
    console.log('Updated tasks signal')
  }
}
