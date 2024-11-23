import { Component, computed, Signal } from '@angular/core';
import { BoardService } from '../../services/board.service';
import { BoardSelectorService } from '../../services/board-selector.service';
import { BoardDTO } from '../../model/board';

@Component({
  selector: 'app-board-view',
  standalone: true,
  imports: [],
  templateUrl: './board-view.component.html',
  styleUrl: './board-view.component.scss'
})
export class BoardViewComponent {

  public readonly selectedBoard: Signal<BoardDTO | undefined> = computed(() => this.boardSelectorService.selectedBoard());

  constructor(
    private boardService: BoardService,
    private boardSelectorService: BoardSelectorService
  ) {

  }
}
