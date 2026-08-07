import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEditContainer } from './add-edit-container';

describe('AddEditContainer', () => {
  let component: AddEditContainer;
  let fixture: ComponentFixture<AddEditContainer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddEditContainer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddEditContainer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
