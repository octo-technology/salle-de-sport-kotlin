package example.souscription.commands.programmerunemailrecapitulatif

import example._sharedkernel.eventbus.EventPublisher
import example.souscription.domain.Abonnement.Errors
import example.souscription.domain.EmailRecapitulatifProgrammé
import java.time.format.DateTimeFormatter

//TODO TU
class ProgrammerUnEmailRecapitulatifCommandHandler(
    private val mailer: Mailer,
    private val eventPublisher: EventPublisher
) {
    operator fun invoke(command: ProgrammerUnEmailRecapitulatifCommand) : Result {
        return try {
            val subject = "Récapitulatif de votre abonnement"
            val body = buildBody(command)
            mailer.programmer(
                command.email,
                subject,
                body
            )
            eventPublisher.publish(
                listOf(
                    EmailRecapitulatifProgrammé(
                        command.email,
                        subject,
                        body
                    )
                )
            )
            Result.Success
        } catch (e: Errors) {
            Result.Failure
        }
    }

    private fun buildBody(command: ProgrammerUnEmailRecapitulatifCommand): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/YYY")
        return "Formule : ${command.intituléFormule}\n" +
            "Durée : ${command.duréeFormule}\n" +
            "Prix : ${command.prixFinal} €\n" +
            "Tarif: ${if (command.isEtudiant) {
                "Etudiant"
            } else {
                "Standard"
            }}\n" +
            "Periode de validité : ${command.dateDébut.format(formatter)} - ${command.dateFin.format(formatter)}"
    }

    sealed class Result {
        object Success : Result()
        object Failure: Result()
    }
}