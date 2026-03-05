import { Component, inject, input } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { RoomModel } from '../../../model/room.model';
import { ControlContainer, FormGroup, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-room-create-edit',
  imports: [InputField, ReactiveFormsModule],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './room-create-edit.html',
})
export class RoomCreateEdit {
  model = input<RoomModel>();
  mode = input<'create' | 'edit'>();

  form: FormGroup = inject(ControlContainer).control as FormGroup;
}
