package example.souscription.infrastructure

import example._sharedkernel.eventbus.EventPublisher
import example._sharedkernel.eventbus.SynchronousEventPublisher
import example.souscription.commands.souscrireunabonnement.AbonnementRepository
import example.souscription.commands.programmerunemailrecapitulatif.AbonnementSouscritListener
import example.souscription.commands.programmerunemailrecapitulatif.Mailer
import example.souscription.commands.programmerunemailrecapitulatif.ProgrammerUnEmailRecapitulatifCommandHandler
import example.souscription.commands.souscrireunabonnement.SouscrireUnAbonnementCommandHandler

object DependenciesInjection {
    private var eventPublisher : EventPublisher? = null
    private var mailer : Mailer? = null
    private var abonnementRepository: AbonnementRepository? = null
    private var souscrireUnAbonnementCommandHandler : SouscrireUnAbonnementCommandHandler? = null
    private var abonnementSouscritListener : AbonnementSouscritListener? = null
    private var programmerUnEmailRecapitulatifCommandHandler : ProgrammerUnEmailRecapitulatifCommandHandler? = null

    fun provideEventPublisher() : EventPublisher{
        if (eventPublisher == null) {
            eventPublisher = SynchronousEventPublisher()
        }
        return eventPublisher!!
    }

    fun provideMailer() : Mailer {
        if (mailer == null) {
            mailer = MailerExternalService()
        }
        return mailer!!
    }

    fun provideAbonnementRepository() : AbonnementRepository {
        if (abonnementRepository == null) {
            abonnementRepository = InMemoryAbonnementRepository()
        }
        return abonnementRepository!!
    }

    fun provideSouscireUnAbonnementCommandHandler() : SouscrireUnAbonnementCommandHandler {
        if (souscrireUnAbonnementCommandHandler == null) {
            souscrireUnAbonnementCommandHandler = SouscrireUnAbonnementCommandHandler(
                provideAbonnementRepository(),
                provideEventPublisher()
            )
        }
        return souscrireUnAbonnementCommandHandler!!
    }

    fun provideProgrammerUnEmailRecapitulatifCommandHandler() : ProgrammerUnEmailRecapitulatifCommandHandler {
        if (programmerUnEmailRecapitulatifCommandHandler == null) {
            programmerUnEmailRecapitulatifCommandHandler = ProgrammerUnEmailRecapitulatifCommandHandler(
                provideMailer(),
                provideEventPublisher()
            )
        }
        return programmerUnEmailRecapitulatifCommandHandler!!
    }

    fun provideAbonnementSouscritListener() : AbonnementSouscritListener {
        if (abonnementSouscritListener == null) {
            abonnementSouscritListener = AbonnementSouscritListener(
                provideProgrammerUnEmailRecapitulatifCommandHandler()
            )
        }
        return abonnementSouscritListener!!
    }
}