package example.offres.commands.publierunenouvelleformule

data class PublierUneFormuleCommand (
    val intitulé: String,
    val prixDeBase: String,
    val durée: String
)