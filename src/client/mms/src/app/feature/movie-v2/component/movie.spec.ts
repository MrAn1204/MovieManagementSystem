import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieV2 } from './movie';

describe('MovieV2', () => {
  let component: MovieV2;
  let fixture: ComponentFixture<MovieV2>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieV2]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieV2);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
