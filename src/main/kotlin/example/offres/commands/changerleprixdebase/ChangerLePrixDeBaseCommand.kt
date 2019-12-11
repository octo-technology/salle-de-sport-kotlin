package example.offres.commands.changerleprixdebase

data class ChangerLePrixDeBaseCommand (
    val forumleId: String,
    val nouveauPrixDeBase: String
)