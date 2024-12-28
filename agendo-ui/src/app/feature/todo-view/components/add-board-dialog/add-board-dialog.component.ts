import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog'
import { BoardService } from '../../services/board.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BoardSelectorService } from '../../services/board-selector.service';
import { BoardDTO } from '../../model/board';

@Component({
  selector: 'app-add-board-dialog',
  standalone: true,
  imports: [CommonModule, DialogModule, ButtonModule, InputTextModule, ReactiveFormsModule],
  templateUrl: './add-board-dialog.component.html',
  styleUrl: './add-board-dialog.component.scss'
})
export class AddBoardDialogComponent {
  @Input({ required: true }) visible: boolean = false;
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();

  isSaving = false;
  showBoardExistsWithSameNameError = false;

  createBoardForm = this.formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required]
  });

  constructor(
    private boardService: BoardService,
    private boardSelectorService: BoardSelectorService,
    private formBuilder: FormBuilder
  ) { }

  onSave() {
    if (this.createBoardForm.invalid) {
      return;
    }

    this.isSaving = true;
    const name = this.createBoardForm.controls.name.value!;
    const description = this.createBoardForm.controls.description.value!;
    const requestPromise: Promise<BoardDTO | number> = this.boardService.addBoard(name, description);

    requestPromise.then((result: BoardDTO | number)=> {
      this.handleSaveBoardRequestResult(result);
    });
  }

  private handleSaveBoardRequestResult(result: BoardDTO | number) {
    this.isSaving = false;

    const board = result as BoardDTO;
    if (board.id !== undefined) {
      this.boardSelectorService.selectDeselectBoard(board.id)
      this.visible = false;

    } else {
       this.showBoardExistsWithSameNameError = true;
    }
  }
  
  onHide() {
    this.visibleChange.emit(false);
    this.createBoardForm.reset();
    this.isSaving = false;
    this.showBoardExistsWithSameNameError = false;
  }
}
