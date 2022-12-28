package vtree

import types.UserType
import java.util.*

class VTree {
    var root: Node? = null
    fun Insert(value: UserType?) {
        var value = value
        if (root == null) {
            val newNode = Node(value)
            newNode.count = 1
            root = newNode
        } else {
            var current = root
            var parent: Node = root as Node
            var left = true
            while (true) {
                if (current == null) {
                    val newNode = Node(value)
                    newNode.count = 1
                    if (left) {
                        parent.leftChild = newNode
                    } else {
                        parent.rightChild = newNode
                    }
                    return
                }
                current.incrementCount()
                val cmpResult = value!!.compareTo(current.value)
                if (cmpResult < 0) {
                    val c = current.value
                    current.value = value
                    value = c
                }
                parent = current
                if (current.leftChild == null || current.rightChild != null && current.leftChild!!.count < current.rightChild!!.count) {
                    current = current.leftChild
                    left = true
                } else {
                    current = current.rightChild
                    left = false
                }
            }
        }
    }

    fun find(value: UserType): Node? {
        val nodes = Stack<Node?>()
        nodes.push(root)
        val states = Stack<Int>()
        states.push(0)
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null
            }
            val current = nodes.peek()
            if (current == null) {
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return null
                }
                //                Integer l = Integer.valueOf(states.peek().intValue() + 1);
//                states.pop();
//                states.push(l);
                continue
            }
            val cmpResult = value.compareTo(current.value)
            if (cmpResult == 0) {
                return current
            }
            if (cmpResult < 0 || states.peek() == 2) { // +-
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return null
                }
                //                Integer l = Integer.valueOf(states.peek().intValue() + 1);
//                states.pop();
//                states.push(l);
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go left
                    states.push(0)
                    nodes.push(current.leftChild)
                    continue
                }
                if (states.peek() == 1) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go right
                    states.push(0)
                    nodes.push(current.rightChild)
                    continue
                }
                //                if(states.peek()==2){
//                    // go up
//                }
            }
        }
    }

    fun ForEach(func: DoWith) {
        val nodes = Stack<Node?>()
        nodes.push(root)
        val states = Stack<Int>()
        states.push(0)
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return
            }
            val current = nodes.peek()
            if (current == null) {
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return
                }
                continue
            }
            if (states.peek() == 2) { // +-
                func.doWith(current.value)
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return
                }
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go left
                    states.push(0)
                    nodes.push(current.leftChild)
                    continue
                }
                if (states.peek() == 1) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go right
                    states.push(0)
                    nodes.push(current.rightChild)
                    continue
                }
            }
        }
    }

    fun GetByIndex(index: Int): UserType? {
        val elementList: Array<Vector<UserType?>> = arrayOf<Vector<UserType?>>(Vector<UserType?>())
        val curindex = intArrayOf(0)
        val getElement: DoWith = object : DoWith {
            override fun doWith(obj: Any?) {
                if (index == curindex[0]) {
                    elementList[0].add(obj as UserType?)
                }
                curindex[0] += 1
            }
        }
        ForEach(getElement)
        return if (elementList[0].size > 0) {
            elementList[0][0]
        } else null
    }

    fun GetByIndexOld(index: Int): UserType? {
        var currentIndex = 0
        val nodes = Stack<Node?>()
        nodes.push(root)
        val states = Stack<Int>()
        states.push(0)
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null
            }
            val current = nodes.peek()
            if (current == null) {
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return null
                }
                continue
            }
            if (states.peek() == 2) { // +-
                if (currentIndex == index) {
                    return current.value
                }
                currentIndex += 1
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return null
                }
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go left
                    states.push(0)
                    nodes.push(current.leftChild)
                    continue
                }
                if (states.peek() == 1) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go right
                    states.push(0)
                    nodes.push(current.rightChild)
                    continue
                }
            }
        }
    }

    fun findParents(value: UserType): Stack<Node?>? {
        val nodes = Stack<Node?>()
        nodes.push(root)
        val states = Stack<Int>()
        states.push(0)
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null
            }
            val current = nodes.peek()
            if (current == null) {
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return null
                }
                continue
            }
            val cmpResult = value.compareTo(current.value)
            if (cmpResult == 0) {
                nodes.pop()
                return if (nodes.empty()) {
                    null
                } else nodes
            }
            if (cmpResult < 0 || states.peek() == 2) { // +-
                // go back?
                nodes.pop()
                states.pop()
                // inc last
                if (nodes.empty()) {
                    return null
                }
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go left
                    states.push(0)
                    nodes.push(current.leftChild)
                    continue
                }
                if (states.peek() == 1) {
                    // inc last
                    val l = Integer.valueOf(states.peek().toInt() + 1)
                    states.pop()
                    states.push(l)
                    // go right
                    states.push(0)
                    nodes.push(current.rightChild)
                    continue
                }
                //                if(states.peek()==2){
