package test

import types.UserVector
import vtree.VTree
import vtree.VTreeFactory


fun main(args : Array<String>){

    var builder = VTreeFactory()
    builder.setType(0)
    var tree = builder.createTree()

    var x = (1.0).toFloat()
    var y = (2.0).toFloat()
    var number = UserVector(x, y)

    tree.Insert(number)
    tree.Insert(number)

    x = (3.0).toFloat()
    y = (5.0).toFloat()
    number = UserVector(x, y)

    tree.Insert(number)
    tree.Insert(number)

    x = (1.0).toFloat()
    y = (2.0).toFloat()
    number = UserVector(x, y)

    tree.Remove(number)
    tree.Remove(number)

    x = (3.0).toFloat()
    y = (5.0).toFloat()
    number = UserVector(x, y)

    tree.Remove(number)
    tree.Remove(number)

    println(tree.root == null)
}