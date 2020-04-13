export class HostInput {
  hostSecret: string;
  gezogeneNummer?: number;
  
  constructor(hostSecret: string, gezogeneNummer: number){
    this.hostSecret = hostSecret; 
    this.gezogeneNummer = gezogeneNummer;  
  }

}