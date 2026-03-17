import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { RoomFilter } from '../filter/room-filter';
import { RoomCreateEdit } from '../create-edit/room-create-edit';
import { RoomDetail } from '../detail/room-detail';
import { RoomModel } from '../../../model/room/room.model';
import { RoomService } from '../../../service/room/room.service';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SearchableFeature } from '../../../shared/component/feature/searchable-feature';

@Component({
  selector: 'app-room',
  imports: [Search, Table, ReactiveFormsModule],
  templateUrl: './room.html',
})
export class Room extends SearchableFeature<RoomModel> {
  override entityName = 'Room';

  override contentCreateEdit = RoomCreateEdit;
  override contentDetail = RoomDetail;
  override contentFilter = RoomFilter;

  override columns: TableColumnModel<RoomModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'maxCapacity', label: 'Max Capacity', type: 'number' },
  ];

  override sortOptions = [
    { label: 'Name', value: 'name' },
    { label: 'Max Capacity', value: 'maxCapacity' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  constructor(roomService: RoomService) {
    super(roomService);
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      minCapacity: [null, [Validators.min(0)]],
      maxCapacity: [null, [Validators.min(0)]],
    });
  }

  protected override getUpsertGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      name: ['', [Validators.required]],
      rowLength: [1, [Validators.required, Validators.min(1)]],
      columnLength: [1, [Validators.required, Validators.min(1)]],
    });
  }

  override patchEntityForm(model: RoomModel): void {
    this.entityForm.patchValue({
      name: model.name,
      rowLength: model.rowLength,
      columnLength: model.columnLength,
    });
  }
}
