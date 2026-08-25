import { Directive, inject, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseDialogV2 } from "../dialog/base-dialog/base-dialog";
import { RoleConfigModel } from "../../model/role-config.model";
import { SpinnerService } from "../../../service/ui/spinner/spinner.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { EntityService } from "../../../service/entity.service";
import { EntityDialogServiceV2 } from "../../../service/dialog-v2/entity-dialog.service";
import { finalize } from "rxjs";

@Directive()
export abstract class BaseFeatureV2<T extends BaseEntityModel> {
  abstract entityName: string;

  protected abstract readonly entityService: EntityService<T>;
  protected abstract readonly dialogService: EntityDialogServiceV2<T>;

  protected readonly spinner = inject(SpinnerService);
  protected readonly snackbar = inject(MatSnackBar);

  abstract roleConfig: RoleConfigModel;

  abstract contentAddEdit: Type<BaseDialogV2>;
  abstract contentDetail: Type<BaseDialogV2>;

  onAdd(): void {
    this.onDialogOpen?.();

    this.dialogService.displayAdd()
      .pipe(finalize(() => this.onDialogClose?.()))
      .subscribe((result) => this.handleAddResult?.(result));
  }

  onEdit(id: string): void {
    this.onDialogOpen?.();

    this.dialogService.displayEdit(id)
      .pipe(finalize(() => this.onDialogClose?.()))
      .subscribe((result) => this.handleEditResult?.(result));
  }

  onView(id: string): void {
    this.onDialogOpen?.();

    this.dialogService.displayInfo(id)
      .pipe(finalize(() => this.onDialogClose?.()))
      .subscribe(result => this.handleViewResult(result, id));
  }

  onDelete(id: string): void {
    this.onDialogOpen?.();

    this.dialogService.displayDelete(() => this.entityService.delete(id))
      .pipe(finalize(() => this.onDialogClose?.()))
      .subscribe((result) => this.handleDeleteResult?.(result));
  }

  protected onDialogOpen?(): void;

  protected onDialogClose?(): void;

  protected handleViewResult(result: any, id: string): void {
    if (result === 'edit') {
      this.onEdit(id);
    } else if (result === 'delete') {
      this.onDelete(id);
    } else if (result === 'refresh') {
      this.onView(id);
    }
  }

  protected handleAddResult?(result: any): void;

  protected handleEditResult?(result: any): void;

  protected handleDeleteResult?(result: any): void;
}
