package com.mms.mms_api.business.query.user;

import java.util.UUID;

import com.mms.mms_api.business.query.BaseSearchQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Query payload for searching users.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSearchQuery extends BaseSearchQuery {
    private UUID roleId;

}
