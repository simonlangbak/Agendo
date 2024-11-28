import { Component, computed, Signal } from '@angular/core';
import { BoardService } from '../../services/board.service';
import { BoardSelectorService } from '../../services/board-selector.service';
import { BoardDTO } from '../../model/board';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { AddBoardDialogComponent } from '../add-board-dialog/add-board-dialog.component';

@Component({
  selector: 'app-board-view',
  standalone: true,
  imports: [CommonModule, ButtonModule, AddBoardDialogComponent],
  templateUrl: './board-view.component.html',
  styleUrl: './board-view.component.scss'
})
export class BoardViewComponent {

  showAddBoardDialog: boolean = false;

  public readonly selectedBoard: Signal<BoardDTO | undefined> = computed(() => this.boardSelectorService.selectedBoard());

  constructor(
    private boardService: BoardService,
    private boardSelectorService: BoardSelectorService
  ) {
  }

  onCreateBoardClicked() {
    this.showAddBoardDialog = true;
  }

}
