import { Component, inject } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { DialogPopupDataModel } from '../../../model/dialog/dialog-popup-data.model';

@Component({
  selector: 'app-popup-modal',
  imports: [],
  templateUrl: './popup-modal.html',
  styleUrl: './popup-modal.css',
})
export class PopupModal extends BaseDialog {
  data: DialogPopupDataModel = inject(DIALOG_DATA);

  config = {
    warning: {
      icon: "fa-exclamation",
      iconColor: "bg-carrot-orange",
      header: "Warning",
      headerColor: "text-carrot-orange",
      buttonStyle: "bg-carrot-orange-500 hover:bg-carrot-orange-600",
    },

    error: {
      icon: "fa-xmark",
      iconColor: "bg-vermilion",
      header: "Error",
      headerColor: "text-vermilion",
      buttonStyle: "bg-vermilion-500 hover:bg-vermilion-600",
    },

    success: {
      icon: "fa-check",
      iconColor: "bg-bright-fern",
      header: "Success",
      headerColor: "text-bright-fern",
      buttonStyle: "bg-bright-fern-500 hover:bg-bright-fern-600",
    },

    info: {
      icon: "fa-info",
      iconColor: "bg-steel-blue",
      header: "Info",
      headerColor: "text-steel-blue",
      buttonStyle: "bg-steel-blue-500 hover:bg-steel-blue-600",
    },
  }

  confirm = () => this.dialogService.triggerConfirm();
}
