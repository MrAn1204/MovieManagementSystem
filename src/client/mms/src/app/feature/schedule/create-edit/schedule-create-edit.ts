import { Component, OnInit, signal } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { SelectField } from "../../../shared/component/form/select/select-field";
import { ScheduleModel } from '../../../model/schedule.model';
import { ControlContainer, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { MovieService } from '../../../service/movie/movie.service';
import { RoomService } from '../../../service/room/room.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';

@Component({
  selector: 'app-schedule-create-edit',
  imports: [InputField, SelectField, ReactiveFormsModule, CreateEdit],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './schedule-create-edit.html',
  styleUrl: './schedule-create-edit.css',
})
export class ScheduleCreateEdit extends CreateEditDialog<ScheduleModel> implements OnInit {
  movies = signal<FormOptionModel[]>([]);
  rooms = signal<FormOptionModel[]>([]);

  constructor(
    private readonly movieService: MovieService,
    private readonly roomService: RoomService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.loadOptions();
  }

  private loadOptions(): void {
    const model = this.data.model;

    const modelMovie = model?.movie.id;
    const modelRoom = model?.room.id;

    this.movieService.getAll().subscribe((movies) => {
      this.movies.set(movies.map((movie) => ({
        label: movie.name,
        value: movie.id,
        selected: modelMovie === movie.id || false
      })));
    });

    this.roomService.getAll().subscribe((rooms) => {
      this.rooms.set(rooms.map((room) => ({
        label: room.name,
        value: room.id,
        selected: modelRoom === room.id || false
      })));
    });
  }
}
