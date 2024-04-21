import { TestBed } from '@angular/core/testing';

import { DataPriorityService } from './data-priority.service';

describe('DataPriorityService', () => {
  let service: DataPriorityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataPriorityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
