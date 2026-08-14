import { inject, Injectable, Type } from '@angular/core';
import { TicketModel } from '../../../model/ticket/ticket.model';
import { BaseDialogV2 } from '../../../shared/component-v2/dialog/base-dialog/base-dialog';
import { EntityService } from '../../entity.service';
import { TicketService } from '../../ticket/ticket.service';
import { TicketDetailV2 } from '../../../feature/ticket-v2/detail/ticket-detail-v2';
import { TicketAddEdit } from '../../../feature/ticket-v2/add-edit/ticket-add-edit';
import { EntityDialogServiceV2 } from '../entity-dialog.service';
import { AuthService } from '../../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class TicketDialogService extends EntityDialogServiceV2<TicketModel> {
  protected override entityName: string = 'Ticket';
  protected override entityService: EntityService<TicketModel> = inject(TicketService);
  protected override detailDialog: Type<BaseDialogV2> = TicketDetailV2;
  protected override formDialog: Type<BaseDialogV2> = TicketAddEdit;

  private readonly authService = inject(AuthService);

  showBookTicketDialog(scheduleId: string) {
    return this.displayAdd({ scheduleId: scheduleId, userId: this.authService.getId() });
  }
}
