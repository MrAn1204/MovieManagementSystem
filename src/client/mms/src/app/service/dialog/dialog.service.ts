import { Dialog, DialogRef } from '@angular/cdk/dialog';
import { ComponentType } from '@angular/cdk/portal';
import { Injectable, Type } from '@angular/core';
import { DialogDataModel } from '../../shared/model/dialog/dialog-data.model';
import { Subject } from 'rxjs';
import { BaseDialog } from '../../shared/component/dialog/base/base-dialog';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  private readonly openDialogSubject = new Subject<Type<unknown>>();
  readonly openDialog$ = this.openDialogSubject.asObservable();

  private readonly saveFormSubject = new Subject<void>();
  readonly saveForm$ = this.saveFormSubject.asObservable();

  private readonly confirmTaskSubject = new Subject<void>();
  readonly confirmTask$ = this.confirmTaskSubject.asObservable();

  constructor(private readonly dialog: Dialog) { }

  openDialog<R, C>(dialogComponent: ComponentType<C>, dialogData?: DialogDataModel): DialogRef<R, C> {
    return this.dialog.open<R, DialogDataModel, C>(dialogComponent, {
      backdropClass: 'bg-space-black/50',
      data: dialogData,
    });
  }

  closeDialog(dialogRef: DialogRef) {
    dialogRef.close();
  }

  triggerOpen(dialog: Type<BaseDialog>): void {
    this.openDialogSubject.next(dialog);
  }

  triggerSave(): void {
    this.saveFormSubject.next();
  }

  triggerConfirm(): void {
    this.confirmTaskSubject.next();
  }
}
