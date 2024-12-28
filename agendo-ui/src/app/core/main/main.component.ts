import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopbarComponent } from '../topbar/topbar.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [CommonModule, TopbarComponent, RouterOutlet],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
})
export class MainComponent {

  constructor() {
  }  
}
