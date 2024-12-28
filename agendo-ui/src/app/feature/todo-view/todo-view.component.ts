import { Component } from '@angular/core';
import { BoardMenuComponent } from "./components/board-menu/board-menu.component";
import { BoardViewComponent } from "./components/board-view/board-view.component";

@Component({
  selector: 'app-todo-view',
  standalone: true,
  imports: [BoardMenuComponent, BoardViewComponent],
  templateUrl: './todo-view.component.html',
  styleUrl: './todo-view.component.scss'
})
export class TodoViewComponent {

}
