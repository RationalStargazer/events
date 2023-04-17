package net.rationalstargazer.events.value

import net.rationalstargazer.events.lifecycle.RStaLifecycle

class RStaValueMapper<out Value, in SourceValue> private constructor(
    private val base: RStaChainGenericItem<Value, Value, SourceValue>
) : RStaValue<Value>, RStaGenericValue<Value, Value> by base {

    constructor(
        lifecycle: RStaLifecycle,
        source: RStaGenericValue<SourceValue, SourceValue>,
        mapper: (SourceValue) -> Value
    ) : this(
        RStaChainGenericItem(
            lifecycle,
            source,
            mapper,
            source::checkValue,
        ) {
            mapper(source.value)
        }
    )
}