import { Component, computed, OnInit, signal } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { CreateEdit } from '../../../shared/component/create-edit/create-edit';
import { SelectField } from '../../../shared/component/form/select/select-field';
import { ValidationError } from '../../../shared/component/form/error/validation-error';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { TicketDetailModel } from '../../../model/ticket/ticket-detail.model';
import { ScheduleService } from '../../../service/schedule/schedule.service';
import { PromotionService } from '../../../service/promotion/promotion.service';
import { UserService } from '../../../service/user/user.service';
import { SeatService } from '../../../service/seat/seat.service';
import { SeatTypeModel } from '../../../model/seat/seat-type.model';
import { ConstraintService } from '../../../service/constraint.service';
import { SeatMap } from "../../seat/seat-map/seat-map";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { ScheduleDetailModel } from '../../../model/schedule/schedule-detail.model';
import { filter, switchMap } from 'rxjs';

@Component({
  selector: 'app-ticket-create-edit',
  imports: [CreateEdit, SelectField, ValidationError, ReactiveFormsModule, SeatMap, MultiselectField],
  templateUrl: './ticket-create-edit.html',
  styleUrl: './ticket-create-edit.css',
})
export class TicketCreateEdit extends CreateEditDialog<TicketDetailModel> implements OnInit {
  schedules = signal<FormOptionModel[]>([]);
  promotions = signal<FormOptionModel[]>([]);
  users = signal<FormOptionModel[]>([]);
  seatTypes = signal<SeatTypeModel>({});
  selectedSchedule = signal<ScheduleDetailModel | null>(null);

  seats = computed<FormOptionModel[]>(() => {
    const schedule = this.selectedSchedule();

    if (!schedule) {
      return [];
    }

    const availableSeats = schedule.seats.filter((seat) => !seat.reserved);

    return availableSeats.map((seat) => ({
      label: `${seat.name} (${seat.seatType}) - ${this.calculatePrice(seat.seatType)}`,
      value: seat.id,
    }));
  });

  scheduleForm!: FormGroup;

  private readonly basePrice;

  constructor(
    private readonly scheduleService: ScheduleService,
    private readonly seatService: SeatService,
    private readonly promotionService: PromotionService,
    private readonly userService: UserService,
    private readonly constraintService: ConstraintService
  ) {
    super();
    this.basePrice = Number(this.constraintService.getConstraint('BASE_SEAT_PRICE'));
  }

  get isEditMode(): boolean {
    return !!this.data.model;
  }

  ngOnInit(): void {
    this.setupUserField();
    this.loadSchedules();
    this.loadSeatTypes();
    this.loadPromotions();

    if (this.isEditMode) {
      const scheduleId = this.data.model!.schedule.id;

      this.scheduleService.getById(scheduleId).subscribe((schedule) => {
        this.selectedSchedule.set(schedule);
      });
    } else {
      this.loadUsers();
    }

    this.form.get('scheduleId')?.valueChanges
      .pipe(
        filter(scheduleId => !!scheduleId),
        switchMap(scheduleId => this.scheduleService.getById(scheduleId)))
      .subscribe(schedule => this.selectedSchedule.set(schedule));
  }

  private setupUserField(): void {
    const userControl = this.form.get('userId');

    if (!userControl) {
      return;
    }

    if (this.isEditMode) {
      userControl.disable();
    } else {
      userControl.enable();
    }

    userControl.updateValueAndValidity();
  }

  private loadSchedules(): void {
    this.scheduleService.getAll().subscribe((schedules) => {
      this.schedules.set(schedules.map((schedule) => ({
        label: schedule.name,
        value: schedule.id,
        selected: schedule.id === this.data.model?.schedule.id,
      })));
    });
  }

  private loadPromotions(): void {
    const modelPromotionId = this.data.model?.promotion?.id ?? '';

    this.promotionService.getAll().subscribe((promotions) => {
      const promotionOptions: FormOptionModel[] = promotions.map((promotion) => ({
        label: promotion.title,
        value: promotion.id,
        selected: modelPromotionId === promotion.id,
      }));

      this.promotions.set(promotionOptions);
    });
  }

  private loadUsers(): void {
    this.userService.getAll().subscribe((users) => {
      this.users.set(users.map((user) => ({
        label: `${user.username} (${user.fullname})`,
        value: user.id,
      })));
    });
  }

  private loadSeatTypes(): void {
    this.seatService.getSeatTypes().subscribe((seatTypes) => {
      this.seatTypes.set(seatTypes);
    });
  }

  private calculatePrice(seatType: string): number {
    return this.basePrice * this.seatTypes()[seatType];
  }
}
