import { Component, inject, signal } from '@angular/core';
import { AddEditDialog } from '../../../shared/component-v2/dialog/add-edit-dialog/add-edit-dialog';
import { TicketDetailModel } from '../../../model/ticket/ticket-detail.model';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { ScheduleService } from '../../../service/schedule/schedule.service';
import { PromotionService } from '../../../service/promotion/promotion.service';
import { UserService } from '../../../service/user/user.service';
import { SeatService } from '../../../service/seat/seat.service';
import { SeatTypeModel } from '../../../model/seat/seat-type.model';
import { ConstraintService } from '../../../service/constraint.service';
import { SeatMap } from "../../seat/seat-map/seat-map";
import { ScheduleDetailModel } from '../../../model/schedule/schedule-detail.model';
import { filter, switchMap } from 'rxjs';
import { CustomValidators } from '../../../shared/util/custom-validators';
import { DialogFormDataModel } from '../../../shared/model/dialog/dialog-form-data.model';
import { AddEditContainer } from "../../../shared/component-v2/dialog/add-edit-container/add-edit-container";
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DetailEntityService } from '../../../service/detail-entity.service';
import { TicketService } from '../../../service/ticket/ticket.service';
import { MatError } from '@angular/material/select';

export interface TicketDialogDataModel extends DialogFormDataModel<TicketDetailModel> {
  scheduleId?: string;
  userId?: string;
}

@Component({
  selector: 'app-ticket-add-edit',
  imports: [AddEditContainer, FormSelect, SeatMap, MatError],
  templateUrl: './ticket-add-edit.html',
  styleUrl: './ticket-add-edit.css',
})
export class TicketAddEdit extends AddEditDialog<TicketDetailModel> {
  protected override entityService: DetailEntityService<TicketDetailModel> = inject(TicketService);

  private readonly ticketData = inject<TicketDialogDataModel>(MAT_DIALOG_DATA);

  private readonly scheduleService = inject(ScheduleService);
  private readonly seatService = inject(SeatService);
  private readonly promotionService = inject(PromotionService);
  private readonly userService = inject(UserService);
  private readonly constraintService = inject(ConstraintService);

  override form = this.formBuilder.nonNullable.group({
    scheduleId: ['', [CustomValidators.required('ticket.schedule.required')]],
    seatIds: [[] as string[], [CustomValidators.required('ticket.seat.required')]],
    promotionId: [''],
    userId: ['', [CustomValidators.required('user.required')]],
  });

  schedules = signal<FormOptionModel[]>([]);
  promotions = signal<FormOptionModel[]>([]);
  users = signal<FormOptionModel[]>([]);
  seats = signal<FormOptionModel[]>([]);
  seatTypes = signal<SeatTypeModel>({});

  selectedSchedule = signal<ScheduleDetailModel | null>(null);

  private readonly basePrice: number;

  constructor() {
    super();
    this.basePrice = Number(this.constraintService.getConstraint('BASE_SEAT_PRICE'));
  }

  get isEditMode(): boolean {
    return !!this.data.id;
  }

  override ngOnInit(): void {
    super.ngOnInit();

    this.setupUserField();
    this.setupScheduleField();

    this.loadOptions();
  }

  override loadOptions(): void {
    this.loadSchedules();
    this.loadSeatTypes();
    this.loadPromotions();

    if (!this.isEditMode) {
      this.loadUsers();
    }
  }

  protected override mapForm(model: TicketDetailModel): void {
    this.onReset({
      scheduleId: model.schedule.id,
      seatIds: [model.seat.id],
      promotionId: model.promotion?.id ?? '',
      userId: model.user.id,
    });
  }

  private setupUserField(): void {
    const userControl = this.form.get('userId');

    if (!userControl || this.isEditMode) {
      return;
    }

    if (this.ticketData.userId) {
      userControl.disable();
      userControl.setValue(this.ticketData.userId);
    }

    userControl.updateValueAndValidity();
  }

  private setupScheduleField(): void {
    const scheduleControl = this.form.controls.scheduleId;

    scheduleControl.valueChanges
      .pipe(
        filter(scheduleId => !!scheduleId),
        switchMap(scheduleId => this.scheduleService.getById(scheduleId))
      ).subscribe((schedule) => {
        this.selectedSchedule.set(schedule);

        schedule.seats.sort((a, b) => a.name.localeCompare(b.name));

        this.seats.set(schedule.seats.map((seat) => ({
          label: `${seat.name} (${seat.seatType}) - ${this.calculatePrice(seat.seatType)}`,
          value: seat.id
        })));
      });

    if (this.ticketData.scheduleId) {
      scheduleControl.disable();
      scheduleControl.setValue(this.ticketData.scheduleId);
    }

    scheduleControl.updateValueAndValidity();
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
