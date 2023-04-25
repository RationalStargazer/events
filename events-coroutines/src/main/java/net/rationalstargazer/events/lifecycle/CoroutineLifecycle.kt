package net.rationalstargazer.events.lifecycle

import kotlin.coroutines.CoroutineContext

class RStaCoroutineLifecycle(
    val lifecycle: RStaLifecycle,
    val coroutineContext: CoroutineContext
)