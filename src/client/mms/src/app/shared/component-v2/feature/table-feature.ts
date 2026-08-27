import { Directive } from "@angular/core";
import { BaseEntityModel } from "../../model/base-entity.model";
import { BaseFeatureV2 } from "./base-feature";
import { TableColumnModel } from "../../model/table-column.model";
import { TableMenuOutput } from "../table/table";
import { COMMON_MENU_ITEMS, MenuItem } from "../menu/menu";

@Directive()
export abstract class TableFeature<T extends BaseEntityModel> extends BaseFeatureV2<T> {
  abstract columns: TableColumnModel<T>[];

  protected get tableMenuItems(): MenuItem[] {
    return [
      COMMON_MENU_ITEMS.ADD,
    ];
  }

  protected get rowMenuItems(): MenuItem[] {
    return [
      COMMON_MENU_ITEMS.VIEW,
      COMMON_MENU_ITEMS.EDIT,
      COMMON_MENU_ITEMS.DELETE,
    ];
  }

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
