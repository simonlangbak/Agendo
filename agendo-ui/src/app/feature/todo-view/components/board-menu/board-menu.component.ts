import { CommonModule } from '@angular/common';
import { Component, computed, Signal } from '@angular/core';
import { MenuItem, PrimeIcons } from 'primeng/api';
import { DividerModule } from 'primeng/divider';
import { BoardMenuItem } from './board-menu-item';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';
import { AddBoardDialogComponent } from "../add-board-dialog/add-board-dialog.component";
import { BoardService } from '../../services/board.service';
import { BoardDTO } from '../../model/board';
import { DeleteBoardDialogComponent } from '../delete-board-dialog/delete-board-dialog.component';
import { BoardSelectorService } from '../../services/board-selector.service';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-board-menu',
  standalone: true,
  imports: [CommonModule, DividerModule, MenuModule, ButtonModule, AddBoardDialogComponent, DeleteBoardDialogComponent, TooltipModule],
  templateUrl: './board-menu.component.html',
  styleUrl: './board-menu.component.scss'
})
export class BoardMenuComponent {
  showAddBoardDialog: boolean = false;
  showDeleteBoardDialog: boolean = false;
  boardToDelete: BoardDTO | undefined;

  titleItem: BoardMenuItem = new BoardMenuItem(undefined, 'Dashboard', PrimeIcons.TH_LARGE, this.getTitleOptions());
  menuItems: Signal<BoardMenuItem[]> = computed(() => {
    const selectedBoard = this.boardSelectorService.selectedBoard();

    this.titleItem.isSelected = selectedBoard === undefined;
    
    const boards: BoardDTO[] = this.boardService.boards();
    return boards.map((board: BoardDTO) => this.mapToBoardMenu(board, selectedBoard));
  });

  constructor(
    private boardService: BoardService,
    private boardSelectorService: BoardSelectorService
  ) {
    boardService.updateBoardsWithLatestData();
  }

  public selectBoard(boardMenuItem: BoardMenuItem) {
    const boardId = boardMenuItem?.id;
    this.boardSelectorService.selectDeselectBoard(boardId);
  }

  private getTitleOptions(): MenuItem[] {
    return [
      {
        label: 'Options',
        items: [
          {
            label: 'Add board',
            icon: PrimeIcons.PLUS,
            command: () => this.showAddBoardDialog = true
          }
        ]
      }
    ]
  }

  private mapToBoardMenu(board: BoardDTO, selectedBoard: BoardDTO | undefined): BoardMenuItem {
    const isSelected = selectedBoard !== undefined && board.id === selectedBoard.id;
    return {
      id: board.id,
      label: board.name,
      icon: PrimeIcons.CLIPBOARD,
      optionItems: this.getBoardOptions(board),
      isSelected
    };
  }

  private getBoardOptions(board: BoardDTO): MenuItem[] {
    return [
      {
        label: 'Options',
        items: [
          {
            label: 'Delete board',
            icon: PrimeIcons.TRASH,
            command: () => {
              this.boardToDelete = board;
              this.showDeleteBoardDialog = true
            }
          }
        ]
      }
    ]
  }
}
