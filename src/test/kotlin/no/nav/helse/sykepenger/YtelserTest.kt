package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import kotlin.test.Test

class YtelserTest {

   @Test
   fun `du_kan_ha_rett_til_sykepenger_om_du_ikke_har_andre_ytelser`() {
      val søknad = testSøknad(ytelser = emptyList())
      assertJa(harIngenYtelserSomIkkeKanKombineresMedSykepenger.evaluer(søknad))
   }

   @Test
   fun `du_kan_ha_rett_til_sykepenger_om_du_har_andre_ytelser`() {
      val søknad = testSøknad(ytelser = listOf("Dagpenger"))
      assertKanskje(harIngenYtelserSomIkkeKanKombineresMedSykepenger.evaluer(søknad))
   }

   @Test
   fun `søker_har_ingen_ytelser_dersom_listen_er_tom`() {
      assertNei(søkerHarAndreYtelser(emptyList()))
   }

   @Test
   fun `søker_har_ytelser_dersom_listen_ikke_er_tom`() {
      assertJa(søkerHarAndreYtelser(listOf("Dagpenger")))
   }
}
