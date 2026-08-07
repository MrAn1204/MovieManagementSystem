import { Component, inject } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog'

@Component({
  selector: 'app-base-dialog',
  imports: [],
  templateUrl: './base-dialog.html',
  styleUrl: './base-dialog.css',
})
export abstract class BaseDialogV2 {
  protected readonly dialogRef = inject(MatDialogRef<BaseDialogV2>);

  onClose(result?: any): void {
    this.dialogRef.close(result);
  }
}
