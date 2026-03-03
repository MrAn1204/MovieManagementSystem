import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ScheduleFilter } from './schedule-filter';

describe('ScheduleFilter', () => {
  let component: ScheduleFilter;
  let fixture: ComponentFixture<ScheduleFilter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ScheduleFilter]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleFilter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
