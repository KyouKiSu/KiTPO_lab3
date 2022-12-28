package types

import java.util.*

object UserFactory {
    val types = ArrayList(Arrays.asList(UserInteger(), UserFloat(), UserVector()))
    fun getBuilderByName(name: String?): UserType? {
        for (element in types) {
            if (element.className.equals(name, ignoreCase = true)) {
                return element.create()
            }
        }
        return null
    }
}