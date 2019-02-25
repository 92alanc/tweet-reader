package com.alancamargo.tweetreader.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import java.lang.reflect.Field

class SuperclassExclusionStrategy : ExclusionStrategy {

    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        val fieldName = f.name
        val clazz = f.declaringClass

        return isFieldInSuperclass(clazz, fieldName)
    }

    private fun isFieldInSuperclass(subclass: Class<*>, fieldName: String): Boolean {
        var superclass = subclass.superclass

        var field: Field?
        while (superclass != null) {
            field = getField(superclass, fieldName)

            if (field != null) {
                return true
            }

            superclass = superclass.superclass
        }

        return false
    }

    private fun getField(clazz: Class<*>, fieldName: String): Field? {
        return try {
            clazz.getDeclaredField(fieldName)
        } catch (ex: Exception) {
            null
        }
    }

}