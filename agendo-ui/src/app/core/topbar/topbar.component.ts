import { Component, signal, Signal } from '@angular/core';
import { AvatarModule } from 'primeng/avatar';
import { UserDTO } from '../model/user-dto';
import { UserService } from '../auth/user.service';
import { toSignal } from '@angular/core/rxjs-interop';
import { ButtonModule } from 'primeng/button';
import { AuthService } from '../auth/auth.service';
import { MenuModule } from 'primeng/menu';
import { MenuItem } from 'primeng/api';
import { SkeletonModule } from 'primeng/skeleton';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [AvatarModule, ButtonModule, MenuModule, SkeletonModule],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.scss'
})
export class TopbarComponent {
  userSignal: Signal<UserDTO | undefined> = signal(undefined);
  menuItems: MenuItem[] = this.createMenuItems();

  constructor(
    private authService: AuthService,
    private userService: UserService
  ) {
    this.userSignal = toSignal(userService.getUserDetails());
  }

  getAvatarLabel(): string {
    if (!this.userSignal() || !this.userSignal()?.username) {
      return '';
    }

    return this.userSignal()!.username.at(0)!.toUpperCase();
  }

  logout() {
    this.authService.logout();
  }

  createMenuItems(): MenuItem[] {
    return [
      {
        label: 'Profile',
        items: [
          {
            label: 'Settings',
            icon: 'pi pi-cog',
            command: () => alert('Settings menu is to be implemented')
          },
          {
            label: 'Logout',
            icon: 'pi pi-sign-out',
            command: () => this.logout()
          }
        ]
      }
    ] as [MenuItem];
  }
}
