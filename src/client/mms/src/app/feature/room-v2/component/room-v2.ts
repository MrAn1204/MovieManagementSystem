import { Component, inject } from '@angular/core';
import { RoomFilterV2 } from "../filter/room-filter-v2";
import { RoomModel } from '../../../model/room/room.model';
import { RoomService } from '../../../service/room/room.service';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableV2 } from "../../../shared/component-v2/table/table";
import { Paginator } from "../../../shared/component-v2/paginator/paginator";
import { RoomAddEdit } from '../add-edit/room-add-edit';
import { RoomDetailV2 } from '../detail/room-detail-v2';
import { SearchV2 } from "../../../shared/component-v2/search/search";
import { SearchableFeatureV2 } from '../../../shared/component-v2/feature/searchable-feature';
import { EntityService } from '../../../service/entity.service';
import { RoomDialogService } from '../../../service/dialog-v2/room/room-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';

@Component({
  selector: 'app-room-v2',
  imports: [ReactiveFormsModule, TableV2, Paginator, SearchV2, RoomFilterV2],
  templateUrl: './room-v2.html',
  styleUrl: './room-v2.css',
})
export class RoomV2 extends SearchableFeatureV2<RoomModel> {
  override entityName = "Room";

  override contentAddEdit = RoomAddEdit;
  override contentDetail = RoomDetailV2;

  override sortOptions = [
    { label: 'Name', value: 'name' },
    { label: 'Current Capacity', value: 'currentCapacity' },
    { label: 'Max Capacity', value: 'maxCapacity' },
  ]

  override filterForm: FormGroup = this.formBuilder.nonNullable.group({
    minCapacity: [null, [Validators.min(0)]],
    maxCapacity: [null, [Validators.min(0)]],
  });

  override columns: TableColumnModel<RoomModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'currentCapacity', label: 'Current Capacity', type: 'number' },
    { key: 'maxCapacity', label: 'Max Capacity', type: 'number' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  protected override entityService: EntityService<RoomModel> = inject(RoomService);
  protected override dialogService: EntityDialogServiceV2<RoomModel> = inject(RoomDialogService);
}
