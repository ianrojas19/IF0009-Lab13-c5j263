import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { RecetaService } from '../../services/receta.service';
import { AuthService } from '../../services/auth.service';
import { RecetaMedica } from '../../models/receta.models';

@Component({
  selector: 'app-recetas-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './recetas-list.component.html',
  styleUrls: ['./recetas-list.component.css']
})
export class RecetasListComponent implements OnInit {
  private readonly recetaService = inject(RecetaService);
  readonly authService = inject(AuthService);

  // Reactividad con Signals
  readonly recetas = signal<RecetaMedica[]>([]);
  readonly filtroEstado = signal<string>('TODAS');
  readonly cargando = signal<boolean>(false);
  readonly mensaje = signal<{ tipo: 'success' | 'danger'; texto: string } | null>(null);

  // Computed signal para filtrado dinámico según la rúbrica
  readonly recetasFiltradas = computed(() => {
    const filtro = this.filtroEstado();
    const listado = this.recetas();
    if (filtro === 'TODAS') {
      return listado;
    }
    return listado.filter((r) => r.estado?.toUpperCase() === filtro);
  });

  ngOnInit(): void {
    this.cargarRecetas();
  }

  cargarRecetas(): void {
    this.cargando.set(true);
    this.recetaService.obtenerTodas().subscribe({
      next: (data) => {
        this.recetas.set(data);
        this.cargando.set(false);
      },
      error: (err) => {
        this.cargando.set(false);
        this.mensaje.set({
          tipo: 'danger',
          texto: 'Error al cargar las recetas: ' + (err.error || err.message)
        });
      }
    });
  }

  cambiarFiltro(estado: string): void {
    this.filtroEstado.set(estado);
  }

  cambiarEstado(recetaId: number | undefined, nuevoEstado: string): void {
    if (!recetaId) return;

    this.recetaService.cambiarEstado(recetaId, nuevoEstado).subscribe({
      next: (recetaActualizada) => {
        this.recetas.update((prev) =>
          prev.map((r) => (r.id === recetaId ? recetaActualizada : r))
        );
        this.mensaje.set({
          tipo: 'success',
          texto: `Receta #${recetaActualizada.codigoReceta} actualizada a estado ${nuevoEstado}.`
        });
        setTimeout(() => this.mensaje.set(null), 4000);
      },
      error: (err) => {
        this.mensaje.set({
          tipo: 'danger',
          texto: 'Error al cambiar estado: ' + (err.error || err.message)
        });
      }
    });
  }
}
