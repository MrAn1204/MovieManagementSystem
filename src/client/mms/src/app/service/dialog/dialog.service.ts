import { Dialog, DialogRef } from '@angular/cdk/dialog';
import { ComponentType } from '@angular/cdk/portal';
import { Injectable } from '@angular/core';
import { DialogDataModel } from '../../shared/model/dialog-data.model';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  constructor(private readonly dialog: Dialog) { }

  openDialog(dialogComponent: ComponentType<any>, dialogData?: DialogDataModel): DialogRef {
    return this.dialog.open(dialogComponent, {
      backdropClass: 'bg-space-black/50',
      data: dialogData,
    });
  }

  closeDialog(dialogRef: DialogRef) {
    dialogRef.close();
  }
}
