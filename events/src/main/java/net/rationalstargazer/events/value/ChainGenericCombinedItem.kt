package net.rationalstargazer.events.value

import net.rationalstargazer.events.RStaEventSource
import net.rationalstargazer.events.lifecycle.RStaLifecycle
import net.rationalstargazer.events.lifecycle.RStaWhileAllLifecycle

@Suppress("FunctionName")
fun <Value : Event, Event, SourceAValue : SourceAEvent, SourceAEvent, SourceBValue : SourceBEvent, SourceBEvent> RStaChainGenericCombinedItem(
    lifecycle: RStaLifecycle,
    skipSameEvent: Boolean,
    upstreamA: RStaGenericValue<SourceAValue, SourceAEvent>,
    upstreamAChangeHandler: (currentEvent: SourceAEvent, lastAEvent: SourceAEvent?, lastBEvent: SourceBEvent?) -> Event,
    upstreamB: RStaGenericValue<SourceBValue, SourceBEvent>,
    upstreamBChangeHandler: (currentEvent: SourceBEvent, lastAEvent: SourceAEvent?, lastBEvent: SourceBEvent?) -> Event,
    function: (SourceAValue, SourceBValue) -> Value
): RStaGenericValue<Value, Event> {
    val versionHelper = RStaValueVersionHelper.DefaultCombinedValue.create(
        upstreamA::checkValueVersion,
        upstreamB::checkValueVersion
    )

    return RStaChainGenericCombinedItem(
        lifecycle,
        skipSameEvent,
        upstreamA.asEventSource(),
        upstreamAChangeHandler,
        upstreamB.asEventSource(),
        upstreamBChangeHandler,
        versionHelper::checkValueVersion
    ) {
        function(upstreamA.value, upstreamB.value)
    }
}

@Suppress("FunctionName")
fun <Value : Event, Event, SourceAEvent, SourceBEvent> RStaChainGenericCombinedItem(
    lifecycle: RStaLifecycle,
    skipSameEvent: Boolean,
    upstreamA: RStaEventSource<SourceAEvent>,
    upstreamAChangeHandler: (currentEvent: SourceAEvent, lastAEvent: SourceAEvent?, lastBEvent: SourceBEvent?) -> Event,
    upstreamB: RStaEventSource<SourceBEvent>,
    upstreamBChangeHandler: (currentEvent: SourceBEvent, lastAEvent: SourceAEvent?, lastBEvent: SourceBEvent?) -> Event,
    valueVersion: () -> Long,
    function: () -> Value
): RStaGenericValue<Value, Event> {
    val r = RStaDynamicValue<Value, Event>(
        RStaWhileAllLifecycle(lifecycle.coordinator, arrayOf(lifecycle, upstreamA.lifecycle, upstreamB.lifecycle)),
        skipSameEvent,
        valueVersion,
        function
    )

    var lastA: SourceAEvent? = null
    var lastB: SourceBEvent? = null

    upstreamA.listen(lifecycle) { sourceEvent ->
        val event = upstreamAChangeHandler(sourceEvent, lastA, lastB)
        lastA = sourceEvent
        r.notifyChanged(event)
    }

    upstreamB.listen(lifecycle) { sourceEvent ->
        val event = upstreamBChangeHandler(sourceEvent, lastA, lastB)
        lastB = sourceEvent
        r.notifyChanged(event)
    }

    return r
}