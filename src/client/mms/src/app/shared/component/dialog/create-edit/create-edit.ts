import { DIALOG_DATA } from '@angular/cdk/dialog';
import { Component, inject, input, OnInit } from '@angular/core';
import { FlowbiteService } from '../../../../service/flowbite.service';
import { FormControl, FormGroup } from '@angular/forms';
import { NgComponentOutlet } from '@angular/common';
import { BaseDialog } from '../base/base-dialog';

@Component({
  selector: 'app-create-edit',
  imports: [NgComponentOutlet],
  templateUrl: './create-edit.html',
  styleUrl: './create-edit.css',
})
export class CreateEdit extends BaseDialog implements OnInit {
  data: {
    inputs: Record<string, any>,
    mode: 'create' | 'edit',
    title: string,
    contentComponent: any,
  } = inject(DIALOG_DATA);

  fields = input<Record<string, FormControl>>();

  form: FormGroup;

  constructor(
    private readonly flowbiteService: FlowbiteService,
  ) {
    super();
    this.form = new FormGroup({
      ...this.fields(),
    });
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }
}