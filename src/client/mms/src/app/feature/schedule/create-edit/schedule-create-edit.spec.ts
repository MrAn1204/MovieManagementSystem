import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ScheduleCreateEdit } from './schedule-create-edit';

describe('ScheduleCreateEdit', () => {
  let component: ScheduleCreateEdit;
  let fixture: ComponentFixture<ScheduleCreateEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScheduleCreateEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleCreateEdit);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
