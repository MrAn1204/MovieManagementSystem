import { Component, OnInit } from '@angular/core';
import { ControlContainer, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { FlowbiteService } from '../../../service/flowbite.service';
import { InputField } from "../../../shared/component/form/input/input-field";

@Component({
  selector: 'app-room-filter',
  imports: [ReactiveFormsModule, InputField],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './room-filter.html',
})
export class RoomFilter implements OnInit {
  constructor(private readonly flowbiteService: FlowbiteService) { }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }
}
