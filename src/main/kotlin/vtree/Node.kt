package vtree

import types.UserType

class Node(var value: UserType?) {
    var leftChild: Node? = null
    var rightChild: Node? = null
    var count = 0

    fun packValue(): String {
        var packed = "{" +
                "\"className\"" + ":\"" + className + "\"," +
                "\"cnt\"" + ":" + count
        if (value != null) packed += "," + "\"value\"" + ":" + value!!.packValue()
        if (leftChild != null) packed += "," + "\"leftChild\"" + ":" + leftChild!!.packValue()
        if (rightChild != null) packed += "," + "\"rightChild\"" + ":" + rightChild!!.packValue()
        packed += "}"
        return packed
    }

    fun incrementCount() {
        count += 1
    }

    fun decrementCount() {
        count -= 1
    }

    companion object {
        const val className = "Node"
    }
}