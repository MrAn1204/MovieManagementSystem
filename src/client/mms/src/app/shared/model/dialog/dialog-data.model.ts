import { Type } from "@angular/core";

export interface DialogDataModel {
  title: string;
  contentComponent: Type<unknown>;
  contentInputs: Record<string, unknown>;
}
