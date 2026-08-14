import { Component, OnInit, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { Filter } from '../../../shared/component-v2/filter/filter';
import { RoomService } from '../../../service/room/room.service';

@Component({
  selector: 'app-schedule-filter-v2',
  imports: [ReactiveFormsModule, FormSelect, FormInput],
  templateUrl: './schedule-filter-v2.html',
  styleUrl: './schedule-filter-v2.css',
})
export class ScheduleFilterV2 extends Filter<ScheduleFilterForm> implements OnInit {
  rooms = signal<FormOptionModel[]>([]);

  constructor(private readonly roomService: RoomService) {
    super();
  }

  ngOnInit(): void {
    this.roomService.getAll().subscribe((rooms) => {
      const roomOptions: FormOptionModel[] = rooms.map((room) => ({
        label: room.name,
        value: room.id,
      }));

      roomOptions.unshift({ label: 'Any Room', value: '' });
      this.rooms.set(roomOptions);
    });
  }
}

export type ScheduleFilterForm = {
  date: FormControl<string | undefined>;
  minTime: FormControl<string | undefined>;
  maxTime: FormControl<string | undefined>;
  roomId: FormControl<string | undefined>;
}
