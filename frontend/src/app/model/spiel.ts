import { SpielSettings } from './spielSettings';
import { Spieler } from './spieler';
import { Aktion } from './aktion';

export class Spiel {
  id: string;
  name: string; 
  spieler: Spieler[];
  aktionen: Aktion[];
  gezogeneNummern: Array<number>;
  settings: SpielSettings; 
  hostSecret: string;
}
