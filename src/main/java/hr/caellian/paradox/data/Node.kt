package hr.caellian.paradox.data

import hr.caellian.paradox.ParadoxGenerator
import hr.caellian.paradox.lib.Constants

class Node() {
    var id: String = Constants.NullId
        private set
    val children = mutableListOf<Node>()

    constructor(id: String): this() {
        this.id = id
    }

    init {
        if (id !== Constants.NullId) nodeIdMap[id] = this
    }

    fun getRandomChild(): Node? {
        return if (children.isNotEmpty()) {
            children[ParadoxGenerator.random.nextInt(children.size)]
        } else {
            null
        }
    }

    override fun toString(): String {
        return getRandomChild()?.let {
            id + it
        } ?: id
    }

    companion object {
        @JvmStatic
        val nodeIdMap = mutableMapOf<String, Node>()
    }
}