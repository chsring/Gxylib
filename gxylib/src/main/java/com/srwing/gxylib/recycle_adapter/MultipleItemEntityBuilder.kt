package com.srwing.gxylib.recycle_adapter

import java.util.LinkedHashMap

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/27
 * Email: 694177407@qq.com
 */
class MultipleItemEntityBuilder {
    fun setItemType(itemType: Int): MultipleItemEntityBuilder {
        FIELDS[MultipleFields.ITEM_TYPE] = itemType
        return this
    }

    fun setField(key: Any, value: Any): MultipleItemEntityBuilder {
        FIELDS[key] = value
        return this
    }

    fun setFields(map: LinkedHashMap<*, *>?): MultipleItemEntityBuilder {
        FIELDS.putAll(map!!)
        return this
    }

    fun build(): MultipleItemEntity {
        return MultipleItemEntity(FIELDS)
    }

    companion object {
        private val FIELDS = LinkedHashMap<Any, Any>()
    }

    init {
        //先清除之前的数据
        FIELDS.clear()
    }
}