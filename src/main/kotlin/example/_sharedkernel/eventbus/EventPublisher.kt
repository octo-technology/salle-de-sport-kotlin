package example._sharedkernel.eventbus

interface EventPublisher {
    fun publish(domainEvents: List<Event>)
    fun subscribe(listener: EventListener)
}

