import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog'
import { BoardService } from '../../services/board.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-board-dialog',
  standalone: true,
  imports: [CommonModule, DialogModule, ButtonModule, InputTextModule, ReactiveFormsModule],
  templateUrl: './add-board-dialog.component.html',
  styleUrl: './add-board-dialog.component.scss'
})
export class AddBoardDialogComponent {
  @Input() visible: boolean = false;
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();

  isSaving = false;
  showBoardExistsWithSameNameError = false;

  createBoardForm = this.formBuilder.group({
    name: ['', Validators.required],
    description: ['', Validators.required]
  });

  constructor(
    private boardService: BoardService,
    private formBuilder: FormBuilder
  ) { }

  onSave() {
    if (this.createBoardForm.invalid) {
      return;
    }

    this.isSaving = true;
    const name = this.createBoardForm.controls.name.value!;
    const description = this.createBoardForm.controls.description.value!;
    const requestPromise: Promise<boolean | number> = this.boardService.addBoard(name, description);

    requestPromise.then((result: boolean | number)=> {
      this.handleSaveBoardRequestResult(result);
    });
  }

  private handleSaveBoardRequestResult(result: boolean | number) {
    this.isSaving = false;

    if (result === true) {
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
