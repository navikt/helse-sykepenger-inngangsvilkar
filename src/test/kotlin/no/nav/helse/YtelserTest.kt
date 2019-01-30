package no.nav.helse

import org.junit.jupiter.api.Test

class YtelserTest {

   @Test
   fun `du kan ha rett til sykepenger om du ikke har andre ytelser`() {
      val søknad = testSøknad(ytelser = emptyList())
      assertJa(harIngenYtelserSomIkkeKanKombineresMedSykepenger.evaluer(søknad))
   }

   @Test
   fun `du kan ha rett til sykepenger om du har andre ytelser`() {
      val søknad = testSøknad(ytelser = listOf("Dagpenger"))
      assertKanskje(harIngenYtelserSomIkkeKanKombineresMedSykepenger.evaluer(søknad))
   }

   @Test
   fun `søker har ingen ytelser dersom listen er tom`() {
      assertJa(søkerHarAndreYtelser(emptyList()))
   }

   @Test
   fun `søker har ytelser dersom listen ikke er tom`() {
      assertNei(søkerHarAndreYtelser(listOf("Dagpenger")))
   }
}