//                    // go up
//                }
            }
        }
    }

    fun RemoveByIndex(index: Int) {
        val value = GetByIndex(index)
        value?.let { Remove(it) }
    }

    fun Remove(value: UserType): UserType? {
        val parents = findParents(value)
        if (parents == null || parents.empty()) { // root or not found
            if (root!!.value!!.compareTo(value) == 0) {
                if (root!!.rightChild == null && root!!.leftChild == null) {
                    val toreturn = root!!.value
                    root = null
                    return toreturn
                } // if any child present, continue below
            } else {
                // no element found
                return null
            }
        }
        var current: Node?
        var parent: Node? = null
        val childs = Stack<Node?>()
        val states = Stack<Int>() // 1 left 0 right
        if (parents == null) {
            current = root
        } else {
            parent = parents.peek()
            current = if (parent!!.rightChild != null && value.compareTo(parent.rightChild!!.value) == 0) {
                parent.rightChild
            } else {
                parent.leftChild
            }
        }
        childs.push(current)
        var shouldReturn = false
        var cvalue: UserType? = value
        var deleted: Node? = null
        while (true) {
            if (childs.empty()) {
                break
            }
            current = childs.peek()
            if (shouldReturn) {
                if (parent == null) { // remove root
                }
                if (parent != null && parent.rightChild == deleted) {
                    parent.rightChild = null
                    break
                }
                if (parent != null && parent.leftChild == deleted) {
                    parent.leftChild = null
                    break
                }
                if (childs.peek()!!.leftChild == deleted) {
                    childs.peek()!!.leftChild = null
                }
                if (childs.peek()!!.rightChild == deleted) {
                    childs.peek()!!.rightChild = null
                }
                val b = childs.peek()!!.value
                childs.peek()!!.value = cvalue
                childs.peek()!!.decrementCount()
                cvalue = b
                childs.pop()
                continue
            }
            if (current!!.leftChild == null && current.rightChild == null) {
                deleted = current
                cvalue = current.value
                shouldReturn = true
                continue
            }
            if (childs.peek()!!.leftChild == null || childs.peek()!!.rightChild != null && childs.peek()!!.leftChild!!.value!!.compareTo(
                    childs.peek()!!.rightChild!!.value
                ) > 0
            ) {
                // going right
                childs.push(childs.peek()!!.rightChild)
            } else {
                // going left
                childs.push(childs.peek()!!.leftChild)
            }
        }
        while (true) {
            if (parents == null || parents.empty()) {
                break
            }
            parents.peek()!!.decrementCount()
            parents.pop()
        }
        return value
    }

    fun rebalance() {
        val elementList = Vector<UserType?>()
        val getElement: DoWith = object : DoWith {
            override fun doWith(obj: Any?) {
                elementList.add(obj as UserType?)
            }
        }
        ForEach(getElement)
        root = null
        for (element in elementList) {
            Insert(element)
        }
    }

    fun print(): String {
        return if (root == null) {
            "Empty tree"
        } else print("", root, false)
    }

    private fun print(prefix: String, n: Node?, isLeft: Boolean): String {
        var result = ""
        if (n != null) {
            result += """$prefix${if (isLeft) "├── " else "└── "}${n.value}(${n.count})
"""
            var new_prefix = prefix + if (isLeft) "|   " else "    "
            result += print(new_prefix, n.leftChild, true)
            result += print(new_prefix, n.rightChild, false)
        }
        return result
    }

    fun GetRoot(): UserType? {
        return root!!.value
    }

    fun SetRoot(_root: Node?) {
        root = _root
    }

    fun packValue(): String {
        return if (root == null) "{}" else root!!.packValue()
    }
}