import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GradeListComponent } from './grade-list/grade-list/grade-list.component';
import { UserFormComponent } from './user-form/user-form.component';
import { GradeFormComponent } from './grade-form/grade-form.component';

@NgModule({
  declarations: [
    AppComponent,
    GradeListComponent,
    UserFormComponent,
    GradeFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
