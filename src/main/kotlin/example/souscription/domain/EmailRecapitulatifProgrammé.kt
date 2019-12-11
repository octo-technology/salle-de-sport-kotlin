package example.souscription.domain

import example._sharedkernel.eventbus.Event

data class EmailRecapitulatifProgrammé(
    val recipientEmailAddress: String,
    val subject: String,
    val body: String
) : Event
