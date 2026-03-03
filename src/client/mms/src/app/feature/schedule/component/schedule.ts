import { Component } from '@angular/core';
import { Search } from "../../../shared/component/search/search";
import { Table } from "../../../shared/component/table/table";
import { ScheduleFilter } from "../filter/schedule-filter";
import { ScheduleCreateEdit } from '../create-edit/schedule-create-edit';
import { ScheduleDetail } from '../detail/schedule-detail';
import { ScheduleModel } from '../../../model/schedule.model';
import { ScheduleService } from '../../../service/schedule/schedule.service';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TableColumnModel } from '../../../shared/model/table-column.model';
import { BaseFeature } from '../../../shared/component/feature/base-feature';
import { getRoleConfig } from '../../../shared/config/role-config';

@Component({
  selector: 'app-schedule',
  imports: [Search, Table, ReactiveFormsModule],
  templateUrl: './schedule.html',
  styleUrl: './schedule.css',
})
export class Schedule extends BaseFeature<ScheduleModel> {
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
    { label: 'Name', value: 'name' },
    { label: 'Show Time', value: 'showTime' },
  ];

  override roleConfig = getRoleConfig(this.entityName);

  constructor(private readonly scheduleService: ScheduleService) {
    super();
  }

  protected override getFilterGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      date: [''],
      minTime: [''],
      maxTime: [''],
      roomId: [''],
    });
  }

  protected override getUpsertGroup(): FormGroup {
    return this.formBuilder.nonNullable.group({
      showTime: ['', [Validators.required]],
      movieId: ['', [Validators.required]],
      roomId: ['', [Validators.required]],
    });
  }

  override onSearch(): void {
    this.scheduleService.search(this.searchForm.value).subscribe(res => {
      this.data.set(res);
    });
  }

  override saveNew(): void {
    this.scheduleService.create(this.entityForm.value).subscribe(() => {
      this.onSearch();
    });
  }

  override saveUpdate(id: string): void {
    this.scheduleService.update(id, this.entityForm.value).subscribe(() => {
      this.onSearch();
    });
  }

  override confirmDelete(id: string): void {
    this.scheduleService.delete(id).subscribe(() => {
      this.onSearch();
    });
  }

  override onEdit(id: string): void {
    this.scheduleService.getById(id).subscribe(res => {
      this.displayEdit(res);
    });
  }

  override onView(id: string): void {
    this.scheduleService.getById(id).subscribe(res => {
      this.displayInfo(res);
    });
  }

  override onDelete(id: string): void {
    this.displayDelete(id);
  }

  override patchEntityForm(model: ScheduleModel): void {
    this.entityForm.patchValue({
      ...model,
      movieId: model.movie?.id ?? null,
      roomId: model.room?.id ?? null,
    });
  }
}
