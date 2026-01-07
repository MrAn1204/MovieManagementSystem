import { TestBed } from '@angular/core/testing';

import { TalentService } from './talent.service';

describe('MovieService', () => {
  let service: TalentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TalentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
