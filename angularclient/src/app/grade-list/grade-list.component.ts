import { Component, OnInit } from '@angular/core';
import { Grade } from '../model/grade';
import { GradeService } from '../service/grade.service';
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-grade-list',
  templateUrl: './grade-list.component.html',
  styleUrls: ['./grade-list.component.css']
})
export class GradeListComponent implements OnInit {

  grades: Observable<Grade[]>;

  constructor(private gradeService: GradeService, private router:Router) { }

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
this.grades= this.gradeService.findAll();
  }

  deleteGrade(id: number){
this.gradeService.deletegrade(id)
  .subscribe(
    data => {
      console.log(data);
      this.reloadData();
    },
    error => console.log(error));
  }

}
