package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import java.time.LocalDate
import kotlin.test.Test

class OpptjeningTest {

   @Test
   fun `søker_oppfyller_opptjeningstid`() {
      val søknad = testSøknad(førsteSykdomsdag = LocalDate.parse("2019-01-29"), datoForAnsettelse = LocalDate.parse("2019-01-01"))
      assertJa(harOppfyltOpptjeningstid.evaluer(søknad))
   }

   @Test
   fun `søker_kan_kanskje_oppfylle_opptjeningstid_selv_om_han_har_jobbet_mindre_enn_28_dager`() {
      val søknad = testSøknad(førsteSykdomsdag = LocalDate.parse("2019-01-28"), datoForAnsettelse = LocalDate.parse("2019-01-01"))
      assertKanskje(harOppfyltOpptjeningstid.evaluer(søknad))
   }

   @Test
   fun `søker_har_oppfylt_opptjeningstid_når_han_har_jobbet_i_28_dager`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-29")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      assertJa(søkerHarVærtIArbeid(førsteSykdomsdag, datoForAnsettelse))
   }

   @Test
   fun `søker_har_oppfylt_opptjeningstid_når_han_har_jobbet_mer_enn_28_dager`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-30")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      assertJa(søkerHarVærtIArbeid(førsteSykdomsdag, datoForAnsettelse))
   }

   @Test
   fun `søker_har_ikke_oppfylt_opptjeningstid_når_han_har_jobbet_mindre_enn_28_dager`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-28")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      assertNei(søkerHarVærtIArbeid(førsteSykdomsdag, datoForAnsettelse))
   }
}
