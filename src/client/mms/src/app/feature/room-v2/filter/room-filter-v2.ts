import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { Filter } from '../../../shared/component-v2/filter/filter';

@Component({
  selector: 'app-room-filter-v2',
  imports: [ReactiveFormsModule, FormInput],
  templateUrl: './room-filter-v2.html',
  styleUrl: './room-filter-v2.css',
})
export class RoomFilterV2 extends Filter<RoomFilterForm> {}

export type RoomFilterForm = {
  minCapacity: FormControl<number | null | undefined>;
  maxCapacity: FormControl<number | null | undefined>;
}
