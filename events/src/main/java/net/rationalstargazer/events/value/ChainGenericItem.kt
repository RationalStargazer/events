package net.rationalstargazer.events.value

import net.rationalstargazer.events.RStaEventSource
import net.rationalstargazer.events.lifecycle.RStaLifecycle
import net.rationalstargazer.events.lifecycle.RStaWhileAllLifecycle

@Suppress("FunctionName")
fun <Value : Event, Event, SourceEvent> RStaChainGenericItem(
    lifecycle: RStaLifecycle,
    skipSameEvent: Boolean,
    upstreamChangeSource: RStaValueEventSource<SourceEvent>,
    changeHandler: (SourceEvent, emitter: (Event) -> Unit) -> Unit,
    valueVersion: () -> Long,
    function: () -> Value
): RStaGenericValue<Value, Event> {
    return RStaChainGenericItem(
        lifecycle,
        skipSameEvent,
        upstreamChangeSource.asEventSource(),
        changeHandler,
        valueVersion,
        function
    )
}

@Suppress("FunctionName")
fun <Value : Event, Event, SourceEvent> RStaChainGenericItem(
    lifecycle: RStaLifecycle,
    skipSameEvent: Boolean,
    upstreamChangeSource: RStaEventSource<SourceEvent>,
    changeHandler: (SourceEvent, emitter: (Event) -> Unit) -> Unit,
    valueVersion: () -> Long,
    function: () -> Value
): RStaGenericValue<Value, Event> {
    val r = RStaDynamicValue<Value, Event>(
        RStaWhileAllLifecycle(lifecycle, upstreamChangeSource.lifecycle),
        skipSameEvent,
        valueVersion,
        function
    )

    upstreamChangeSource.listen(lifecycle) { sourceEvent ->
        changeHandler(sourceEvent) { handledEvent ->
            r.notifyChanged(handledEvent)
        }
    }

    return r
}

@Suppress("FunctionName")
fun <Value : Event, Event, SourceEvent> RStaChainGenericItem(
    lifecycle: RStaLifecycle,
    skipSameEvent: Boolean,
    upstreamChangeSource: RStaValueEventSource<SourceEvent>,
    changeHandler: (SourceEvent) -> Event,
    valueVersion: () -> Long,
    function: () -> Value
) : RStaGenericValue<Value, Event> {
    return RStaChainGenericItem(
        lifecycle,
        skipSameEvent,
        upstreamChangeSource.asEventSource(),
        changeHandler,
        valueVersion,
        function
    )
}

@Suppress("FunctionName")
fun <Value : Event, Event, SourceEvent> RStaChainGenericItem(
    lifecycle: RStaLifecycle,
    skipSameEvent: Boolean,
    upstreamChangeSource: RStaEventSource<SourceEvent>,
    changeHandler: (SourceEvent) -> Event,
    valueVersion: () -> Long,
    function: () -> Value
) : RStaGenericValue<Value, Event> {
    val r = RStaDynamicValue<Value, Event>(
        RStaWhileAllLifecycle(lifecycle, upstreamChangeSource.lifecycle),
        skipSameEvent,
        valueVersion,
        function
    )

    upstreamChangeSource.listen(lifecycle) { sourceEvent ->
        val handledEvent = changeHandler(sourceEvent)
        r.notifyChanged(handledEvent)
    }

    return r
}