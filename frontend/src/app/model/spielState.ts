import { Spieler } from './spieler';
import { Aktion } from './aktion';

export class SpielState {
  knownSpieler: Spieler[];
  knownGezogeneNummern: number[];
  knownAktionen: Aktion[];

  constructor(spieler: Spieler[], nummern: number[], aktionen: Aktion[]){
      this.knownSpieler = spieler;
      this.knownGezogeneNummern = nummern;
      this.knownAktionen = aktionen;
  }

}
