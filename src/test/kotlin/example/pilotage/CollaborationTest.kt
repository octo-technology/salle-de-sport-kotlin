package example.pilotage

import example._sharedkernel.eventbus.SynchronousEventPublisher
import example.pilotage.infrastructure.DependenciesInjection
import example.pilotage.queries.ChiffreAffaireMensuelQuery
import example.pilotage.queries.ChiffreAffaireMensuelQueryHandler.Result.Success
import example.souscription.domain.AbonnementSouscrit
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate

class CollaborationTest : StringSpec({
    "Ecouter et persister les abonnements souscrits et calculer un chiffre d'affaire mensuel" {
        // GIVEN
        val eventBus = SynchronousEventPublisher()
        val listener = DependenciesInjection.provideAbonnementSouscritListener()
        eventBus.subscribe(listener)
        val chiffreAffaireHandler = DependenciesInjection.provideChiffreAffaireMensuelQueryHandler()
        val débutFévrier2019 = LocalDate.of(2019, 2, 1)
        val finFévrier2019 = LocalDate.of(2019, 2, 28)
        val débutMars2019 = LocalDate.of(2019, 3, 1)
        val finMars2019 = LocalDate.of(2019, 3, 31)
        val débutJanvier2019 = LocalDate.of(2019, 1, 1)
        val finDécembre2019 = LocalDate.of(2019, 12, 31)

        // WHEN
        eventBus.publish(
            listOf(
                AbonnementSouscrit(
                    id = "id1",
                    recipientEmailAddress = "someone@octo.com",
                    intituléFormule = "Piscine",
                    prixFinal = "50",
                    duréeFormule = "MENSUELLE",
                    isEtudiant = false,
                    dateDebut = débutFévrier2019,
                    dateFin = finFévrier2019
                ),
                AbonnementSouscrit(
                    id = "id2",
                    recipientEmailAddress = "someoneelse@octo.com",
                    intituléFormule = "Pilates",
                    prixFinal = (60 * 12).toString(),
                    duréeFormule = "ANNUELLE",
                    isEtudiant = true,
                    dateDebut = débutJanvier2019,
                    dateFin = finDécembre2019
                ),
                AbonnementSouscrit(
                    id = "id3",
                    recipientEmailAddress = "somebodyelse@octo.com",
                    intituléFormule = "Crossfit",
                    prixFinal = "40",
                    duréeFormule = "MENSUELLE",
                    isEtudiant = false,
                    dateDebut = débutMars2019,
                    dateFin = finMars2019
                )
            )
        )
        val result = chiffreAffaireHandler(
            ChiffreAffaireMensuelQuery(
                2,
                2019
            )
        )

        // THEN
        result `shouldBe` Success((50 + 60).toDouble())
    }
})