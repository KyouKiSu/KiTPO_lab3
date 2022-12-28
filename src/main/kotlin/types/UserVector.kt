package types

import javafx.util.Pair
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class UserVector : UserType {
    private var x: Float?
    private var y: Float?

    constructor() {
        x = null
        y = null
    }

    constructor(_x: Float?, _y: Float?) {
        x = _x
        y = _y
    }

    override fun copy(): UserType? {
        return UserVector(x, y)
    }

    override fun create(): UserType? {
        return UserVector()
    }

    override fun create(values: ArrayList<String?>?): UserType? {
        return UserVector(values!![0]!!.toFloat(), values[1]!!.toFloat())
    }

    override val fields: ArrayList<Pair<String?, String?>?>?
        get() = ArrayList(Arrays.asList(Pair("X", "Float"), Pair("Y", "Float")))
    override val className: String
        get() = Companion.className

    override fun toString(): String {
        return if (x == null || y == null) "null;null" else x.toString() + ";" + y
    }

    override fun parseValue(json: JSONObject?): UserType? {
        return try {
            UserVector(
                json!!.getFloat("x"),
                json.getFloat("y")
            )
        } catch (e: JSONException) {
            null
        }
    }

    override fun packValue(): String? {
        return "{\"className\":\"" + Companion.className + "\"," + "\"x\"" + ":" + x.toString() + "," + "\"y\"" + ":" + y.toString() + "}"
    }

    override fun compareTo(o: Any?): Int {
        val tValue = x!! * x!! + y!! * y!!
        val oValue = (o as UserVector?)!!.x!! * o!!.x!! + o!!.y!! * o!!.y!!
        return tValue.compareTo(oValue)
    }

    companion object {
        const val className = "UserVector"
    }
}