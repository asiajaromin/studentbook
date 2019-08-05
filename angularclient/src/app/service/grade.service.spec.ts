import { TestBed } from '@angular/core/testing';

import { GradeService } from './grade.service';

describe('GradeServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GradeService = TestBed.get(GradeService);
    expect(service).toBeTruthy();
  });
});
