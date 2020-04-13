import { Component, OnInit, SimpleChanges, SimpleChange } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { first } from 'rxjs-compat/operator/first';
import { SpielService } from '../service/spiel.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Spieler } from '../model/spieler';
import { ToastrService } from 'ngx-toastr';
import { ifStmt } from '@angular/compiler/src/output/output_ast';
import { Spiel } from '../model/spiel';

@Component({
  selector: 'app-spiel-beitreten',
  templateUrl: './spiel-beitreten.component.html',
  styleUrls: ['./spiel-beitreten.component.css'],
  providers: [SpielService],
})
export class SpielBeitretenComponent implements OnInit {

  isLinear = true;

  spiel: Spiel;
  spielerName: string;
  spielfeld: number[][];
  spielIdFormGroup: FormGroup;
  spielInvitation: string; 
  
  constructor(
    private _formBuilder: FormBuilder,
    private spielService: SpielService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe(paramMap => {
  
      this.spielInvitation = paramMap.get("spielId")
      this.spielIdFormGroup = this._formBuilder.group({
        spielId: [this.spielInvitation],
        spielerName: [''],
        spielfeld: [''],
      });

      this.checkIfSpielWithIdExists();

    })

  }

  ngOnChanges(changes: SimpleChanges) {
    const currentItem: SimpleChange = changes.item;
    console.log('prev value: ', currentItem.previousValue);
    console.log('got item: ', currentItem.currentValue);
  }
  
  enterName(){
    
    let spielerName = this.spielIdFormGroup.value["spielerName"];

    if(spielerName && spielerName.length<3) {
      this.toastr.error(spielerName +" ist kein gültiger Spielername.")
      this.spielerName = null; 
      return;
    }

    if(this.spielerName != spielerName){
      this.toastr.info("Herzlich Willkommen, " +spielerName +"!")
      this.spielerName = spielerName; 
    }

  }

  checkIfSpielWithIdExists() {
    let spielId = this.spielIdFormGroup.value["spielId"];
    if( spielId.length!=36 ) {
      this.spiel = null; 
      return
    }; 

    console.log('Checking for spiel of spielId ' +spielId)

    this.spielService.findSpiel(spielId).subscribe(spiel => {
      this.toastr.success("Das Spiel " + spielId + " wurde gefunden.")
      this.spiel = spiel;
    }, err => {
      this.toastr.error("Das Spiel " + spielId + " wurde nicht gefunden.")
      this.spiel = null;
    });
  }

  generateSpielfeld() {
    this.spielService.getGeneratedSpielfeld(this.spiel.id).subscribe(spielfeld => {
      this.toastr.info("Ein neues Spielfeld wurde generiert");
      this.spielfeld = spielfeld; 
    });
  }

  joinSpiel() {
    let spieler = new Spieler(this.spielerName, this.spiel.id, this.spielfeld)
    this.spielService.createSpieler(this.spiel.id, spieler).subscribe(s => {
      this.toastr.success("Das Spielfeld wurde erfolgreich angelegt");
      this.router.navigateByUrl('/spiel/' + this.spiel.id + "?currentSpieler=" + s.id);
    }, err => {
      this.toastr.error(err.message);
    });
  }


  determineCellColor(){
    return "#DCDCDC";
  }

}
