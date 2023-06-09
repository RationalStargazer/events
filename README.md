
Not production-ready.

Event-handling framework that could be a better alternative than JetPack's LiveData.

Drawbacks of LiveData:

Summary:

LiveData represents a complex concept of "last value which is only delivered in foreground". It can be used reliably only if you need exactly this thing. For other cases you need another solution. Some of them can be achieved with minor (but still not ideal) workarounds. Some of them (like accumulation of not delivered values) are nearly impossible.

In details:

- LiveData has a lot of dependencies (not cross-platform, tightly coupled with Android ecosystem, not portable)
- developed as a tool for communication with a view layer (not suitable for data or business layers)
- can only represent a concept of changeable value. Not suitable for delivering events.
- is designed with the idea that all changes that have happened when the application is in background should be ignored, so it is not suitable for other cases (for example if you want to accumulate all intermediate changes and handle them all at once when the app returned to foreground).
- is designed with the idea that only last value is important. If for example an event leads to several changes of the same value in quick sequence (before all listener will be called) some listeners can be called only with the last value, intermediate values will be skipped. For example if you have `val ints: LiveData<Int>` and you want to know maximum value of all times you have to be careful because LiveData doesn't guarantee that all intermediate values will be delivered even if the lifecycle will be active all the time.
- not suitable for representation of interdependent or aggregated data as its implementation doesn't allow to change a value without immediate invocation of listeners (changing one value after another creates a moment of time when data is in inconsistent state)
- not completely null-safe

Pros of LiveData:

- Very convenient in its use case
- Keeps view layer in updated state, takes into account fragment's lifecycle without any additional work
- Automatically removes listeners if lifecycle is destroyed.

`net.rationalstargazer.events` package keeps all pros of LiveData and have no listed drawbacks

- Instead of encapsulating all behavior into a single class the concept is splitted into (1) "observable thing" which can be a value ([RStaValue](events/src/main/java/net/rationalstargazer/events/value/GenericValue.kt)) or just a simple event ([RStaEventSource](events/src/main/java/net/rationalstargazer/events/EventSource.kt)), and (2) "it can be listened using different (completely customizable) logic (for example [RStaValueConsumer](events/src/main/java/net/rationalstargazer/events/listeners/Listeners.kt))".

- All events are completely sequential in all circumstances (all listeners of previous event are called before the listeners of the next one will be called). It is done by implementing dedicated global event queue ([RStaEventsQueueDispatcher](events/src/main/java/net/rationalstargazer/events/queue/EventsQueueDispatcher.kt)). 

- "Push event, pull data" concept ensures that all data is consistent regardless of complexity and interdependence (see [RStaGenericValue](events/src/main/java/net/rationalstargazer/events/value/GenericValue.kt) for details about "push event, pull data")

- [RStaLifecycle](events/src/main/java/net/rationalstargazer/events/lifecycle/Lifecycle.kt) was implemented to be used in place of Android's Lifecycle. When lifecycle is finished standard implementations of [RStaEventSource] and [RStaValueEventSource] automatically remove their listeners. Also event (and value) sources can have their own separate lifecycle, after their lifecycle is finished listeners will also be removed.

There is no maven repository as the work is not ready for public release. To try `net.rationalstargazer.events` you may download source code and import `events` module to your project, it is cross-platform. `events-android` module contains Android-specific features, such as Android Looper-based [RStaAndroidLooperHandler](events-android/src/main/java/net/rationalstargazer/events/android/queue/RStaAndroidLooperHandler.kt) and JetPack's `Lifecycle` to `RStaLifecycle` adapter.

Module `events-aliases` has type aliases for all common types of the framework without "RSta" prefix (RStaValue -> Value, RStaLifecycle -> Lifecycle) if you prefer it this way. (Other modules with `-aliases` suffix serve the same purpose)