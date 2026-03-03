import { Component, inject, input, OnInit, signal } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { SelectField } from "../../../shared/component/form/select/select-field";
import { ScheduleModel } from '../../../model/schedule.model';
import { ControlContainer, FormGroup, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { MovieService } from '../../../service/movie/movie.service';
import { RoomService } from '../../../service/room/room.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';

@Component({
  selector: 'app-schedule-create-edit',
  imports: [InputField, SelectField, ReactiveFormsModule],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './schedule-create-edit.html',
  styleUrl: './schedule-create-edit.css',
})
export class ScheduleCreateEdit implements OnInit {
  model = input<ScheduleModel>();
  mode = input<'create' | 'edit'>();

  form: FormGroup = inject(ControlContainer).control as FormGroup;

  movies = signal<FormOptionModel[]>([]);
  rooms = signal<FormOptionModel[]>([]);

  constructor(
    private readonly movieService: MovieService,
    private readonly roomService: RoomService,
  ) { }

  ngOnInit(): void {
    this.loadOptions();
  }

  private loadOptions(): void {
    const model = this.model();

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
