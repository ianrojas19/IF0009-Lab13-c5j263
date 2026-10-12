import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Medicamento } from '../models/receta.models';

@Injectable({
  providedIn: 'root'
})
export class MedicamentoService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/v1/medicamentos';

  obtenerTodos(): Observable<Medicamento[]> {
    return this.http.get<Medicamento[]>(this.apiUrl);
  }
}
