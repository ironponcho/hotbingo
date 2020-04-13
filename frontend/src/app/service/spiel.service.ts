import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Spiel } from '../model/spiel';
import {Observable} from 'rxjs/Rx';
import { SpielSettings } from '../model/spielSettings';
import { Spieler } from '../model/spieler';
import { HostInput } from '../model/hostInput';

@Injectable()
export class SpielService {
  
  public host = 'http://hotbingo.net:8080';
  // public host = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  public createSpiel(spielSettings: SpielSettings) : Observable<Spiel> {
    return this.http.post<Spiel>(this.host + "/spiel", spielSettings);
  }

  public createSpieler(spielId: string, spieler: Spieler) {
    return this.http.post<Spieler>(this.host + "/spiel/" +spielId +"/spieler", spieler);
  }

  public postNummer(spielId: string, hostInput: HostInput): Observable<Spieler[]>  {
    return this.http.post<Spieler[]>(this.host + "/spiel/" +spielId+ "/nummer", hostInput);
  }

  public findSpiel(spielId : String): Observable<Spiel> {
    return this.http.get<Spiel>(this.host + "/spiel/" + spielId);
  }

  public selectRandomNummer(spielId : String): Observable<number> {
    return this.http.get<number>(this.host + "/spiel/" + spielId +'/randomNummer');
  }
  
  public getDefaultSpielSettings() : Observable<SpielSettings>{
    return this.http.get<SpielSettings>(this.host + "/spiel/defaultSettings");
  }

  public getGeneratedSpielfeld(spielId: string) : Observable<number[][]>{
    return this.http.get<number[][]>(this.host + "/spiel/" + spielId +'/generatedSpielfeld');
  }

  public findAllSpieler(spielId : String){
    return this.http.get<Spieler[]>(this.host + "/spiel/" +spielId + "/spieler");
  }

  public addRandomPlayer(spielId: string, hostSecret: string) {
    return this.http.post<Spieler>(this.host + "/spiel/" +spielId +"/spieler/auto", new HostInput(hostSecret, null));
  }
}
