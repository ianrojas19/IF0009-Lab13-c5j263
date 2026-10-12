import { AbstractControl, ValidationErrors } from '@angular/forms';

/**
 * Validador Personalizado (positivoValidator):
 * Comprueba que el valor sea un número entero estrictamente mayor a 0.
 */
export function positivoValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;

  if (value === null || value === undefined || value === '') {
    return null; // El validador Validators.required se encarga de valores vacíos
  }

  const num = Number(value);

  // Debe ser número válido, entero y estrictamente mayor a 0
  if (!Number.isInteger(num) || num <= 0) {
    return { positivoInvalido: true };
  }

  return null;
}
