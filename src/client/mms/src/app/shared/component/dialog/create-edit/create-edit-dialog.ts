import { Component, inject } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { DialogFormDataModel } from '../../../model/dialog/dialog-form-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-create-edit-dialog',
  imports: [],
  templateUrl: './create-edit-dialog.html',
  styleUrl: './create-edit-dialog.css',
})
export abstract class CreateEditDialog<T extends BaseEntityModel> extends BaseDialog {
  data: DialogFormDataModel<T> = inject(DIALOG_DATA);

  get form(): FormGroup {
    return this.data.form;
  }

  onSubmit(): void {
    this.data.form.markAllAsTouched();
    if (this.data.form.invalid) {
      return;
    }
    this.dialogService.triggerSave();
  }

  onReset(): void {
    this.form.reset();
  }
}
