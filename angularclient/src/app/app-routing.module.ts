import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {GradeListComponent} from "./grade-list/grade-list.component";
import {GradeFormComponent} from "./grade-form/grade-form.component";


const routes: Routes = [
  {path: 'grades', component: GradeListComponent},
  {path: 'addgrade', component: GradeFormComponent},
  {path: 'updategrade', component: GradeFormComponent},
  {path: "deletegrade", component: GradeListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
