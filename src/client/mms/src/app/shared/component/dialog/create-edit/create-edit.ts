import { DIALOG_DATA } from '@angular/cdk/dialog';
import { Component, inject, OnInit } from '@angular/core';
import { FlowbiteService } from '../../../../service/flowbite.service';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgComponentOutlet } from '@angular/common';
import { BaseDialog } from '../base/base-dialog';
import { DialogDataModel } from '../../../model/dialog-data.model';

@Component({
  selector: 'app-create-edit',
  imports: [NgComponentOutlet, ReactiveFormsModule],
  templateUrl: './create-edit.html',
  styleUrl: './create-edit.css',
})
export class CreateEdit extends BaseDialog implements OnInit {
  data: DialogDataModel = inject(DIALOG_DATA);

  form!: FormGroup;

  constructor(
    private readonly flowbiteService: FlowbiteService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });

    this.form = new FormGroup({});
  }
  
  onSubmit(): void {
    console.log(this.form.value);
    
  }
}