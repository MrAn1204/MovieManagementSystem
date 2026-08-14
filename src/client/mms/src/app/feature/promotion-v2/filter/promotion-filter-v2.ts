import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { Filter } from '../../../shared/component-v2/filter/filter';

@Component({
  selector: 'app-promotion-filter-v2',
  imports: [ReactiveFormsModule, FormInput],
  templateUrl: './promotion-filter-v2.html',
  styleUrl: './promotion-filter-v2.css',
})
export class PromotionFilterV2 extends Filter<PromotionFilterForm> {}

export type PromotionFilterForm = {
  startDate: FormControl<string | undefined>;
  endDate: FormControl<string | undefined>;
}
