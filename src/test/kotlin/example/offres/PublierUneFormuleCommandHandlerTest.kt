package example.offres

import example._sharedkernel.eventbus.EventPublisher
import example.offres.domain.formule.NouvelleFormulePubliée
import example.offres.commands.publierunenouvelleformule.PublierUneFormuleCommand
import example.offres.commands.publierunenouvelleformule.PublierUneFormuleCommandHandler
import example.offres.commands.publierunenouvelleformule.PublierUneFormuleCommandHandler.Result.Failure
import example.offres.commands.publierunenouvelleformule.PublierUneFormuleCommandHandler.Result.Success
import example._sharedkernel.uuid.RandomUUIDGenerator
import example.offres.commands.FormuleRepository
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class PublierUneFormuleCommandHandlerTest : StringSpec({
    "Devrait persiter une nouvelle formule et publier un evenement" {
        // GIVEN
        RandomUUIDGenerator.setNextId("id")
        val repository = mockk<FormuleRepository>()
        every { repository.persist(any()) } returns Unit
        val eventPublisher = mockk<EventPublisher>()
        every {
            eventPublisher.publish(
                listOf(
                    NouvelleFormulePubliée(
                        "id",
                        "Piscine",
                        "120.0",
                        "MENSUELLE"
                    )
                )
            )
        } returns Unit

        // WHEN
        val result = PublierUneFormuleCommandHandler(
            repository,
            eventPublisher
        )(
            PublierUneFormuleCommand(
                "Piscine",
                "120.0",
                "MENSUELLE"
            )
        )

        // THEN
        result `shouldBe` Success
        verify { repository.persist(any()) }
        verify { eventPublisher.publish(any()) }
    }
    "Devrait retourner un Failure en cas d'erreur" {
        // GIVEN
        RandomUUIDGenerator.setNextId("id")
        val repository = mockk<FormuleRepository>()
        val eventPublisher = mockk<EventPublisher>()

        // WHEN
        val result = PublierUneFormuleCommandHandler(
            repository,
            eventPublisher
        )(
            PublierUneFormuleCommand(
                "Piscine",
                "invalide",
                "invalide"
            )
        )

        // THEN
        result `shouldBe` Failure
    }

})