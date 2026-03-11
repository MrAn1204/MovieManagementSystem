import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SeatDetail } from './seat-detail';

describe('SeatDetail', () => {
  let component: SeatDetail;
  let fixture: ComponentFixture<SeatDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SeatDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SeatDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
