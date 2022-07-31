package com.linecorp.kotlinjdsl.querydsl.selectFrom

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec

interface SingleSelectFromDsl<T> {
    fun selectFrom(entity: EntitySpec<T>) = selectFrom(false, entity)
    fun selectFrom(distinct: Boolean, entity: EntitySpec<T>): SingleSelectClause<T>
}
