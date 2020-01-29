package example._sharedkernel.eventbus

class SynchronousEventPublisher : EventPublisher {
    private val pilotageAbonnementSouscritListener = mutableListOf<example.pilotage.commands.AbonnementSouscritListener>()
    private val subscriptionAbonnementSouscritListener = mutableListOf<example.souscription.commands.programmerunemailrecapitulatif.AbonnementSouscritListener>()
    private val listeners = mutableListOf<EventListener<Event>>() // hack to make CollaborationTest pass. This should be a specific event listener e.g EmailProgramméListener, PrixDeBaseAchangéListener etc

    override fun <T : Event> subscribe(listener: EventListener<T>) {
        if (listener is example.pilotage.commands.AbonnementSouscritListener) {
            pilotageAbonnementSouscritListener.add(listener)
        } else if (listener is example.souscription.commands.programmerunemailrecapitulatif.AbonnementSouscritListener) {
            subscriptionAbonnementSouscritListener.add(listener)
        } else {
            listeners.add(listener as EventListener<Event>) // hack, we should repeat what we did above for each event listener
        }
    }


    override fun publish(domainEvents: List<Event>) {
        domainEvents.forEach { event ->
            if (event is example.souscription.domain.AbonnementSouscrit) {
                subscriptionAbonnementSouscritListener.forEach { listener -> listener.notify(event) }
                pilotageAbonnementSouscritListener.forEach { listener -> listener.notify(event) }
            } else {
                listeners.forEach({ listener -> listener.notify(event) }) // do this for each event listener
            }
        }
    }
}

