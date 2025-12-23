import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MultiselectField } from './multiselect-field';

describe('Multiselect', () => {
  let component: MultiselectField;
  let fixture: ComponentFixture<MultiselectField>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MultiselectField]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MultiselectField);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
