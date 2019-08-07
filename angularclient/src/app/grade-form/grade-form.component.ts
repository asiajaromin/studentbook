import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GradeService} from "../service/grade.service";
import {Grade} from "../model/grade";

@Component({
  selector: 'app-grade-form',
  templateUrl: './grade-form.component.html',
  styleUrls: ['./grade-form.component.css']
})
export class GradeFormComponent implements OnInit {

  ngOnInit(): void {
  }

  grade: Grade = new Grade();
  submitted = false;

  constructor(private router: Router, private gradeService: GradeService) {
  }

  newGrade(): void {
    this.submitted = false;
    this.grade = new Grade();
  }

  save(){
    this.gradeService.save(this.grade)
      .subscribe(data => console.log(data), error => console.log(error));
      this.grade =new Grade();
      this.goToGradeList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  goToGradeList() {
    this.router.navigate(['/grades'])
  }

}
