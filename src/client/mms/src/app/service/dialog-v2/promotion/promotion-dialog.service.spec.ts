import { TestBed } from '@angular/core/testing';

import { PromotionDialogService } from './promotion-dialog.service';

describe('PromotionDialogService', () => {
  let service: PromotionDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PromotionDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
