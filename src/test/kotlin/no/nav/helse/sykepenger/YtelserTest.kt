package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
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
      assertNei(søkerHarAndreYtelser(emptyList()))
   }

   @Test
   fun `søker har ytelser dersom listen ikke er tom`() {
      assertJa(søkerHarAndreYtelser(listOf("Dagpenger")))
   }
}
