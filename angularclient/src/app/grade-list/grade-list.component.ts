import { Component, OnInit } from '@angular/core';
import { Grade } from '../model/grade';
import { GradeService } from '../service/grade.service';

@Component({
  selector: 'app-grade-list',
  templateUrl: './grade-list.component.html',
  styleUrls: ['./grade-list.component.css']
})
export class GradeListComponent implements OnInit {

  grades: Grade[];

  constructor(private gradeService: GradeService) { }

  ngOnInit() {
    this.gradeService.findAll().subscribe(data=>{
    this.grades =data;
    });
  }

}
