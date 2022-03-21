package com.osiris.messaging.infrastructure;

import com.osiris.messaging.domain.BaseEntity;
import com.osiris.messaging.queries.BaseQuery;
import com.osiris.messaging.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
