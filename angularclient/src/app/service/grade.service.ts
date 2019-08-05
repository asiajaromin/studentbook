import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Grade } from '../model/grade';
import { Observable } from 'rxjs';

@Injectable()
export class GradeService {

  private gradesUrl: string;

  constructor(private http: HttpClient) {
    this.gradesUrl = 'http://localhost:8500/users';
  }

  public findById(id: number):Observable<Object>{
    return this.http.get(`${this.gradesUrl}/${id}`)
  }

  public findAll(): Observable<Grade[]> {
    return this.http.get<Grade[]>(this.gradesUrl);
  }

  public save(grade: Grade) {
    return this.http.post<Grade>(this.gradesUrl, grade);
  }

  public update(id: number, value: any) {
    return this.http.put(`${this.gradesUrl}/${id}`, value);
  }

  public delete(id: number):Observable<any>{
    return this.http.delete(`${this.gradesUrl}/${id}`,{ responseType: 'text' })
  }
}
