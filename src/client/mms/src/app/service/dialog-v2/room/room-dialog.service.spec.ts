import { TestBed } from '@angular/core/testing';

import { RoomDialogService } from './room-dialog.service';

describe('RoomDialogService', () => {
  let service: RoomDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RoomDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
