package example._sharedkernel.eventbus

interface EventPublisher {
    fun publish(domainEvents: List<Event>)
    fun <T: Event> subscribe(listener: EventListener<T>)
}
