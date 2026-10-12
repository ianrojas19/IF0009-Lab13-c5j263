import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecetaMedica, RecetaCreacionPayload } from '../models/receta.models';

@Injectable({
  providedIn: 'root'
})
export class RecetaService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/v1/recetas';

  obtenerTodas(): Observable<RecetaMedica[]> {
    return this.http.get<RecetaMedica[]>(this.apiUrl);
  }

  obtenerPorEstado(estado: string): Observable<RecetaMedica[]> {
    return this.http.get<RecetaMedica[]>(`${this.apiUrl}/estado/${estado}`);
  }

  registrarReceta(payload: RecetaCreacionPayload): Observable<RecetaMedica> {
    return this.http.post<RecetaMedica>(this.apiUrl, payload);
  }

  cambiarEstado(id: number, nuevoEstado: string): Observable<RecetaMedica> {
    return this.http.patch<RecetaMedica>(`${this.apiUrl}/${id}/estado`, {
      estado: nuevoEstado
    });
  }
}
