import { TestBed } from '@angular/core/testing';

import { DataStageService } from './data-stage.service';

describe('DataStageService', () => {
  let service: DataStageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataStageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
