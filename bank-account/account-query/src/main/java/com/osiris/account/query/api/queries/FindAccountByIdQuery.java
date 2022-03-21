package com.osiris.account.query.api.queries;

import com.osiris.messaging.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {
    private String id;
}
