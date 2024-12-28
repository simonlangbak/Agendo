import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteBoardDialogComponent } from './delete-board-dialog.component';

describe('DeleteBoardDialogComponent', () => {
  let component: DeleteBoardDialogComponent;
  let fixture: ComponentFixture<DeleteBoardDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeleteBoardDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeleteBoardDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
