import { Dialog, DialogRef } from '@angular/cdk/dialog';
import { ComponentType } from '@angular/cdk/portal';
import { Injectable, Type } from '@angular/core';
import { DialogDataModel } from '../../shared/model/dialog/dialog-data.model';
import { Subject } from 'rxjs';
import { BaseDialog } from '../../shared/component/dialog/base/base-dialog';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  private readonly dialogStack: DialogRef<any, any>[] = [];

  private readonly openDialogSubject = new Subject<DialogActionEvent>();
  readonly openDialog$ = this.openDialogSubject.asObservable();

  private readonly saveFormSubject = new Subject<FormGroup>();
  readonly saveForm$ = this.saveFormSubject.asObservable();

  private readonly confirmTaskSubject = new Subject<DialogActionEvent>();
  readonly confirmTask$ = this.confirmTaskSubject.asObservable();

  private readonly reloadSubject = new Subject<DialogActionEvent>();
  readonly reload$ = this.reloadSubject.asObservable();

  constructor(private readonly dialog: Dialog) { }

  openDialog<R, C>(dialogComponent: ComponentType<C>, dialogData?: DialogDataModel<any>): DialogRef<R, C> {
    const dialogRef = this.dialog.open<R, DialogDataModel<any>, C>(dialogComponent, {
      backdropClass: 'bg-space-black/50',
      data: dialogData,
    });

    const prev = this.dialogStack.at(-1);

    this.dialogStack.push(dialogRef);

    if (this.dialogStack.length > 1) {
      prev?.addPanelClass('opacity-0');
    }

    dialogRef.closed.subscribe(() => {
      this.dialogStack.pop();
      prev?.removePanelClass('opacity-0');
    });

    return dialogRef;
  }

  closeDialog(dialogRef: DialogRef) {
    dialogRef.close();
  }

  triggerOpen(sourceRef: DialogRef, dialog: Type<BaseDialog>): void {
    this.openDialogSubject.next({ sourceRef, dialog });
  }

  triggerSave(form: FormGroup): void {
    this.saveFormSubject.next(form);
  }

  triggerConfirm(sourceRef: DialogRef): void {
    this.confirmTaskSubject.next({ sourceRef });
  }

  triggerReload(sourceRef: DialogRef): void {
    this.reloadSubject.next({ sourceRef });
  }
}

interface DialogActionEvent {
  sourceRef: DialogRef;
  dialog?: Type<BaseDialog>;
}
