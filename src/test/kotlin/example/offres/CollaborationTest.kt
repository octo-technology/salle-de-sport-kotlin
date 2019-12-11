package example.offres

import example._sharedkernel.eventbus.EventListener
import example._sharedkernel.uuid.RandomUUIDGenerator
import example.offres.commands.changerleprixdebase.ChangerLePrixDeBaseCommand
import example.offres.commands.publierunenouvelleformule.PublierUneFormuleCommand
import example.offres.domain.formule.NouvelleFormulePubliée
import example.offres.domain.formule.PrixDeBaseAChangé
import example.offres.infrastructure.DependenciesInjection
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder

class CollaborationTest : StringSpec({
    "Publication d'une formule et changement de son prix de base" {
        // GIVEN
        val eventBus = DependenciesInjection.provideEventPublisher()
        val listener = mockk<EventListener>()
        every { listener.notify(any()) } returns Unit
        eventBus.subscribe(listener)
        val publierUneFormuleHander = DependenciesInjection.providePublierUneFormuleCommandHandler()
        val changerLePrixDeBaseHandler = DependenciesInjection.provideChangerLePrixDeBaseCommandHandler()
        RandomUUIDGenerator.setNextId("id1")

        // WHEN
        publierUneFormuleHander(
            PublierUneFormuleCommand(
                "Piscine",
                "100.0",
                "MENSUELLE"
            )
        )
        changerLePrixDeBaseHandler(
            ChangerLePrixDeBaseCommand(
                "id1",
                "200.0"
            )
        )

        // THEN
        verifyOrder {
            listener.notify(
                NouvelleFormulePubliée(
                    "id1",
                    "Piscine",
                    "100.0",
                    "MENSUELLE"
                )
            )
            listener.notify(
                PrixDeBaseAChangé(
                    "id1",
                    "200.0"
                )
            )
        }

    }
})