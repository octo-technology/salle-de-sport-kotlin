package example._sharedkernel.eventbus

class SynchronousEventPublisher : EventPublisher {
    private val subscribers = mutableListOf<EventListener>()

    override fun subscribe(listener: EventListener) {
        subscribers.add(listener)
    }

    override fun publish(domainEvents: List<Event>) {
        domainEvents.forEach { event ->
            subscribers.forEach { subscriber ->
                subscriber.notify(event)
            }
        }
    }
}

