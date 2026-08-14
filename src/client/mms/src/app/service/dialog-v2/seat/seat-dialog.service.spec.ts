import { TestBed } from '@angular/core/testing';

import { SeatDialogService } from './seat-dialog.service';

describe('SeatDialogService', () => {
  let service: SeatDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SeatDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
