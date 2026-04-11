package com.mms.mms_api.business.command.promotion;

import com.mms.mms_api.business.command.BaseDeleteCommand;
import java.util.UUID;

/**
 * Command payload for deleting promotions.
 */
public class PromotionDeleteCommand extends BaseDeleteCommand {
    public PromotionDeleteCommand(UUID id) {
        super(id);
    }
}
