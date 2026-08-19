import { Directive } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseFeatureV2 } from "./base-feature";
import { TableColumnModel } from "../../model/table-column.model";
import { TableMenuOutput } from "../table/table";

@Directive()
export abstract class TableFeature<T extends BaseEntityModel> extends BaseFeatureV2<T> {
  abstract columns: TableColumnModel<T>[];

  onMenuAction(event: TableMenuOutput): void {
    const action = event.action;
    const item = event.item;

    if (action === 'add') {
      this.onAdd();
    } else if (action === 'edit' && item) {
      this.onEdit(item.id);
    } else if (action === 'view' && item) {
      this.onView(item.id);
    } else if (action === 'delete' && item) {
      this.onDelete(item.id);
    }
  }
}
