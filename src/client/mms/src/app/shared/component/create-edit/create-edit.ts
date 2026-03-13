import { Component, input, OnInit, output } from '@angular/core';
import { FlowbiteService } from '../../../service/flowbite.service';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-create-edit',
  imports: [ReactiveFormsModule],
  templateUrl: './create-edit.html',
  styleUrl: './create-edit.css',
})
export class CreateEdit implements OnInit {
  title = input<string>();

  submitForm = output<void>();
  resetForm = output<void>();
  closeDialog = output<void>();

  constructor(
    private readonly flowbiteService: FlowbiteService,
  ) {
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }
}
