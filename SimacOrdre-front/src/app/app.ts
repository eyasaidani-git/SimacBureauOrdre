import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { Departement, DepartementService } from './services/departement.service';
import { Utilisateur, UtilisateurService } from './services/utilisateur.service';
import { Courrier, CourrierService, CreateCourrierRequest } from './services/courrier.service';
import { Affectation, AffectationService, CreateAffectationRequest } from './services/affectation.service';

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {

  departements: Departement[] = [];
  utilisateurs: Utilisateur[] = [];
  courriers: Courrier[] = [];
  affectations: Affectation[] = [];

  loadingDepartements = true;
  loadingUtilisateurs = true;
  loadingCourriers = true;
  loadingAffectations = true;

  erreurDepartements = '';
  erreurUtilisateurs = '';
  erreurCourriers = '';
  erreurAffectations = '';

  messageSucces = '';
  erreurCreation = '';

  messageAffectation = '';
  erreurAffectation = '';

  nouveauCourrier: CreateCourrierRequest = {
    objet: '',
    expediteur: '',
    destination: '',
    type: 'ENTRANT',
    priorite: 'NORMALE',
    dateReception: '',
    dateLimite: '',
    description: '',
    createurId: 0,
    departementId: 0
  };

  nouvelleAffectation: CreateAffectationRequest = {
    courrierId: 0,
    affecteParId: 0,
    affecteAUtilisateurId: 0,
    affecteADepartementId: 0,
    commentaire: ''
  };

  constructor(
    private departementService: DepartementService,
    private utilisateurService: UtilisateurService,
    private courrierService: CourrierService,
    private affectationService: AffectationService
  ) {}

  ngOnInit(): void {
    this.chargerDepartements();
    this.chargerUtilisateurs();
    this.chargerCourriers();
    this.chargerAffectations();
  }

  chargerDepartements(): void {
    this.departementService.getDepartements().subscribe({
      next: (data) => {
        this.departements = data;
        this.loadingDepartements = false;
      },
      error: (error) => {
        this.erreurDepartements = 'Erreur lors du chargement des départements.';
        this.loadingDepartements = false;
        console.error('Erreur API départements :', error);
      }
    });
  }

  chargerUtilisateurs(): void {
    this.utilisateurService.getUtilisateurs().subscribe({
      next: (data) => {
        this.utilisateurs = data;
        this.loadingUtilisateurs = false;

        const agent = this.utilisateurs.find(u => u.role === 'AGENT');
        if (agent) {
          this.nouveauCourrier.createurId = agent.id;
          this.nouvelleAffectation.affecteParId = agent.id;
        }
      },
      error: (error) => {
        this.erreurUtilisateurs = 'Erreur lors du chargement des utilisateurs.';
        this.loadingUtilisateurs = false;
        console.error('Erreur API utilisateurs :', error);
      }
    });
  }

  chargerCourriers(): void {
    this.courrierService.getCourriers().subscribe({
      next: (data) => {
        this.courriers = data;
        this.loadingCourriers = false;
      },
      error: (error) => {
        this.erreurCourriers = 'Erreur lors du chargement des courriers.';
        this.loadingCourriers = false;
        console.error('Erreur API courriers :', error);
      }
    });
  }

  chargerAffectations(): void {
    this.affectationService.getAffectations().subscribe({
      next: (data) => {
        this.affectations = data;
        this.loadingAffectations = false;
      },
      error: (error) => {
        this.erreurAffectations = 'Erreur lors du chargement des affectations.';
        this.loadingAffectations = false;
        console.error('Erreur API affectations :', error);
      }
    });
  }

  ajouterCourrier(): void {
    this.messageSucces = '';
    this.erreurCreation = '';

    if (
      !this.nouveauCourrier.objet ||
      !this.nouveauCourrier.expediteur ||
      !this.nouveauCourrier.type ||
      !this.nouveauCourrier.priorite ||
      !this.nouveauCourrier.dateReception ||
      !this.nouveauCourrier.dateLimite ||
      !this.nouveauCourrier.description ||
      !this.nouveauCourrier.createurId ||
      !this.nouveauCourrier.departementId
    ) {
      this.erreurCreation = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }

    this.courrierService.createCourrier(this.nouveauCourrier).subscribe({
      next: (courrierCree) => {
        this.messageSucces = 'Courrier créé avec succès : ' + courrierCree.numeroOrdre;

        this.nouveauCourrier = {
          objet: '',
          expediteur: '',
          destination: '',
          type: 'ENTRANT',
          priorite: 'NORMALE',
          dateReception: '',
          dateLimite: '',
          description: '',
          createurId: this.nouveauCourrier.createurId,
          departementId: 0
        };

        this.chargerCourriers();
      },
      error: (error) => {
        this.erreurCreation = 'Erreur lors de la création du courrier.';
        console.error('Erreur création courrier :', error);
      }
    });
  }

  affecterCourrier(): void {
    this.messageAffectation = '';
    this.erreurAffectation = '';

    if (
      !this.nouvelleAffectation.courrierId ||
      !this.nouvelleAffectation.affecteParId ||
      !this.nouvelleAffectation.affecteAUtilisateurId ||
      !this.nouvelleAffectation.affecteADepartementId
    ) {
      this.erreurAffectation = 'Veuillez choisir le courrier, l’agent, le responsable et le département.';
      return;
    }

    this.affectationService.createAffectation(this.nouvelleAffectation).subscribe({
      next: (affectationCreee) => {
        this.messageAffectation = 'Courrier affecté avec succès à : ' + affectationCreee.affecteAUtilisateurNomComplet;

        this.nouvelleAffectation = {
          courrierId: 0,
          affecteParId: this.nouvelleAffectation.affecteParId,
          affecteAUtilisateurId: 0,
          affecteADepartementId: 0,
          commentaire: ''
        };

        this.chargerAffectations();
        this.chargerCourriers();
      },
      error: (error) => {
        this.erreurAffectation = 'Erreur lors de l’affectation du courrier.';
        console.error('Erreur affectation courrier :', error);
      }
    });
  }
}