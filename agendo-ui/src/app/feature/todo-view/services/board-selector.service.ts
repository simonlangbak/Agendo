import { effect, Injectable, Signal, signal, WritableSignal } from '@angular/core';
import { BoardService } from './board.service';
import { BoardDTO } from '../model/board';

/**
 * Responsible for setting the currently selected board, i.e, the one that should be showed to the user in details.
 * TODO: Consider to merge this service with the {@link BoardService}.
 */
@Injectable({
  providedIn: 'root'
})
export class BoardSelectorService {

  private readonly selectedBoardsSignal: WritableSignal<BoardDTO | undefined> = signal<BoardDTO | undefined>(undefined)
  /**
   * Holds an in-sync instance for all boards and their columns.
   */
  public readonly selectedBoard: Signal<BoardDTO | undefined> = this.selectedBoardsSignal.asReadonly();

  constructor(
    private boardService: BoardService
  ) {
    
    effect(() => {
      if (this.selectedBoard() === undefined) {
        return;
      }

      // Deselect board if it does not exist anymore
      const selectedBoardId = this.selectedBoard()!.id;
      const doesBoardStillExist = this.findBoardInSignal(selectedBoardId) !== undefined;
      if (!doesBoardStillExist) {
        this.selectedBoardsSignal.set(undefined);
        console.log('The selected board does not exist anymore. Deselecting it.', selectedBoardId);
      }
    }, { allowSignalWrites: true });
  }

  /**
   * Selects/deselects the board with the id if it exists. If not found, nothing happens.
   */
  public selectDeselectBoard(boardId: number | undefined) {
    // Deselect case
    if (!boardId) {
      this.selectedBoardsSignal.set(undefined);
      return;
    }

    // Select case
    const board = this.findBoardInSignal(boardId);
      if (board === undefined) {
        console.log('Board with id not found', boardId);
      } else {
        this.selectedBoardsSignal.set(board);
        // Fetch tasks for the board
        this.boardService.getTasksByBoard(board.id);
      }
  }

  private findBoardInSignal(boardId: number): BoardDTO | undefined {
    return this.boardService.boards().find(b => b.id === boardId);
  }
}
