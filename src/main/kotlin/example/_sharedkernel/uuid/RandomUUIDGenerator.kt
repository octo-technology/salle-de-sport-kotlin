package example._sharedkernel.uuid

import java.util.*

object RandomUUIDGenerator {
    private var nextId: String? = null
    fun generateId() : String {
        nextId?.let {
            return it
        }
        return UUID.randomUUID().toString()
    }

    /**
     * À n'utiliser qu'en test
     */
    fun setNextId(nextId: String) {
        RandomUUIDGenerator.nextId = nextId
    }
}