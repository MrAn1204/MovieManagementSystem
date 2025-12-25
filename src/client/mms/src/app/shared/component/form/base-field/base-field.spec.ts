import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseField } from './base-field';

describe('BaseField', () => {
  let component: BaseField;
  let fixture: ComponentFixture<BaseField>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BaseField]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BaseField);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
