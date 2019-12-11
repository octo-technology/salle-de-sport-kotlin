package example.souscription.commands.programmerunemailrecapitulatif

interface Mailer {
    fun programmer(email: String, subject: String, body: String)
}
