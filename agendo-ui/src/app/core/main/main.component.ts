import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from '../topbar/topbar.component';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [CommonModule, TopbarComponent],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent {

  constructor() {
  }  
}
