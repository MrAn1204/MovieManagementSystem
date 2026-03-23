import { Component } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ReactiveFormsModule } from '@angular/forms';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { RoomDetailModel } from '../../../model/room/room-detail.model';
import { ValidationError } from "../../../shared/component/form/error/validation-error";

@Component({
  selector: 'app-room-create-edit',
  imports: [InputField, ReactiveFormsModule, CreateEdit, ValidationError],
  templateUrl: './room-create-edit.html',
  styleUrl: './room-create-edit.css'
})
export class RoomCreateEdit extends CreateEditDialog<RoomDetailModel> {

}
