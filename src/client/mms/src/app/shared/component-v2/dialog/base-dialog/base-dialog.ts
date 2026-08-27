import { Component, inject } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog'
import { SpinnerService } from '../../../../service/ui/spinner/spinner.service';

@Component({
  selector: 'app-base-dialog',
  imports: [],
  templateUrl: './base-dialog.html',
  styleUrl: './base-dialog.css',
})
export abstract class BaseDialogV2 {
  protected readonly dialogRef = inject(MatDialogRef<BaseDialogV2>);
  protected readonly spinner = inject(SpinnerService);

  onClose(result?: any): void {
    this.dialogRef.close(result);
  }

  hideSelf(): void {
    this.dialogRef.addPanelClass('opacity-0!');
    this.dialogRef.addPanelClass('hidden!');
    this.dialogRef.addPanelClass('pointer-events-none!');
  }

  showSelf(): void {
    this.dialogRef.removePanelClass('opacity-0!');
    this.dialogRef.removePanelClass('hidden!');
    this.dialogRef.removePanelClass('pointer-events-none!');
  }

  onRefresh(): void {
    this.onClose('refresh');
  }
}
