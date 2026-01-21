import { DIALOG_DATA } from '@angular/cdk/dialog';
import { Component, inject, OnInit } from '@angular/core';
import { FlowbiteService } from '../../../../service/flowbite.service';
import { ReactiveFormsModule } from '@angular/forms';
import { NgComponentOutlet } from '@angular/common';
import { BaseDialog } from '../base/base-dialog';
import { DialogFormDataModel } from '../../../model/dialog/dialog-form-data.model';

@Component({
  selector: 'app-create-edit',
  imports: [NgComponentOutlet, ReactiveFormsModule],
  templateUrl: './create-edit.html',
  styleUrl: './create-edit.css',
})
export class CreateEdit extends BaseDialog implements OnInit {
  data: DialogFormDataModel = inject(DIALOG_DATA);

  constructor(
    private readonly flowbiteService: FlowbiteService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }
  
  onSubmit(): void {
    this.dialogService.triggerSave();
  }
}