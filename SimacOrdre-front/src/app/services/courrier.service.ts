import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Courrier {
  id: number;
  numeroOrdre: string;
  numeroOrdreSeqId?: number;
  objet: string;
  expediteur: string;
  destination: string | null;
  type: string;
  statut: string;
  priorite: string;
  dateReception: string;
  dateLimite: string;
  description: string;
  archive: boolean;
  createurNomComplet?: string;
  departementNom?: string;
  createdAt: string;
}

export interface CreateCourrierRequest {
  objet: string;
  expediteur: string;
  destination: string;
  type: string;
  priorite: string;
  dateReception: string;
  dateLimite: string;
  description: string;
  createurId: number;
  departementId: number;
}

@Injectable({
  providedIn: 'root'
})
export class CourrierService {

  private apiUrl = 'http://localhost:8081/api/courriers';

  constructor(private http: HttpClient) {}

  getCourriers(): Observable<Courrier[]> {
    return this.http.get<Courrier[]>(this.apiUrl);
  }

  createCourrier(request: CreateCourrierRequest): Observable<Courrier> {
    return this.http.post<Courrier>(this.apiUrl, request);
  }
}