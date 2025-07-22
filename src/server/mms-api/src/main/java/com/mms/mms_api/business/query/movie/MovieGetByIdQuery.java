package com.mms.mms_api.business.query.movie;

import java.util.UUID;

import com.mms.mms_api.business.query.BaseGetByIdQuery;

public class MovieGetByIdQuery extends BaseGetByIdQuery {
    public MovieGetByIdQuery(UUID id) {
        super(id);
    }
}
