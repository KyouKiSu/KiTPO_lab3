package types

import javafx.util.Pair
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class UserInteger : UserType {
    private var value: Int?

    constructor() {
        value = null
    }

    constructor(data: Int?) {
        value = data
    }

    override fun copy(): UserType? {
        return UserInteger(value)
    }

    override fun create(): UserType? {
        return UserInteger()
    }

    override fun create(values: ArrayList<String?>?): UserType? {
        return UserInteger(values!![0]!!.toInt())
    }

    override val fields: ArrayList<Pair<String?, String?>?>?
        get() = ArrayList(Arrays.asList(Pair("Value", "Integer")))
    override val className: String
        get() = Companion.className

    override fun parseValue(json: JSONObject?): UserType? {
        return try {
            UserInteger(
                json!!.getInt("raw_value")
            )
        } catch (e: JSONException) {
            null
        }
    }

    override fun packValue(): String? {
        return "{\"className\":\"" + Companion.className + "\"," + "\"raw_value\"" + ":" + value.toString() + "}"
    }

    override fun toString(): String {
        return if (value == null) "null" else value.toString()
    }

    override fun compareTo(o: Any?): Int {
        return value!!.compareTo((o as UserInteger?)!!.value!!)
    }

    companion object {
        const val className = "UserInteger"
    }
}