import { DIALOG_DATA, DialogRef } from '@angular/cdk/dialog';
import { Component, inject, OnInit } from '@angular/core';
import { FlowbiteService } from '../../../../service/flowbite.service';
import { SelectField } from "../../form/select/select-field";
import { MultiselectField } from "../../form/multiselect/multiselect-field";
import { InputField } from "../../form/input/input-field";
import { Textarea } from "../../form/textarea/textarea-field";
import { ImageField } from "../../form/image/image-field";

@Component({
  selector: 'app-create-edit',
  imports: [SelectField, MultiselectField, InputField, Textarea, ImageField],
  templateUrl: './create-edit.html',
  styleUrl: './create-edit.css',
})
export class CreateEdit implements OnInit {
  data: {
    mode: 'create' | 'edit',
    title: string,
  } = inject(DIALOG_DATA);

  constructor(
    private readonly dialogRef: DialogRef,
    private readonly flowbiteService: FlowbiteService,
  ) { }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }

  close() {
    this.dialogRef.close();
  }
}