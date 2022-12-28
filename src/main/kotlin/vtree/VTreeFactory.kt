package vtree

import org.json.JSONException
import org.json.JSONObject
import types.UserFactory
import types.UserType

class VTreeFactory {
    private var typeInstance: UserType? = null
    private val factory: UserFactory

    init {
        factory = UserFactory
        setType(0)
    }

    fun getTypeInstance(): UserType? {
        return typeInstance!!.create()
    }

    fun setType(id: Int) {
        typeInstance = factory.getBuilderByName(UserFactory.types[id].className)
    }

    fun setType(name: String?) {
        typeInstance = factory.getBuilderByName(name)
    }

    fun parseTree(json: JSONObject): VTree {
        val node = parseNode(json)
        val _vtree = VTree()
        _vtree.SetRoot(node)
        return _vtree
    }

    fun createTree(): VTree {
        return VTree()
    }

    private fun parseNode(json: JSONObject): Node {
        val _nodeClassName = json.getString("className")
        assert(_nodeClassName !== Node.className)
        var _valueJSON: JSONObject? = null
        var _value: UserType? = null
        try {
            _valueJSON = json.getJSONObject("value")
            setType(_valueJSON.getString("className"))
            if (typeInstance == null) {
                throw Exception("Wrong type")
            }
            _value = typeInstance!!.parseValue(_valueJSON)
        } catch (e: Exception) {
            println(e)
        }
        val _cnt = json.getInt("cnt")
        var _leftChild: Node? = null
        var _rightChild: Node? = null
        try {
            _leftChild = parseNode(json.getJSONObject("leftChild"))
        } catch (e: JSONException) {
        }
        try {
            _rightChild = parseNode(json.getJSONObject("rightChild"))
        } catch (e: JSONException) {
        }
        val node = Node(_value)
        node.count = _cnt
        node.leftChild = _leftChild
        node.rightChild = _rightChild
        return node
    }
}