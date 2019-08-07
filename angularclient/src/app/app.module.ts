import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import {FormsModule} from "@angular/forms";
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { GradeListComponent} from "./grade-list/grade-list.component";
import { GradeFormComponent } from './grade-form/grade-form.component';
import {GradeService} from "./service/grade.service";

@NgModule({
  declarations: [
    AppComponent,
    GradeListComponent,
    GradeFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [GradeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
