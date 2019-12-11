package example._sharedkernel.eventbus

// TODO templatiser sur un type d'event particulier ?
interface EventListener {
    fun notify(event: Event)
}
