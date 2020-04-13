import { Component, OnInit } from '@angular/core';
import { SpielService } from '../service/spiel.service';
import { SpielSettings } from '../model/spielSettings';
import { Router } from '@angular/router';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-spiel-erstellen',
  templateUrl: './spiel-erstellen.component.html',
  styleUrls: ['./spiel-erstellen.component.css'],
  providers: [SpielService],
})
export class SpielErstellenComponent implements OnInit {

  constructor(private spielService: SpielService, private router: Router) { }

  spielSettingsForm = new FormGroup({
    maxNummer: new FormControl(''),
    minNummer: new FormControl(''),
    spielfeldElementsPerDimension: new FormControl(''),
    spielfeldDimensions: new FormControl(''),
    wahrscheinlichkeitAktionInProzent: new FormControl('')
  });


  ngOnInit(): void {
    this.spielService.getDefaultSpielSettings().subscribe(spielSettings => {
      this.spielSettingsForm.patchValue({maxNummer: spielSettings.maxNummer})
      this.spielSettingsForm.patchValue({minNummer: spielSettings.minNummer})
      this.spielSettingsForm.patchValue({spielfeldElementsPerDimension: spielSettings.spielfeldElementsPerDimension})
      this.spielSettingsForm.patchValue({spielfeldDimensions: spielSettings.spielfeldDimensions})
      this.spielSettingsForm.patchValue({wahrscheinlichkeitAktionInProzent: spielSettings.wahrscheinlichkeitAktionInProzent})
    }); 
  }

  submitForm(){
    
    this.spielService.createSpiel(this.spielSettingsForm.value).subscribe(spiel => {
      console.log(spiel.hostSecret)
      alert('Spiel successfully created: Bookmark the next page to be able to restore the session. You might lose your host-acces to this session if u close the site.')
      this.router.navigateByUrl('/spiel/'+spiel.id+"?hostSecret="+spiel.hostSecret);
    }); 


  }

}
