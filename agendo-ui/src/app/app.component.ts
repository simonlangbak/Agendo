import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AppConfigService } from './core/configuration/app-config.service';

@Component({
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'Agendo';

  constructor(
    private appConfigService: AppConfigService
  ) {
    async () => await appConfigService.loadAppConfig();
  }
}
