package net.rationalstargazer.events.value

class RStaValueVersionHelper<CheckHolder : Any>(
    lastCheckHolder: CheckHolder,
    currentCheckHolder: CheckHolder,
    private val check: (CheckHolder) -> Unit
) {
    object DefaultCombinedValue {
        fun create(valueVersionA: () -> Long, valueVersionB: () -> Long): RStaValueVersionHelper<Array<Long>> {
            return RStaValueVersionHelper(arrayOf(0L, 0L), arrayOf(0L, 0L)) {
                it[0] = valueVersionA()
                it[1] = valueVersionB()
            }
        }
    }

    fun checkValueVersion(): Long {
        check(nextCheck)
        if (nextCheck == lastCheck) {
            return version
        }

        val t = lastCheck
        lastCheck = nextCheck
        nextCheck = t
        version++
        return version
    }

    private var lastCheck: CheckHolder = lastCheckHolder
    private var nextCheck: CheckHolder = currentCheckHolder
    private var version: Long = 0L
}