package com.osiris.account.query.api.queries;

import com.osiris.account.query.api.dto.EqualityType;
import com.osiris.messaging.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
