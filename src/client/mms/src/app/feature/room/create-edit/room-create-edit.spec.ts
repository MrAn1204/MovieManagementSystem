import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomCreateEdit } from './room-create-edit';

describe('RoomCreateEdit', () => {
  let component: RoomCreateEdit;
  let fixture: ComponentFixture<RoomCreateEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoomCreateEdit]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RoomCreateEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
