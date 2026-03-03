import { Component, OnInit, signal } from '@angular/core';
import { SelectField } from "../../../shared/component/form/select/select-field";
import { InputField } from "../../../shared/component/form/input/input-field";
import { ControlContainer, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { RoomService } from '../../../service/room/room.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FlowbiteService } from '../../../service/flowbite.service';

@Component({
  selector: 'app-schedule-filter',
  imports: [SelectField, InputField, ReactiveFormsModule],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './schedule-filter.html',
  styleUrl: './schedule-filter.css',
})
export class ScheduleFilter implements OnInit {
  rooms = signal<FormOptionModel[]>([]);

  constructor(
    private readonly roomService: RoomService,
    private readonly flowbiteService: FlowbiteService
  ) { }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });

    this.roomService.getAll().subscribe((rooms) => {
      const roomOptions: FormOptionModel[] = rooms.map((room) => ({
          label: room.name,
          value: room.id,
          selected: false
        }));

      roomOptions.unshift({
        label: 'Any Room',
        value: '',
        selected: true
      });

      this.rooms.set(roomOptions);
    });
  }
}
