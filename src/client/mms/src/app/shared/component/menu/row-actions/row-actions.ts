import { Component, input, output } from '@angular/core';
import { ConnectedPosition } from "@angular/cdk/overlay";
import { Button } from "../../button/button";
import { CdkMenu, CdkMenuItem, CdkMenuTrigger } from "@angular/cdk/menu";

@Component({
  selector: 'app-row-actions',
  imports: [Button, CdkMenu, CdkMenuTrigger],
  templateUrl: './row-actions.html',
  styleUrl: './row-actions.css',
})
export class RowActions {
  canView = input<boolean>(false);
  canEdit = input<boolean>(false);
  canDelete = input<boolean>(false);

  viewDetail = output<void>();
  updateItem = output<void>()
  deleteItem = output<void>();

  positions: ConnectedPosition[] = [
    { originX: 'center', originY: 'bottom', overlayX: 'center', overlayY: 'top', offsetY: 4 }
  ];
}
