package example.souscription.infrastructure

import example.souscription.commands.programmerunemailrecapitulatif.Mailer

class MailerExternalService : Mailer {
    override fun programmer(email: String, subject: String, body: String) = Unit
}