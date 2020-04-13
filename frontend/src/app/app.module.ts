import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { SpielComponent } from './spiel/spiel.component';
import { SpielService } from './service/spiel.service';
import { StartseiteComponent } from './startseite/startseite.component';
import { Materials } from './material-module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { SpielBeitretenComponent } from './spiel-beitreten/spiel-beitreten.component';
import { SpielErstellenComponent } from './spiel-erstellen/spiel-erstellen.component';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    SpielComponent,
    StartseiteComponent,
    SpielBeitretenComponent,
    SpielErstellenComponent,
  ],
  imports: [
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    Materials,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [SpielService],
  bootstrap: [AppComponent]
})
export class AppModule { }
