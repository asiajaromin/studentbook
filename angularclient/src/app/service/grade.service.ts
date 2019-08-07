import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Grade } from '../model/grade';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class GradeService {

  constructor(private http: HttpClient) {
  }

  public findById(id: number){
    return this.http.get(`/server/grades/${id}`)
  }

  public findAll() {
    return this.http.get('/server/grades');
  }

  public save(grade: Grade) {
    return this.http.post<Grade>('/server/grades', grade);
  }

  public updategrade(id: number, value: any) {
    return this.http.put(`/server/grades/${id}`, value);
  }

  public deletegrade(id: number){
    return this.http.delete(`/server/grades/${id}`,{ responseType: 'text' })
  }
}
