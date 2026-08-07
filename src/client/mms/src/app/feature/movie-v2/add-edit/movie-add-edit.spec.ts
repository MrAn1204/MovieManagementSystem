import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieAddEdit } from './movie-add-edit';

describe('MovieAddEdit', () => {
  let component: MovieAddEdit;
  let fixture: ComponentFixture<MovieAddEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MovieAddEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieAddEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
