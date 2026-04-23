import { TestBed } from '@angular/core/testing';

import { EntityDialogService } from './entity-dialog.service';

describe('EntityDialogService', () => {
  let service: EntityDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EntityDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
