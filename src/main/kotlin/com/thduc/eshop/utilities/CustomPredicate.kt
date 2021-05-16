package com.thduc.eshop.utilities

import com.querydsl.core.types.dsl.StringPath

import com.querydsl.core.types.dsl.NumberPath

import com.querydsl.core.types.dsl.PathBuilder

import com.querydsl.core.types.dsl.BooleanExpression
import org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric


class CustomPredicate(
    private val criteria: SearchCriteria? = null
) {
    fun predicate(clazz: Class<*>):BooleanExpression?{
            val entityPath: PathBuilder<Any?> = PathBuilder<Any?>(clazz::class.java, "user")
            if (isNumeric(criteria.toString())) {
                val path = entityPath.getNumber(criteria!!.key, Int::class.java)
                val value: Int = criteria!!.value.toString().toInt()
                when (criteria!!.operation) {
                    ":" -> return path.eq(value)
                    ">" -> return path.goe(value)
                    "<" -> return path.loe(value)
                }
            } else {
                val path = entityPath.getString(criteria!!.key)
                if (criteria!!.operation.equals(":",true)) {
                    return path.containsIgnoreCase(criteria!!.value.toString())
                }
            }
            return null
        }
}
