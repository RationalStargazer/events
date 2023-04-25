package net.rationalstargazer.events.lifecycle

import kotlinx.coroutines.CoroutineScope

interface LifecycleBasedCoroutineDispatcher : LifecycleBasedSimpleCoroutineDispatcher {
    override val lifecycle: RStaLifecycle
    fun autoCancellableScope(): CoroutineScope?
}