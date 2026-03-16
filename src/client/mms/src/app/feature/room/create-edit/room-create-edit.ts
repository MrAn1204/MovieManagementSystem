import { Component } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ReactiveFormsModule } from '@angular/forms';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { CreateEditDialog } from '../../../shared/component/dialog/create-edit/create-edit-dialog';
import { RoomDetailModel } from '../../../model/room/room-detail.model';

@Component({
  selector: 'app-room-create-edit',
  imports: [InputField, ReactiveFormsModule, CreateEdit],
  templateUrl: './room-create-edit.html',
  styleUrl: './room-create-edit.css'
})
export class RoomCreateEdit extends CreateEditDialog<RoomDetailModel> {

}
