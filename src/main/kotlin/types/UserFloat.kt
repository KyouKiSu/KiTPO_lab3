package types

import javafx.util.Pair
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class UserFloat : UserType {
    private var value: Float?

    constructor() {
        value = null
    }

    constructor(data: Float?) {
        value = data
    }

    override fun copy(): UserType? {
        return UserFloat(value)
    }

    override fun create(): UserType? {
        return UserFloat()
    }

    override val className: String
        get() = Companion.className

    override fun toString(): String {
        return if (value == null) "null" else value.toString()
    }

    override fun parseValue(json: JSONObject?): UserType? {
        return try {
            UserFloat(
                json!!.getFloat("raw_value")
            )
        } catch (e: JSONException) {
            null
        }
    }

    override fun packValue(): String? {
        return "{\"className\":\"" + Companion.className + "\"," + "\"raw_value\"" + ":" + value.toString() + "}"
    }

    override fun create(values: ArrayList<String?>?): UserType? {
        return UserFloat(values!![0]!!.toFloat())
    }

    override val fields: ArrayList<Pair<String?, String?>?>?
        get() = ArrayList(Arrays.asList(Pair("Value", "Integer")))

    override fun compareTo(o: Any?): Int {
        return value!!.compareTo((o as UserFloat?)!!.value!!)
    }

    companion object {
        const val className = "UserFloat"
    }
}