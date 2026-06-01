import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  loginForm!: FormGroup;
  hidePassword = true;
  isLoading = false;
  languages = ["EN", "ES", "FR", "PT"];
  selectedLang = "";

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', Validators.required, Validators.email],
      password: ['', Validators.required]
    });

    this.selectedLang = this.languages[1];
  }

  onLogin():void {
    if(this.loginForm.invalid){
      this.loginForm.markAllAsTouched();
      return;
    }

    this.isLoading = true;
    this.authService.login(this.loginForm.value).subscribe({
      next: (response) =>{
        this.isLoading = false;
        if(response.status === 'success'){
           this.authService.saveToken(response.token);
           this.snackBar.open(response.message, 'Cerrar', { duration : 4000 });
           console.log('Traditional token recive: ', response.token);

        }
      },
      error: (err) => {
        this.isLoading = false;
        const errorMsg = err.error?.message || 'Error de conexion con el servidor.';
        this.snackBar.open(errorMsg, 'Cerrar', { duration: 5000 });
      }
    })
  }

  onSSOLogin(): void {
  }

}
