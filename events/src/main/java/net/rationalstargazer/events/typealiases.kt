package net.rationalstargazer.events

import net.rationalstargazer.events.lifecycle.RStaControlledLifecycle
import net.rationalstargazer.events.lifecycle.RStaInnerLifecycle
import net.rationalstargazer.events.lifecycle.RStaLifecycle
import net.rationalstargazer.events.lifecycle.RStaLifecycleDispatcher
import net.rationalstargazer.events.lifecycle.RStaLifecycleMarker
import net.rationalstargazer.events.lifecycle.RStaLifecycleScope
import net.rationalstargazer.events.lifecycle.RStaSuspendableLifecycle
import net.rationalstargazer.events.lifecycle.RStaWhileAllLifecycle
import net.rationalstargazer.events.queue.RStaEventsQueueDispatcher
import net.rationalstargazer.events.queue.RStaQueueGenericHandler
import net.rationalstargazer.events.value.RStaDynamicValue
import net.rationalstargazer.events.value.RStaGenericValue
import net.rationalstargazer.events.value.RStaValue
import net.rationalstargazer.events.value.RStaValueDispatcher
import net.rationalstargazer.events.value.RStaValueEventSource
import net.rationalstargazer.events.value.RStaValueGenericConsumer
import net.rationalstargazer.events.value.RStaValueMapper
import net.rationalstargazer.events.value.RStaValueSource
import net.rationalstargazer.events.value.RStaVariable

typealias QueueGenericHandler = RStaQueueGenericHandler
typealias EventsQueueDispatcher = RStaEventsQueueDispatcher
typealias LifecycleMarker = RStaLifecycleMarker
typealias LifecycleScope = RStaLifecycleScope
typealias Lifecycle = RStaLifecycle
typealias SuspendableLifecycle = RStaSuspendableLifecycle
typealias ControlledLifecycle = RStaControlledLifecycle
typealias LifecycleDispatcher = RStaLifecycleDispatcher

inline fun InnerLifecycle(lifecycle: LifecycleScope): ControlledLifecycle {
    return RStaInnerLifecycle(lifecycle)
}

/**
 * Returns the lifecycle which is an intersection of given `lifecycles`.
 * When any of the `lifecycles` will become `finished` the result lifecycle will be finished.
 * Technically the result lifecycle can be finished at the same time or slightly before any of `lifecycles` will become `finished`.
 */
inline fun WhileAllLifecycle(coordinator: EventsQueueDispatcher, lifecycles: Array<Lifecycle>): Lifecycle {
    return RStaWhileAllLifecycle(coordinator, lifecycles)
}

inline fun WhileAllLifecycle(lifecycle0: Lifecycle, lifecycle1: Lifecycle): Lifecycle {
    return RStaWhileAllLifecycle(lifecycle0, lifecycle1)
}

inline fun WhileAllLifecycle(lifecycle0: Lifecycle, lifecycle1: Lifecycle, lifecycle2: Lifecycle): Lifecycle {
    return RStaWhileAllLifecycle(lifecycle0, lifecycle1, lifecycle2)
}

typealias EventSource<T> = RStaEventSource<T>
typealias ValueEventSource<T> = RStaValueEventSource<T>
typealias EventDispatcher<T> = RStaEventDispatcher<T>

typealias GenericValue<Value, Event> = RStaGenericValue<Value, Event>
typealias ValueSource<T> = RStaValueSource<T>
typealias Value<T> = RStaValue<T>
typealias Variable<T> = RStaVariable<T>

typealias ValueGenericConsumer<T> = RStaValueGenericConsumer<T>
typealias ValueDispatcher<T> = RStaValueDispatcher<T>
typealias ValueMapper<Value, SourceValue> = RStaValueMapper<Value, SourceValue>
typealias DynamicValue<Value, Event> = RStaDynamicValue<Value, Event>