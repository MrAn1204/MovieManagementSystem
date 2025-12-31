import { Component, inject } from '@angular/core';
import { DialogService } from '../../../../service/dialog/dialog.service';
import { DialogRef } from '@angular/cdk/dialog';

@Component({
  selector: 'app-base-dialog',
  imports: [],
  templateUrl: './base-dialog.html',
  styleUrl: './base-dialog.css',
})
export class BaseDialog {
  dialogService = inject(DialogService);
  dialogRef = inject(DialogRef);
  
  close() {
    this.dialogService.closeDialog(this.dialogRef);
  }
}
