package example.offres.infrastructure

import example._sharedkernel.eventbus.EventPublisher
import example._sharedkernel.eventbus.SynchronousEventPublisher
import example.offres.commands.FormuleRepository
import example.offres.commands.changerleprixdebase.ChangerLePrixDeBaseCommandHandler
import example.offres.commands.publierunenouvelleformule.PublierUneFormuleCommandHandler

object DependenciesInjection {
    private var eventPublisher: EventPublisher? = null
    private var formuleRepository: FormuleRepository? = null
    private var publierUneFormuleCommandHandler: PublierUneFormuleCommandHandler? = null
    private var changerLePrixDeBaseCommandHandler: ChangerLePrixDeBaseCommandHandler? = null

    fun provideEventPublisher(): EventPublisher {
        if (eventPublisher == null) {
            eventPublisher = SynchronousEventPublisher()
        }
        return eventPublisher!!
    }

    fun provideFormuleRepository(): FormuleRepository {
        if (formuleRepository == null) {
            formuleRepository = InMemoryFormuleRepository()
        }
        return formuleRepository!!
    }

    fun providePublierUneFormuleCommandHandler(): PublierUneFormuleCommandHandler {
        if (publierUneFormuleCommandHandler == null) {
            publierUneFormuleCommandHandler = PublierUneFormuleCommandHandler(
                provideFormuleRepository(),
                provideEventPublisher()
            )
        }
        return publierUneFormuleCommandHandler!!
    }

    fun provideChangerLePrixDeBaseCommandHandler(): ChangerLePrixDeBaseCommandHandler {
        if (changerLePrixDeBaseCommandHandler == null) {
            changerLePrixDeBaseCommandHandler = ChangerLePrixDeBaseCommandHandler(
                provideFormuleRepository(),
                provideEventPublisher()
            )
        }
        return changerLePrixDeBaseCommandHandler!!
    }
}