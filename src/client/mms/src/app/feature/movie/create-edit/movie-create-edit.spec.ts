import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieCreateEdit } from './movie-create-edit';

describe('MovieCreateEdit', () => {
  let component: MovieCreateEdit;
  let fixture: ComponentFixture<MovieCreateEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieCreateEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieCreateEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
