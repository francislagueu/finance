package com.osiris.messaging.queries;

import com.osiris.messaging.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery>{
    List<BaseEntity> handle(T query);
}
