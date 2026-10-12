import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { RecetaService } from '../../services/receta.service';
import { MedicamentoService } from '../../services/medicamento.service';
import { Medicamento, RecetaCreacionPayload } from '../../models/receta.models';
import { positivoValidator } from '../../validators/positivo.validator';

@Component({
  selector: 'app-receta-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './receta-form.component.html',
  styleUrls: ['./receta-form.component.css']
})
export class RecetaFormComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly recetaService = inject(RecetaService);
  private readonly medicamentoService = inject(MedicamentoService);
  private readonly router = inject(Router);

  readonly medicamentos = signal<Medicamento[]>([]);
  readonly guardando = signal<boolean>(false);
  readonly errorMensaje = signal<string | null>(null);

  // Formulario Reactivo Anidado con FormGroup y FormArray
  readonly recetaForm: FormGroup = this.fb.group({
    pacienteNombre: ['', [Validators.required, Validators.minLength(5)]],
    detalles: this.fb.array([])
  });

  get detalles(): FormArray {
    return this.recetaForm.get('detalles') as FormArray;
  }

  ngOnInit(): void {
    this.cargarMedicamentos();
    this.agregarRenglon(); // Al menos un medicamento inicial
  }

  cargarMedicamentos(): void {
    this.medicamentoService.obtenerTodos().subscribe({
      next: (catalogo) => this.medicamentos.set(catalogo),
      error: (err) => {
        this.errorMensaje.set('No se pudo cargar el catálogo de medicamentos: ' + (err.error || err.message));
      }
    });
  }

  crearRenglonGroup(): FormGroup {
    return this.fb.group({
      medicamentoId: ['', [Validators.required]],
      cantidad: [1, [Validators.required, positivoValidator]],
      dosisIndicada: ['', [Validators.required]]
    });
  }

  agregarRenglon(): void {
    this.detalles.push(this.crearRenglonGroup());
  }

  eliminarRenglon(index: number): void {
    if (this.detalles.length > 1) {
      this.detalles.removeAt(index);
    }
  }

  onSubmit(): void {
    if (this.recetaForm.invalid) {
      this.recetaForm.markAllAsTouched();
      return;
    }

    if (this.detalles.length === 0) {
      this.errorMensaje.set('Debe prescribir al menos un medicamento.');
      return;
    }

    this.guardando.set(true);
    this.errorMensaje.set(null);

    const val = this.recetaForm.value;
    const anio = new Date().getFullYear();
    const codigoAleatorio = `REC-${anio}-${Math.floor(100 + Math.random() * 900)}`;

    const payload: RecetaCreacionPayload = {
      codigoReceta: codigoAleatorio,
      pacienteNombre: val.pacienteNombre.trim(),
      detalles: val.detalles.map((d: any) => ({
        medicamento: { id: Number(d.medicamentoId) },
        cantidad: Number(d.cantidad),
        dosisIndicada: d.dosisIndicada.trim()
      }))
    };

    this.recetaService.registrarReceta(payload).subscribe({
      next: () => {
        this.guardando.set(false);
        this.router.navigate(['/recetas']);
      },
      error: (err) => {
        this.guardando.set(false);
        this.errorMensaje.set(
          typeof err.error === 'string'
            ? err.error
            : err.error?.message || 'Error al emitir la receta médica.'
        );
      }
    });
  }
}
