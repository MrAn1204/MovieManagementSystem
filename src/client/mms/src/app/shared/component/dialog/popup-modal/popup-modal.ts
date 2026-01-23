import { Component, inject } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { DialogModalDataModel } from '../../../model/dialog/dialog-modal-data.model';

@Component({
  selector: 'app-popup-modal',
  imports: [],
  templateUrl: './popup-modal.html',
  styleUrl: './popup-modal.css',
})
export class PopupModal extends BaseDialog {
  data: DialogModalDataModel = inject(DIALOG_DATA);
  
  confirm = () => this.dialogService.triggerConfirm();
}
