package types

import javafx.util.Pair
import org.json.JSONObject

interface UserType : Comparable<Any?> {
    fun copy(): UserType?
    fun create(): UserType?
    override fun toString(): String
    val className: String?
    fun parseValue(json: JSONObject?): UserType?
    fun packValue(): String?
    fun create(values: ArrayList<String?>?): UserType?
    val fields: ArrayList<Pair<String?, String?>?>?
    override fun compareTo(o: Any?): Int
}