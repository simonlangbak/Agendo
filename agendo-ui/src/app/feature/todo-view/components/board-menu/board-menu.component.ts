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

@Component({
  selector: 'app-board-menu',
  standalone: true,
  imports: [CommonModule, DividerModule, MenuModule, ButtonModule, AddBoardDialogComponent, DeleteBoardDialogComponent],
  templateUrl: './board-menu.component.html',
  styleUrl: './board-menu.component.css'
})
export class BoardMenuComponent {
  showAddBoardDialog: boolean = false;
  showDeleteBoardDialog: boolean = false;
  boardToDelete: BoardDTO | undefined;

  titleItem: BoardMenuItem = new BoardMenuItem(undefined, 'Boards', PrimeIcons.CLIPBOARD, this.getTitleOptions());
  menuItems: Signal<BoardMenuItem[]> = computed(() => {
    const boards: BoardDTO[] = this.boardService.boards();
    return boards.map((board: BoardDTO) => this.mapToBoardMenu(board));
  });

  constructor(
    private boardService: BoardService
  ) {
    boardService.updateBoardsWithLatestData();
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

  private mapToBoardMenu(board: BoardDTO): BoardMenuItem {
    return {
      id: board.id,
      label: board.name,
      icon: PrimeIcons.CLIPBOARD,
      optionItems: this.getBoardOptions(board)
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
