package example._sharedkernel.time

import java.time.LocalDate

object Today {
    private var now: LocalDate? = null
    fun getDate() : LocalDate {
        now?.let {
            return it
        }
        return LocalDate.now()
    }

    /**
     * À n'utiliser qu'en test
     */
    fun setNow(now: LocalDate) {
        Today.now = now
    }
}