package example.offres.domain

import example._sharedkernel.uuid.RandomUUIDGenerator
import example.offres.domain.formule.Formule
import example.offres.domain.formule.FormuleId
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class FormuleTest : StringSpec({
    "Devrait lever une exeption quand instancié avec une mauvaise durée" {
        try {
            Formule("Piscine", "invalide", "120.0")
        } catch (e: Exception) {
            e shouldBe Formule.Errors.DuréeInvalide("invalide")
        }
    }
    "Devrait lever une exeption quand instancié avec un mauvais prix" {
        try {
            Formule("Piscine", "MENSUELLE", "invalide")
        } catch (e: Exception) {
            e shouldBe Formule.Errors.PrixInvalide("invalide")
        }
    }
    "Devrait instancer une formule avec id random" {
        RandomUUIDGenerator.setNextId("randomId")
        Formule("Piscine", "MENSUELLE", "120.0").id shouldBe FormuleId("randomId")
    }
})