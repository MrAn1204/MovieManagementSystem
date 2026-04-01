import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { ScheduleFilter } from "../filter/schedule-filter";
import { ScheduleCreateEdit } from '../create-edit/schedule-create-edit';
import { ScheduleDetail } from '../detail/schedule-detail';
import { ScheduleModel } from '../../../model/schedule/schedule.model';
import { ScheduleService } from '../../../service/schedule/schedule.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { getRoleConfig } from '../../../shared/config/role-config';
import { SearchableFeature } from '../../../shared/component/feature/searchable-feature';
import { Pagination } from "../../../shared/component/pagination/pagination";

@Component({
  selector: 'app-schedule',
  imports: [Search, Table, ReactiveFormsModule, Pagination],
  templateUrl: './schedule.html',
  styleUrl: './schedule.css',
})
export class Schedule extends SearchableFeature<ScheduleModel> {
  override entityName = "Schedule";

  override contentCreateEdit = ScheduleCreateEdit;
  override contentDetail = ScheduleDetail;
  override contentFilter = ScheduleFilter;

  override columns: TableColumnModel<ScheduleModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'showTime', label: 'Show Time', type: 'datetime' },
    { key: 'movie', label: 'Movie', type: 'id-name' },
    { key: 'room', label: 'Room', type: 'id-name' }
  ];

  override sortOptions = [
    { label: 'Movie Name', value: 'movieName' },
    { label: 'Room Name', value: 'roomName' },
    { label: 'Show Time', value: 'showTime' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  constructor(scheduleService: ScheduleService) {
    super(scheduleService);
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      date: [''],
      minTime: [''],
      maxTime: [''],
      roomId: [''],
    });
  }
}
