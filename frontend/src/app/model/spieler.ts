export class Spieler {
  id: string;
  name: string;
  spielfeld: number[][];
  spielId: string;

  constructor(name: string, spielId: string, spielfeld: number[][]) {
    this.name = name;
    this.spielId = spielId;
    this.spielfeld = spielfeld;
  }
}