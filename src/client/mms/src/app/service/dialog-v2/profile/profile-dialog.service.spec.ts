import { TestBed } from '@angular/core/testing';

import { ProfileDialogService } from './profile-dialog.service';

describe('ProfileDialogService', () => {
  let service: ProfileDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
