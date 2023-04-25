package net.rationalstargazer.events.value

import net.rationalstargazer.events.RStaEventSource
import net.rationalstargazer.events.lifecycle.RStaLifecycle

@Suppress("FunctionName")
fun <Value, SourceA, SourceB> RStaChainStrictCombinedItem(
    lifecycle: RStaLifecycle,
    upstreamA: RStaValue<SourceA>,
    upstreamAChangeHandler: (currentEvent: SourceA, lastAEvent: SourceA?, lastBEvent: SourceB?) -> Value,
    upstreamB: RStaValue<SourceB>,
    upstreamBChangeHandler: (currentEvent: SourceB, lastAEvent: SourceA?, lastBEvent: SourceB?) -> Value,
    function: (SourceA, SourceB) -> Value
): RStaValue<Value> {
    return RStaChainGenericCombinedItem(
        lifecycle,
        true,
        upstreamA,
        upstreamAChangeHandler,
        upstreamB,
        upstreamBChangeHandler,
        function
    ).considerStrictValue()
}

@Suppress("FunctionName")
fun <Value, SourceAEvent, SourceBEvent> RStaChainStrictCombinedItem(
    lifecycle: RStaLifecycle,
    upstreamA: RStaEventSource<SourceAEvent>,
    upstreamAChangeHandler: (currentEvent: SourceAEvent, lastAEvent: SourceAEvent?, lastBEvent: SourceBEvent?) -> Value,
    upstreamB: RStaEventSource<SourceBEvent>,
    upstreamBChangeHandler: (currentEvent: SourceBEvent, lastAEvent: SourceAEvent?, lastBEvent: SourceBEvent?) -> Value,
    valueVersion: () -> Long,
    function: () -> Value
): RStaValue<Value> {
    return RStaChainGenericCombinedItem(
        lifecycle,
        true,
        upstreamA,
        upstreamAChangeHandler,
        upstreamB,
        upstreamBChangeHandler,
        valueVersion,
        function
    ).considerStrictValue()
}