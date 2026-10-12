export interface LoginRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  rol: string;
}

export interface Usuario {
  id?: number;
  username: string;
  nombreCompleto?: string;
  rol: string;
}
