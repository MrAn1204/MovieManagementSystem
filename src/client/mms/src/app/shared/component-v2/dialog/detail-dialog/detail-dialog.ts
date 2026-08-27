import { Component, inject, OnInit, signal } from '@angular/core';
import { BaseDialogV2 } from '../base-dialog/base-dialog';
import { DetailDialogDataModel } from '../../../model/dialog/detail-dialog-data.model';
import { BaseEntityModel } from '../../../model/base-entity.model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuditModel } from '../../../model/audit.model';
import { DetailEntityService } from '../../../../service/detail-entity.service';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-detail-dialog',
  imports: [],
  templateUrl: './detail-dialog.html',
  styleUrl: './detail-dialog.css',
})
export abstract class DetailDialogV2<T extends BaseEntityModel> extends BaseDialogV2 implements OnInit {
  data: DetailDialogDataModel<T> = inject(MAT_DIALOG_DATA);

  private readonly item = signal<T | null>(null);

  protected abstract entityService: DetailEntityService<T>;

  get model(): T | null {
    return this.item();
  }

  get audit(): AuditModel | undefined {
    if (this.model && "audit" in this.model) {
      return this.model.audit as AuditModel;
    }
    return undefined;
  }

  get hasEdit(): boolean {
    return this.data.hasEdit ?? true;
  }

  get hasDelete(): boolean {
    return this.data.hasDelete ?? true;
  }

  ngOnInit(): void {
    this.loadItem();
  }

  protected loadItem(): void {
    if (this.data.id) {
      this.spinner.show();

      this.entityService.getById(this.data.id)
        .pipe(finalize(() => this.spinner.hide()))
        .subscribe((res) => this.item.set(res));
    }
  }

  openUpdate(): void {
    this.onClose('edit');
  }

  openDelete(): void {
    this.onClose('delete');
  }
}
