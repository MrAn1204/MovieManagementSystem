import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieFilterV2 } from './movie-filter';

describe('MovieFilterV2', () => {
  let component: MovieFilterV2;
  let fixture: ComponentFixture<MovieFilterV2>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieFilterV2]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieFilterV2);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
