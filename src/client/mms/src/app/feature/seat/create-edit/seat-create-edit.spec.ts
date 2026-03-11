import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeatCreateEdit } from './seat-create-edit';

describe('SeatCreateEdit', () => {
  let component: SeatCreateEdit;
  let fixture: ComponentFixture<SeatCreateEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SeatCreateEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SeatCreateEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
