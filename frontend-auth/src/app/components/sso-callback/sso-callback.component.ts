import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-sso-callback',
  templateUrl: './sso-callback.component.html',
  styleUrls: ['./sso-callback.component.scss']
})
export class SsoCallbackComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      const code = params.get('code');

      if (code){
        this.authService.validateSsoCode(code).subscribe({
          next: (response) => {
            if(response.status === 'success'){
              this.authService.saveToken(response.token);

              this.snackBar.open(response.message, 'Cerrar', { duration: 4000 });
              this.router.navigate(['/home']);
            }
          },
          error: (err) => {
            const errorMsg = err.error?.message || 'Error al validar sesion SSO.';
            this.snackBar.open(errorMsg, 'Cerrar', { duration: 4000 });
            this.router.navigate(['/login']);
          }
        });
      } else{
        this.snackBar.open('No se proporciono ningun codigo de autenticacion', 'Cerrar', { duration: 4000 });
        this.router.navigate(['/login']);
      }
    })
  }

}
