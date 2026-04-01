import { Component, inject } from '@angular/core';
import { BaseDialog } from '../base/base-dialog';
import { DialogDataModel } from '../../../model/dialog/dialog-data.model';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-create-edit-dialog',
  imports: [],
  templateUrl: './create-edit-dialog.html',
  styleUrl: './create-edit-dialog.css',
})
export abstract class CreateEditDialog<T extends BaseEntityModel> extends BaseDialog {
  data: DialogDataModel<T> = inject(DIALOG_DATA);
  protected readonly formBuilder = inject(FormBuilder);

  abstract form: FormGroup;

  get model(): T | undefined {
    return this.data.model;
  }

  onSubmit(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) {
      return;
    }
    this.dialogService.triggerSave(this.form);
  }

  onReset(): void {
    this.form.reset();
  }

  abstract createForm(): FormGroup;

  abstract patchForm(): void;
}
