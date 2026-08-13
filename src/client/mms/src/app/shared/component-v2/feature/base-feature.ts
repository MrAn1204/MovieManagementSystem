import { Directive, inject, Type } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseDialogV2 } from "../dialog/base-dialog/base-dialog";
import { RoleConfigModel } from "../../model/role-config.model";
import { FormBuilder } from "@angular/forms";
import { SpinnerService } from "../../../service/ui/spinner/spinner.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { EntityService } from "../../../service/entity.service";
import { EntityDialogServiceV2 } from "../../../service/dialog-v2/entity-dialog.service";

@Directive()
export abstract class BaseFeatureV2<T extends BaseEntityModel> {
  abstract entityName: string;

  abstract contentAddEdit: Type<BaseDialogV2>;
  abstract contentDetail: Type<BaseDialogV2>;

  abstract roleConfig: RoleConfigModel;

  protected readonly formBuilder = inject(FormBuilder);
  protected readonly spinner = inject(SpinnerService);
  protected readonly snackbar = inject(MatSnackBar);

  protected abstract readonly entityService: EntityService<T>;
  protected abstract readonly dialogService: EntityDialogServiceV2<T>;

  onAdd(): void {
    this.dialogService.showAddEditDialog();
  }

  onEdit(id: string): void {
    this.dialogService.showAddEditDialog(id);
  }

  onView(id: string): void {
    this.dialogService.showDetailDialog(id).subscribe(result => {
      if (result === 'edit') {
        this.onEdit(id);
      } else if (result === 'delete') {
        this.onDelete(id);
      }
    });
  }

  onDelete(id: string): void {
    this.dialogService.showDeleteDialog(id);
  }
}
