package net.rationalstargazer.events.lifecycle

import net.rationalstargazer.events.queue.EventsQueueDispatcher

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