import { DIALOG_DATA, DialogRef } from '@angular/cdk/dialog';
import { Component, inject, input, OnInit } from '@angular/core';
import { FlowbiteService } from '../../../../service/flowbite.service';
import { ActivatedRoute } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { NgComponentOutlet } from '@angular/common';

@Component({
  selector: 'app-create-edit',
  imports: [NgComponentOutlet],
  templateUrl: './create-edit.html',
  styleUrl: './create-edit.css',
})
export class CreateEdit implements OnInit {
  data: {
    mode: 'create' | 'edit',
    title: string,
    contentComponent: any,
  } = inject(DIALOG_DATA);
  route = inject(ActivatedRoute);

  fields = input<Record<string, FormControl>>();

  form: FormGroup;

  constructor(
    private readonly dialogRef: DialogRef,
    private readonly flowbiteService: FlowbiteService,
  ) {
    this.form = new FormGroup({
      ...this.fields(),
    });
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }

  close() {
    this.dialogRef.close();
  }
}