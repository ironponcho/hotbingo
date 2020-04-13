import{Component, OnInit}from '@angular/core';
import {Spieler}from '../model/spieler';
import {Spiel}from '../model/spiel';
import {SpielService}from '../service/spiel.service';
import 'rxjs/Rx';
import { ActivatedRoute } from "@angular/router";
import { Subscription } from 'rxjs/Rx';
import { ToastrService } from 'ngx-toastr';
import { SpielState } from '../model/spielState';

@Component({
  selector: 'app-spielfeld-list',
  templateUrl: './spiel.component.html',
  styleUrls: ['./spiel.component.css']
})
export class SpielComponent implements OnInit {

  currentSpieler: Spieler;
  otherSpieler: Spieler[];
  spiel: Spiel;
  interval: any;
  spielId: string;
  hostSecret?: string;
  currentSpielerId?: string;
  moeglicheNummern: number[] = [];
  spielState: SpielState;
  private routeSub: Subscription;
  trackBySpielerId = (index, spieler) => spieler.id;

constructor(
  private spielService: SpielService,
  private route: ActivatedRoute,
  private toastr: ToastrService
  ) {}

  ngOnInit() {

    this.routeSub = this.route.params.subscribe(params => {
      this.spielId = params['id'];    
      this.refreshSpiel();
      this.interval = setInterval(() => {
          this.refreshSpiel();
      }, 3000);
    });

    this.route.queryParamMap.subscribe(paramMap => {
      this.currentSpielerId = paramMap.get("currentSpieler")
      this.hostSecret = paramMap.get("hostSecret")
    })
  };

  refreshSpiel() {
    this.spielService.findSpiel(this.spielId).subscribe(data => {
      this.spiel = data;
      if(this.moeglicheNummern.length==0){
        this.spielState = new SpielState(this.spiel.spieler, this.spiel.gezogeneNummern, this.spiel.aktionen);
        this.initMoeglicheNummern(); 
      }
      
      let cSpieler = this.spiel.spieler.filter(spieler => spieler.id == this.currentSpielerId);
      if( cSpieler.length == 1) this.currentSpieler = cSpieler[0];

      this.otherSpieler = this.spiel.spieler.filter(spieler => spieler.id != this.currentSpielerId);
      this.updateSpielStateNummern();
      this.updateSpielStateAktionen();
      this.updateSpielStateSpieler(); 

    });
  }

  initMoeglicheNummern() { 
    for(let i = this.spiel.settings.minNummer; i <= this.spiel.settings.maxNummer; i++){
      this.moeglicheNummern.push(i)
    }
  }

  determineCellColor(value: number) {
    return this.spiel.gezogeneNummern.includes(value) ? "#8dc176" : "	#DCDCDC";
  };

  addGezogeneNummer(nummer: number) {
    
    if( !this.hostSecret ){
      this.toastr.error('Nur der Spielleiter darf Nummern eingeben')
      return; 
    }

    this.spielService.postNummer(this.spielId, {hostSecret:this.hostSecret, gezogeneNummer:nummer}).subscribe(spieler => {
      this.spiel.gezogeneNummern.push(nummer);
      this.refreshSpiel(); 

      if(spieler.length == 0 ){
        this.toastr.info("Kein Spieler hat die " +nummer)
      }
      if(spieler.length == 1 ){
        this.toastr.success("Nur " +spieler[0].name +" hat die " +nummer)
      }

      if(spieler.length > 1 ){
        let spielerMitNummer = spieler.map(spieler => spieler.name);
        this.toastr.success(spielerMitNummer +" haben die " +nummer)
      }

    },
    err => {
      this.toastr.error(err.message)
    }
    );
  }

  isNummerAlreadyGezogen(nummer: number) : boolean {
    let result = this.spiel.gezogeneNummern.includes(nummer);
    return result; 
  }

  getTreffer(spieler: Spieler){
    
    let all: number[] = [];
    spieler.spielfeld.forEach(row => {
      row.forEach(col => {
        all.push(col)
      })
    });

    let treffer: number[] = [];
    all.forEach(number => {
      if(this.spiel.gezogeneNummern.includes(number)){
        treffer.push(number);
      }
    });
    return treffer; 
  }

  postRandomNummer() {
    this.spielService.selectRandomNummer(this.spielId).subscribe(nummer => {
      this.addGezogeneNummer(nummer);
    },
    err => {
      this.toastr.error(err.message)
    }
    );
  }

  addRandomPlayer() {

    if(!this.hostSecret) return;
    
    this.spielService.addRandomPlayer(this.spielId, this.hostSecret).subscribe(spieler => {
      this.refreshSpiel(); 
    },
    err => {
      this.toastr.error(err.message)
    }
    );
  }

  getEinladungsLink() {
    return this.spielService.host +"/spiel-beitreten?spielId="+this.spielId;
  }

  private updateSpielStateNummern() {

    if( this.spiel.gezogeneNummern.length == this.spielState.knownGezogeneNummern.length) return;  
    let updatedNummern: number[] = this.spiel.gezogeneNummern.filter(x => !this.spielState.knownGezogeneNummern.includes(x));
    updatedNummern.forEach(nummer => {
      console.log('Nummer ' +nummer +' wurde gezogen!');
      this.spielState.knownGezogeneNummern.push(nummer);
    });

  }

  private  updateSpielStateAktionen() {

    if( this.spiel.aktionen.length == this.spielState.knownAktionen.length) return;  
    this.spiel.aktionen.forEach(aktion => {       
      if(!this.spielState.knownAktionen.some(kAktion => kAktion.id === aktion.id)){
        this.toastr.warning('Die nächste gezogene Nummer löst das Ereignis ' +aktion.beschreibung +' aus');
        this.spielState.knownAktionen.push(aktion);
      }
    })  

  }

  private updateSpielStateSpieler() {
    if( this.spiel.spieler.length == this.spielState.knownSpieler.length) return;  
      this.spiel.spieler.forEach(spieler => {       
        if(!this.spielState.knownSpieler.some(kSpieler => kSpieler.id === spieler.id)){
          this.toastr.success(spieler.name +' ist beigetreten!');
          this.spielState.knownSpieler.push(spieler);
        }
      })  
  }

}
