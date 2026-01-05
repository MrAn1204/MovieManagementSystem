import { DIALOG_DATA } from '@angular/cdk/dialog';
import { Component, inject, input } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { NgComponentOutlet } from '@angular/common';

@Component({
  selector: 'app-detail',
  imports: [NgComponentOutlet],
  templateUrl: './detail.html',
  styleUrl: './detail.css',
})
export class Detail extends BaseDialog {
  data: {
    inputs: Record<string, any>,
    title: string,
    contentComponent: any,
    updateItem: () => void,
  } = inject(DIALOG_DATA);

  model = input<any>();
}
