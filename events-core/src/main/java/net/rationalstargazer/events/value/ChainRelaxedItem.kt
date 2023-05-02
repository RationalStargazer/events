package net.rationalstargazer.events.value

import net.rationalstargazer.events.RStaEventSource
import net.rationalstargazer.events.lifecycle.RStaLifecycle
import net.rationalstargazer.events.lifecycle.RStaWhileAllLifecycle

class RStaChainRelaxedItem<out Value>(
    private val base: RStaDynamicValue<Value, Any?>
) : RStaValueSource<Value> by base {

    constructor(
        lifecycle: RStaLifecycle,
        upstreamChangeSource: RStaValueEventSource<Any?>,
        changeHandler: () -> Boolean,
        valueVersion: () -> Long,
        function: () -> Value
    ) : this(
        lifecycle,
        upstreamChangeSource.asEventSource(),
        changeHandler,
        valueVersion,
        function
    )

    constructor(
        lifecycle: RStaLifecycle,
        upstreamChangeSource: RStaEventSource<Any?>,
        changeHandler: () -> Boolean,
        valueVersion: () -> Long,
        function: () -> Value
    ) : this(
        RStaDynamicValue(
            RStaWhileAllLifecycle(lifecycle, upstreamChangeSource.lifecycle),
            false,
            valueVersion,
            function
        )
    ) {
        upstreamChangeSource.listen(lifecycle) {
            val notifyDownstream = changeHandler()
            if (notifyDownstream) {
                base.notifyChanged(Unit)
            }
        }
    }

    constructor(
        lifecycle: RStaLifecycle,
        upstreamChangeSource: RStaValueEventSource<Any?>,
        valueVersion: () -> Long,
        function: () -> Value
    ) : this(
        lifecycle,
        upstreamChangeSource.asEventSource(),
        valueVersion,
        function
    )

    constructor(
        lifecycle: RStaLifecycle,
        upstreamChangeSource: RStaEventSource<Any?>,
        valueVersion: () -> Long,
        function: () -> Value
    ) : this(
        RStaDynamicValue(
            RStaWhileAllLifecycle(lifecycle, upstreamChangeSource.lifecycle),
            false,
            valueVersion,
            function
        )
    ) {
        upstreamChangeSource.listen(lifecycle) {
            base.notifyChanged(Unit)
        }
    }
}