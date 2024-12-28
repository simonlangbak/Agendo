import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog'
import { BoardService } from '../../services/board.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-task-dialog',
  standalone: true,
  imports: [CommonModule, DialogModule, ButtonModule, InputTextModule, ReactiveFormsModule],
  templateUrl: './add-task-dialog.component.html',
  styleUrl: './add-task-dialog.component.scss'
})
export class AddTaskDialogComponent {
  @Input({ required: true }) visible: boolean = false;
  @Input({ required: true }) columnId: number | undefined;

  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();

  isSaving = false;

  createTaskForm = this.formBuilder.group({
    name: ['', Validators.required]
  });

  constructor(
    private boardService: BoardService,
    private formBuilder: FormBuilder
  ) {
  }

  onSave() {
    if (this.createTaskForm.invalid) {
      return;
    }

    this.isSaving = true;
    const name = this.createTaskForm.controls.name.value!;
    const requestPromise: Promise<boolean | number> = this.boardService.addTaskToBoardColumn(this.columnId!, name);

    requestPromise.then(()=> {
      // Close dialog when a result is received
      this.isSaving = false;
      this.visible = false;
    });
  }

  onHide() {
    this.visibleChange.emit(false);
    this.createTaskForm.reset();
    this.isSaving = false;
  }
}
