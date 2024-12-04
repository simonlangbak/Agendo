import { Component, computed, Signal } from '@angular/core';
import { BoardService } from '../../services/board.service';
import { BoardSelectorService } from '../../services/board-selector.service';
import { BoardDTO, TaskDTO } from '../../model/board';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { AddBoardDialogComponent } from '../add-board-dialog/add-board-dialog.component';
import { AddTaskDialogComponent } from "../add-task-dialog/add-task-dialog.component";
import { AvatarModule } from 'primeng/avatar';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-board-view',
  standalone: true,
  imports: [CommonModule, ButtonModule, AddBoardDialogComponent, AddTaskDialogComponent, AvatarModule, TooltipModule],
  templateUrl: './board-view.component.html',
  styleUrl: './board-view.component.scss'
})
export class BoardViewComponent {
  showAddBoardDialog: boolean = false;
  showAddTaskDialog: boolean = false;

  public readonly selectedBoard: Signal<BoardDTO | undefined> = computed(() => this.boardSelectorService.selectedBoard());
  
  /**
   * Finds the ID of the first column in the selected board
   */
  public readonly selectedBoardColumnId: Signal<number | undefined> = 
    computed(() => this.selectedBoard() !== undefined ? this.selectedBoard()?.columns[0].id : undefined, { equal: () => false });
  
  /**
   * Gets the tasks for the selected board
   */
  public readonly selectedBoardTasks: Signal<TaskDTO[] | undefined> = computed(() => {
    if (this.selectedBoard() !== undefined) {
      return this.boardService.tasks().get(this.selectedBoard()!.id);
    } else {
      return undefined;
    }  
  }, { equal: () => false })

  /**
   * Filters tasks in the board by the columns in the board
   */
  public readonly tasksByColumns: Signal<Map<number, TaskDTO[]>> = computed(() => {
    const data = new Map();

    const columnIds = this.selectedBoard()?.columns.map(c => c.id);
    if (!columnIds) {
      return data;
    }

    for (const columnId of columnIds) {
      const tasksInColumn = this.selectedBoardTasks()?.filter(t => t.boardColumnId == columnId);
      data.set(columnId, tasksInColumn);
    }
    console.log('Filtered tasks by column');
    return data;
  }, { equal: () => false });

  constructor(
    private boardService: BoardService,
    private boardSelectorService: BoardSelectorService
  ) {
  }

  /**
   * Opens the add board dialog
   */
  onCreateBoardClicked() {
    this.showAddBoardDialog = true;
  }

  /**
   * Opens the add task dialog
   */
  onCreateTaskClicked() {
    this.showAddTaskDialog = true;
  }
}
