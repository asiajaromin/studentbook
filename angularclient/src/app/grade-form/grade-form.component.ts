import {Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GradeService} from "../service/grade.service";
import {Grade} from "../model/grade";

@Component({
  selector: 'app-grade-form',
  templateUrl: './grade-form.component.html',
  styleUrls: ['./grade-form.component.css']
})
export class GradeFormComponent {

  grade: Grade;

  constructor(private route: ActivatedRoute, private router: Router, private gradeService: GradeService) {
    this.grade = new Grade();
  }

  onSubmit() {
    this.gradeService.save(this.grade).subscribe(result => this.goToGradeList());
  }

  goToGradeList(){
    this.router.navigate(['/grades'])
  }

}
