import { Component, OnInit } from '@angular/core';
import { InputField } from '../../../shared/component/form/input/input-field';
import { ControlContainer, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { FlowbiteService } from '../../../service/flowbite.service';

@Component({
  selector: 'app-promotion-filter',
  imports: [InputField, ReactiveFormsModule],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './promotion-filter.html',
  styleUrl: './promotion-filter.css',
})
export class PromotionFilter implements OnInit {
  constructor(private readonly flowbiteService: FlowbiteService) {
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }
}
