import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BoardService } from '../../services/board.service';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { BoardDTO } from '../../model/board';

@Component({
  selector: 'app-delete-board-dialog',
  standalone: true,
  imports: [DialogModule, ButtonModule, ReactiveFormsModule],
  templateUrl: './delete-board-dialog.component.html',
  styleUrl: './delete-board-dialog.component.scss'
})
export class DeleteBoardDialogComponent {
  @Input() boardToDelete: BoardDTO | undefined;
  @Input() visible: boolean = false;
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();

  isDeleting = false;
  showUnknownDeletionError = false;

  constructor(
    private boardService: BoardService
  ) {
  }

  onDelete() {
    if (this.boardToDelete === undefined) {
      return;
    }

    this.isDeleting = true;
    const requestPromise: Promise<boolean | number> = this.boardService.deleteBoard(this.boardToDelete.id);

    requestPromise.then((result: boolean | number)=> {
      this.handleDeleteBoardRequestResult(result);
    });
  }

  private handleDeleteBoardRequestResult(result: boolean | number) {
    this.isDeleting = false;

    if (result === true) {
      this.visible = false;
      this.showUnknownDeletionError = false;
    } else {
       this.showUnknownDeletionError = true;
    }
  }

  onHide() {
    this.visibleChange.emit(false);
    this.isDeleting = false;
    this.showUnknownDeletionError = false;
  }
}
