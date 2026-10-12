import { Usuario } from './auth.models';

export interface Medicamento {
  id: number;
  codigo: string;
  nombre: string;
  stock: number;
  precioUnitario: number;
}

export interface DetalleReceta {
  id?: number;
  medicamento: Medicamento;
  cantidad: number;
  dosisIndicada: string;
}

export interface RecetaMedica {
  id?: number;
  codigoReceta: string;
  pacienteNombre: string;
  medico?: Usuario;
  estado: string;
  fechaEmision: string;
  detalles: DetalleReceta[];
}

export interface DetalleCreacionPayload {
  medicamento: { id: number };
  cantidad: number;
  dosisIndicada: string;
}

export interface RecetaCreacionPayload {
  codigoReceta?: string;
  pacienteNombre: string;
  detalles: DetalleCreacionPayload[];
}
