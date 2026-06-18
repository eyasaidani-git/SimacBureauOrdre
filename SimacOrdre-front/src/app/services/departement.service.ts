import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Departement {
  id: number;
  nom: string;
  description: string;
  code: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class DepartementService {

  private apiUrl = 'http://localhost:8081/api/departements';

  constructor(private http: HttpClient) {}

  getDepartements(): Observable<Departement[]> {
    return this.http.get<Departement[]>(this.apiUrl);
  }
}