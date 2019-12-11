package example.souscription.commands.souscrireunabonnement

data class SouscrireUnAbonnementCommand (
    val intituléFormuleChoisie: String,
    val prixDeBaseFormuleChoisie: String,
    val duréeFormuleChoisie: String,
    val email: String,
    val isEtudiant: Boolean
)
