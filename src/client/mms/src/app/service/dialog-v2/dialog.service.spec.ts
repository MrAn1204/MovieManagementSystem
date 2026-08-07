import { TestBed } from '@angular/core/testing';

import { DialogServiceV2 } from './dialog.service';

describe('DialogServiceV2', () => {
  let service: DialogServiceV2;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DialogServiceV2);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
