import { Component, inject } from '@angular/core';
import { ScheduleFilterV2 } from "../filter/schedule-filter-v2";
import { ScheduleModel } from '../../../model/schedule/schedule.model';
import { ScheduleService } from '../../../service/schedule/schedule.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { TableV2 } from "../../../shared/component-v2/table/table";
import { Paginator } from "../../../shared/component-v2/paginator/paginator";
import { ScheduleAddEdit } from '../add-edit/schedule-add-edit';
import { ScheduleDetailV2 } from '../detail/schedule-detail-v2';
import { SearchV2 } from "../../../shared/component-v2/search/search";
import { SearchableFeatureV2 } from '../../../shared/component-v2/feature/searchable-feature';
import { EntityService } from '../../../service/entity.service';
import { ScheduleDialogService } from '../../../service/dialog-v2/schedule/schedule-dialog.service';
import { EntityDialogServiceV2 } from '../../../service/dialog-v2/entity-dialog.service';

@Component({
  selector: 'app-schedule-v2',
  imports: [ReactiveFormsModule, TableV2, Paginator, SearchV2, ScheduleFilterV2],
  templateUrl: './schedule-v2.html',
  styleUrl: './schedule-v2.css',
})
export class ScheduleV2 extends SearchableFeatureV2<ScheduleModel> {
  override entityName = "Schedule";

  override contentAddEdit = ScheduleAddEdit;
  override contentDetail = ScheduleDetailV2;

  override sortOptions = [
    { label: 'Movie Name', value: 'movieName' },
    { label: 'Room Name', value: 'roomName' },
    { label: 'Show Time', value: 'showTime' },
  ]

  override filterForm: FormGroup = this.formBuilder.nonNullable.group({
    date: [''],
    minTime: [''],
    maxTime: [''],
    roomId: [''],
  });

  override columns: TableColumnModel<ScheduleModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'showTime', label: 'Show Time', type: 'datetime' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'room', label: 'Room', type: 'id-name' }
  ];

  override roleConfig = getRoleConfig(this.entityName);

  protected override entityService: EntityService<ScheduleModel> = inject(ScheduleService);
  protected override dialogService: EntityDialogServiceV2<ScheduleModel> = inject(ScheduleDialogService);
}
