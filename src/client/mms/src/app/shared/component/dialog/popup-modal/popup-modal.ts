import { Component, inject } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { DialogPopupDataModel } from '../../../model/dialog/dialog-popup-data.model';
import { Button, ButtonVariant } from "../../button/button";

@Component({
  selector: 'app-popup-modal',
  imports: [Button],
  templateUrl: './popup-modal.html',
  styleUrl: './popup-modal.css',
})
export class PopupModal extends BaseDialog {
  data: DialogPopupDataModel = inject(DIALOG_DATA);

  config: Record<string, PopupConfig> = {
    warning: {
      icon: "fa-exclamation",
      iconColor: "bg-carrot-orange",
      header: "Warning",
      headerColor: "text-carrot-orange",
      buttonStyle: "warning",
    },

    error: {
      icon: "fa-xmark",
      iconColor: "bg-vermilion",
      header: "Error",
      headerColor: "text-vermilion",
      buttonStyle: "danger",
    },

    success: {
      icon: "fa-check",
      iconColor: "bg-bright-fern",
      header: "Success",
      headerColor: "text-bright-fern",
      buttonStyle: "success",
    },

    info: {
      icon: "fa-info",
      iconColor: "bg-steel-blue",
      header: "Info",
      headerColor: "text-steel-blue",
      buttonStyle: "primary",
    },
  }

  confirm = () => this.dialogService.triggerConfirm(this.dialogRef);
}

interface PopupConfig {
  icon: string;
  iconColor: string;
  header: string;
  headerColor: string;
  buttonStyle: ButtonVariant;
}
