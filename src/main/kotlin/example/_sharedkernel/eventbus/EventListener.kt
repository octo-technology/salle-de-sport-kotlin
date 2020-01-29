package example._sharedkernel.eventbus

interface EventListener<in T : Event> {
    fun notify(event: T)
}
