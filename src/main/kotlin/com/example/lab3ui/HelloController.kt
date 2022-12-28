package com.example.lab3ui

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import org.json.JSONObject
import types.UserFactory
import types.UserType
import vtree.VTree
import vtree.VTreeFactory
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class HelloController {
    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }

    var vTreeFactory = VTreeFactory()
    var tree: VTree? = null

    @FXML
    private lateinit var userTypeChoiceBox: ChoiceBox<String>

    @FXML
    private lateinit var showTextArea: TextArea

    @FXML
    private lateinit var userTypeFieldGrid: GridPane

    @FXML
    private lateinit var changeUserTypeButton: Button

    @FXML
    private lateinit var filenameField: TextField

    fun initialize() {
        // init choice box with user types
        val list = FXCollections.observableArrayList<String?>()
        for (_class in UserFactory.types) {
            list.add(_class.className)
        }
        println(list)
        userTypeChoiceBox.items = list
        userTypeChoiceBox.value = list[0]
    }
    protected fun initTree() {
        setFactoryClasses()
        resetTree()
        refreshTree()
    }

    protected fun resetTree() {
        tree = vTreeFactory.createTree()
    }

    protected fun refreshTree() {
        showTextArea!!.text = tree!!.print()
    }

    protected fun setFactoryClasses() {
        val selectedClass = userTypeChoiceBox!!.value as String
        vTreeFactory.setType(selectedClass)
    }

    protected fun createElement(): UserType? {
        val values = ArrayList<String?>()
        for (element in userTypeFieldGrid!!.children) {
            if (element.javaClass == TextField::class.java) {
                val field = element as TextField
                values.add(field.text)
            }
        }
        val builder = vTreeFactory.getTypeInstance()
        return builder!!.create(values)
    }

    protected fun insertElement(element: UserType?) {
        tree!!.Insert(element)
    }

    protected fun rebalanceTree() {
        tree!!.rebalance()
    }

    protected fun removeElement(element: UserType?) {
        tree!!.Remove(element!!)
    }


    @FXML
    protected fun onChangeUserTypeButtonClick() {
        // update selected class in factory
        setFactoryClasses()

        // update grid with fields from factory
        val selectedClass = userTypeChoiceBox!!.value as String
        val userTypeFieldInfo = UserFactory.getBuilderByName(selectedClass)!!.fields
        userTypeFieldGrid!!.children.clear()
        var row = 0
        for (field in userTypeFieldInfo!!) {
            val label = Label(field!!.key)
            val text = TextField()
            text.promptText = field.value
            userTypeFieldGrid.add(label, 0, row)
            userTypeFieldGrid.add(text, 1, row)
            row += 1
        }
        initTree()
    }

    @FXML
    protected fun onAddButtonClick() {
        val element = createElement()
        insertElement(element)
        refreshTree()
    }

    @FXML
    protected fun onRebalanceButtonClick() {
        rebalanceTree()
        refreshTree()
    }

    @FXML
    protected fun onRemoveButtonClick() {
        val element = createElement()
        removeElement(element)
        refreshTree()
    }

    @FXML
    protected fun onResetButtonClick() {
        resetTree()
        refreshTree()
    }

    @FXML
    protected fun onRefreshButtonClick() {
        refreshTree()
    }

    @FXML
    protected fun onShowJSONButtonClick() {
        showTextArea!!.text = JSONObject(tree!!.packValue()).toString(4)
    }

    @FXML
    protected fun onToJSONButtonClick() {
        val packed = tree!!.packValue()
        val path = filenameField!!.text
        try {
            Files.write(Paths.get(path), packed.toByteArray())
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        refreshTree()
    }

    @FXML
    protected fun onFromJSONButtonClick() {
        val path = filenameField!!.text
        tree = try {
            val json = Files.readString(Paths.get(path))
            vTreeFactory.parseTree(JSONObject(json))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        refreshTree()
    }
}