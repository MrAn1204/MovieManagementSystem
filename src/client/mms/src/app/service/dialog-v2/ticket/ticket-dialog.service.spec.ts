import { TestBed } from '@angular/core/testing';

import { TicketDialogService } from './ticket-dialog.service';

describe('TicketDialogService', () => {
  let service: TicketDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
