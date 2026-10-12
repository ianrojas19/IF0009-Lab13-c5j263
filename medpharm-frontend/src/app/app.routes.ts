import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RecetasListComponent } from './components/recetas-list/recetas-list.component';
import { RecetaFormComponent } from './components/receta-form/receta-form.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'recetas', component: RecetasListComponent, canActivate: [authGuard] },
  { path: 'nueva-receta', component: RecetaFormComponent, canActivate: [authGuard] },
  { path: '', redirectTo: 'recetas', pathMatch: 'full' },
  { path: '**', redirectTo: 'recetas' }
];
