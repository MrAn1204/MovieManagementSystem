import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomFilter } from './room-filter';

describe('RoomFilter', () => {
  let component: RoomFilter;
  let fixture: ComponentFixture<RoomFilter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoomFilter]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RoomFilter);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
