import { inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SpinnerService } from '../ui/spinner/spinner.service';
import { MatSnackBar } from '@angular/material/snack-bar';

export abstract class DialogServiceV2 {
  protected readonly dialog = inject(MatDialog);
  protected readonly spinner = inject(SpinnerService);
  protected readonly snackbar = inject(MatSnackBar);
}
