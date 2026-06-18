import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Affectation {
  id: number;
  courrierId: number;
  numeroOrdre: string;
  affecteParNomComplet: string;
  affecteAUtilisateurNomComplet: string;
  affecteADepartementNom: string;
  commentaire: string;
  active: boolean;
  createdAt: string;
}

export interface CreateAffectationRequest {
  courrierId: number;
  affecteParId: number;
  affecteAUtilisateurId: number;
  affecteADepartementId: number;
  commentaire: string;
}

@Injectable({
  providedIn: 'root'
})
export class AffectationService {

  private apiUrl = 'http://localhost:8081/api/affectations';

  constructor(private http: HttpClient) {}

  getAffectations(): Observable<Affectation[]> {
    return this.http.get<Affectation[]>(this.apiUrl);
  }

  createAffectation(request: CreateAffectationRequest): Observable<Affectation> {
    return this.http.post<Affectation>(this.apiUrl, request);
  }
}