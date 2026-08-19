import { Component, inject, OnInit } from '@angular/core';
import { BaseDialogV2 } from '../base-dialog/base-dialog';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogFormDataModel } from '../../../model/dialog/dialog-form-data.model';
import { FormMapper } from '../../../util/form-mapper';

@Component({
  selector: 'app-add-edit-dialog',
  imports: [],
  templateUrl: './add-edit-dialog.html',
  styleUrl: './add-edit-dialog.css',
})
export abstract class AddEditDialog<T extends BaseEntityModel> extends BaseDialogV2 implements OnInit {
  abstract form: FormGroup;

  private readonly data = inject<DialogFormDataModel<T>>(MAT_DIALOG_DATA);

  protected readonly formBuilder: FormBuilder = inject(FormBuilder);

  get model(): T | undefined {
    return this.data.model;
  }

  get title(): string | undefined {
    return this.data.title;
  }

  ngOnInit(): void {
    this.loadOptions?.();
  }

  onSubmit(): void {
    this.form.markAllAsTouched();

    if (this.form.invalid) {
      return;
    }

    this.data.onSubmit(this.form).subscribe({
      next: (res) => {
        this.onClose(res);
      },
      error: (res) => FormMapper.mapErrorResponse(res, this.form)
    });
  }

  onReset(): void {
    this.form.reset();
  }

  loadOptions?(): void;
}
