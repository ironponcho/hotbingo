import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SpielComponent } from './spiel/spiel.component';
import { StartseiteComponent } from './startseite/startseite.component';
import { AppComponent } from './app.component';
import { SpielBeitretenComponent } from './spiel-beitreten/spiel-beitreten.component';
import { SpielErstellenComponent } from './spiel-erstellen/spiel-erstellen.component';

const routes: Routes = [
  { path: '', component: StartseiteComponent },
  { path: 'spiel/:id', component: SpielComponent },
  { path: 'spiel-erstellen', component: SpielErstellenComponent },
  { path: 'spiel-beitreten', component: SpielBeitretenComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  bootstrap: [AppComponent]
})
export class AppRoutingModule { }
