import { Component } from '@angular/core';
import { CdkMenu, CdkMenuTrigger } from "@angular/cdk/menu";
import { Button } from '../../button/button';
import { ConnectedPosition } from '@angular/cdk/overlay';

@Component({
  selector: 'app-table-actions',
  imports: [Button, CdkMenu, CdkMenuTrigger],
  templateUrl: './table-actions.html',
  styleUrl: './table-actions.css',
})
export class TableActions {
  positions: ConnectedPosition[] = [
    { originX: 'center', originY: 'bottom', overlayX: 'center', overlayY: 'top', offsetY: 4 }
  ];
}
