package example.souscription

import example._sharedkernel.eventbus.EventListener
import example._sharedkernel.uuid.RandomUUIDGenerator
import example._sharedkernel.time.Today
import example.souscription.domain.EmailRecapitulatifProgrammé
import example.souscription.commands.souscrireunabonnement.SouscrireUnAbonnementCommand
import example.souscription.domain.AbonnementSouscrit
import example.souscription.infrastructure.DependenciesInjection
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate

class CollaborationTest : StringSpec({
    "Appliquer une réduction et envoyer un e-mail récapitulatif après souscription" {
        // GIVEN
        val eventBus = DependenciesInjection.provideEventPublisher()
        val souscrireHandler = DependenciesInjection.provideSouscireUnAbonnementCommandHandler()
        val abonnementSouscritListener = DependenciesInjection.provideAbonnementSouscritListener()
        eventBus.subscribe(abonnementSouscritListener)
        val listener = mockk<EventListener>()
        every { listener.notify(any()) } returns Unit
        eventBus.subscribe(listener)
        val today = LocalDate.of(2019, 12, 11)
        Today.setNow(today)
        val todayPlusOneMonth = LocalDate.of(2020, 1, 11)
        val todayPlusOneYear = LocalDate.of(2020, 12, 11)

        // WHEN
        RandomUUIDGenerator.setNextId("id1")
        souscrireHandler(
            SouscrireUnAbonnementCommand(
                intituléFormuleChoisie = "Piscine",
                prixDeBaseFormuleChoisie = "60",
                duréeFormuleChoisie = "MENSUELLE",
                email = "someone@octo.com",
                isEtudiant = true
            )
        )
        RandomUUIDGenerator.setNextId("id2")
        souscrireHandler(
            SouscrireUnAbonnementCommand(
                intituléFormuleChoisie = "Crossfit",
                prixDeBaseFormuleChoisie = "1000",
                duréeFormuleChoisie = "ANNUELLE",
                email = "someoneelse@octo.com",
                isEtudiant = false
            )
        )
        RandomUUIDGenerator.setNextId("id3")
        souscrireHandler(
            SouscrireUnAbonnementCommand(
                intituléFormuleChoisie = "Pilates",
                prixDeBaseFormuleChoisie = "1500",
                duréeFormuleChoisie = "ANNUELLE",
                email = "somebodyelse@octo.com",
                isEtudiant = true
            )
        )

        // THEN
        verify {
            listener.notify(
                AbonnementSouscrit(
                    id = "id1",
                    recipientEmailAddress = "someone@octo.com",
                    intituléFormule = "Piscine",
                    prixFinal = "48.0",
                    duréeFormule = "MENSUELLE",
                    isEtudiant = true,
                    dateDebut = today,
                    dateFin = todayPlusOneMonth
                )
            )
            listener.notify(
                EmailRecapitulatifProgrammé(
                    recipientEmailAddress = "someone@octo.com",
                    subject = "Récapitulatif de votre abonnement",
                    body = "Formule : Piscine\n" +
                        "Durée : MENSUELLE\n" +
                        "Prix : 48.0 €\n" + // 60 -20%
                        "Tarif: Etudiant\n" +
                        "Periode de validité : 11/12/2019 - 11/01/2020"
                )
            )
            listener.notify(
                AbonnementSouscrit(
                    id = "id2",
                    recipientEmailAddress = "someoneelse@octo.com",
                    intituléFormule = "Crossfit",
                    prixFinal = "700.0",
                    duréeFormule = "ANNUELLE",
                    isEtudiant = false,
                    dateDebut = today,
                    dateFin = todayPlusOneYear
                )
            )
            listener.notify(
                EmailRecapitulatifProgrammé(
                    recipientEmailAddress = "someoneelse@octo.com",
                    subject = "Récapitulatif de votre abonnement",
                    body = "Formule : Crossfit\n" +
                        "Durée : ANNUELLE\n" +
                        "Prix : 700.0 €\n" + // 1000 -30%
                        "Tarif: Standard\n" +
                        "Periode de validité : 11/12/2019 - 11/12/2020"
                )
            )
            listener.notify(
                AbonnementSouscrit(
                    id = "id3",
                    recipientEmailAddress = "somebodyelse@octo.com",
                    intituléFormule = "Pilates",
                    prixFinal = "750.0",
                    duréeFormule = "ANNUELLE",
                    isEtudiant = true,
                    dateDebut = today,
                    dateFin = todayPlusOneYear
                )
            )
            listener.notify(
                EmailRecapitulatifProgrammé(
                    recipientEmailAddress = "somebodyelse@octo.com",
                    subject = "Récapitulatif de votre abonnement",
                    body = "Formule : Pilates\n" +
                        "Durée : ANNUELLE\n" +
                        "Prix : 750.0 €\n" + // 1500 -50%
                        "Tarif: Etudiant\n" +
                        "Periode de validité : 11/12/2019 - 11/12/2020"
                )
            )
        }
    }
})