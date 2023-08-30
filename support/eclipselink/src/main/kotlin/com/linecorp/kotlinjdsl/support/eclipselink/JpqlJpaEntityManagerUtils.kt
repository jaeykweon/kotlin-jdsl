package com.linecorp.kotlinjdsl.support.eclipselink

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.eclipse.persistence.jpa.JpaEntityManager
import kotlin.reflect.KClass

internal object JpqlJpaEntityManagerUtils {
    fun <T : Any> createQuery(
        entityManager: JpaEntityManager,
        query: SelectQuery<T>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        entityManager: JpaEntityManager,
        query: SelectQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered, query.returnType)
    }

    fun <T : Any> createQuery(
        entityManager: JpaEntityManager,
        query: UpdateQuery<T>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered)
    }

    fun <T : Any> createQuery(
        entityManager: JpaEntityManager,
        query: UpdateQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered)
    }

    fun <T : Any> createQuery(
        entityManager: JpaEntityManager,
        query: DeleteQuery<T>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, context)

        return createQuery(entityManager, rendered)
    }

    fun <T : Any> createQuery(
        entityManager: JpaEntityManager,
        query: DeleteQuery<T>,
        queryParams: Map<String, Any?>,
        context: RenderContext,
    ): Query {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered)
    }

    private fun <T : Any> createQuery(
        entityManager: JpaEntityManager,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): TypedQuery<T> {
        return entityManager.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun createQuery(
        entityManager: JpaEntityManager,
        rendered: JpqlRendered,
    ): Query {
        return entityManager.createQuery(rendered.query).apply {
            setParams(this, rendered.params)
        }
    }

    private fun setParams(query: Query, params: JpqlRenderedParams) {
        params.forEach { (name, value) ->
            query.setParameter(name, value)
        }
    }
}
