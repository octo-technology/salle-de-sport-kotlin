package example.souscription.domain

sealed class Abonné {
    abstract val email: String
    data class Standard(override val email: String) : Abonné()
    data class Etudiant(override val email: String) : Abonné()
}