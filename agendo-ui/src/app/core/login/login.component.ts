import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CardModule, ButtonModule, CheckboxModule, InputTextModule, PasswordModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  loginForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  isLoading: boolean = false;
  showBadCredenitalsError: boolean = false;
  showUnknownError: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  async login() {
    if (this.loginForm.valid) {
      this.isLoading = true;
      const username = this.loginForm.controls.username.value ?? '';
      const password = this.loginForm.controls.password.value ?? '';
      this.authService.login(username, password).then((result: boolean | number) => this.navigateToMainOnSuccess(result));
    }
  }

  private navigateToMainOnSuccess(result: boolean | number) {
    this.isLoading = false;
    if (result === true) {
      this.showBadCredenitalsError = false;
    } else if (result === 403) {
      // TODO: Add logging framework
      this.showBadCredenitalsError = true;
      this.showUnknownError = false;
    } else {
      this.showUnknownError = true;
      this.showBadCredenitalsError = false;
    }
  }
}
