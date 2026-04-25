import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
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
import { CustomValidators } from '../../../shared/util/custom-validators';

@Component({
  selector: 'app-ticket-create-edit',
  imports: [CreateEdit, SelectField, ValidationError, ReactiveFormsModule, SeatMap, MultiselectField],
  templateUrl: './ticket-create-edit.html',
  styleUrl: './ticket-create-edit.css',
})
export class TicketCreateEdit extends CreateEditDialog<TicketDetailModel> implements OnInit {
  private readonly scheduleService = inject(ScheduleService);
  private readonly seatService = inject(SeatService);
  private readonly promotionService = inject(PromotionService);
  private readonly userService = inject(UserService);
  private readonly constraintService = inject(ConstraintService);
  override form = this.createForm();

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

  private readonly basePrice;

  constructor() {
    super();
    this.basePrice = Number(this.constraintService.getConstraint('BASE_SEAT_PRICE'));
  }

  get isEditMode(): boolean {
    return !!this.data.model;
  }

  ngOnInit(): void {
    this.patchForm();
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

  override createForm() {
    return this.formBuilder.nonNullable.group({
      scheduleId: ['', [CustomValidators.required('ticket.schedule.required')]],
      seatIds: [[] as string[], [CustomValidators.required('ticket.seat.required')]],
      promotionId: [''],
      userId: ['', [CustomValidators.required('user.required')]],
    });
  }

  override patchForm(): void {
    const model = this.data.model;
    if (!model) {
      return;
    }

    this.form.patchValue({
      scheduleId: model.schedule.id,
      seatIds: [model.seat.id],
      promotionId: model.promotion?.id ?? '',
    });
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
      })));
    });
  }

  private loadPromotions(): void {
    this.promotionService.getAll().subscribe((promotions) => {
      const promotionOptions: FormOptionModel[] = promotions.map((promotion) => ({
        label: promotion.title,
        value: promotion.id,
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
