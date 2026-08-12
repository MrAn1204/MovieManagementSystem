import { TestBed } from '@angular/core/testing';

import { MovieDialogService } from './movie-dialog.service';

describe('MovieDialogService', () => {
  let service: MovieDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
