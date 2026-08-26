import { Component, inject, OnInit } from '@angular/core';
import { BaseDialogV2 } from '../base-dialog/base-dialog';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogFormDataModel } from '../../../model/dialog/dialog-form-data.model';
import { FormMapper } from '../../../util/form-mapper';
import { DetailEntityService } from '../../../../service/detail-entity.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-add-edit-dialog',
  imports: [],
  templateUrl: './add-edit-dialog.html',
  styleUrl: './add-edit-dialog.css',
})
export abstract class AddEditDialog<T extends BaseEntityModel> extends BaseDialogV2 implements OnInit {
  abstract form: FormGroup;

  protected readonly data = inject<DialogFormDataModel<T>>(MAT_DIALOG_DATA);
  protected readonly formBuilder: FormBuilder = inject(FormBuilder);

  protected abstract entityService: DetailEntityService<T>;

  get title(): string | undefined {
    return this.data.title;
  }

  ngOnInit(): void {
    this.loadItem();
    this.loadOptions?.();
  }

  protected loadItem(): void {
    if (this.data.id) {
      this.spinner.show();

      this.entityService.getById(this.data.id)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe((res) => this.mapForm(res));
    }
  }

  protected abstract mapForm(model: T): void;

  onSubmit(): void {
    this.form.markAllAsTouched();

    if (this.form.invalid) {
      return;
    }

    if (this.data.id) {
      this.updateItem();
    } else {
      this.addItem();
    }
  }

  addItem(): void {
    this.spinner.show();

    this.entityService.create(this.form.getRawValue())
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: (res) => this.onClose(res),
        error: (res) => FormMapper.mapErrorResponse(res, this.form)
      });
  }

  updateItem(): void {
    this.spinner.show();

    this.entityService.update(this.data.id!, this.form.getRawValue())
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe({
        next: (res) => this.onClose(res),
        error: (res) => FormMapper.mapErrorResponse(res, this.form)
      });
  }

  onReset(data?: Record<string, unknown>): void {
    this.form.reset(data);
  }

  loadOptions?(): void;
}
