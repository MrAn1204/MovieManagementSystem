import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieDetailV2 } from './movie-detail';

describe('MovieDetailV2', () => {
  let component: MovieDetailV2;
  let fixture: ComponentFixture<MovieDetailV2>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieDetailV2]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieDetailV2);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
