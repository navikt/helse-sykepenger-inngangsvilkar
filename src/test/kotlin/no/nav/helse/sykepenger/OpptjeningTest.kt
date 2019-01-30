package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OpptjeningTest {

   @Test
   fun `søker oppfyller opptjeningstid`() {
      val søknad = testSøknad(førsteSykdomsdag = LocalDate.parse("2019-01-29"), datoForAnsettelse = LocalDate.parse("2019-01-01"))
      assertJa(harOppfyltOpptjeningstid.evaluer(søknad))
   }

   @Test
   fun `søker kan kanskje oppfylle opptjeningstid selv om han har jobbet mindre enn 28 dager`() {
      val søknad = testSøknad(førsteSykdomsdag = LocalDate.parse("2019-01-28"), datoForAnsettelse = LocalDate.parse("2019-01-01"))
      assertKanskje(harOppfyltOpptjeningstid.evaluer(søknad))
   }

   @Test
   fun `søker har oppfylt opptjeningstid når han har jobbet i 28 dager`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-29")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      assertJa(søkerHarVærtIArbeid(førsteSykdomsdag, datoForAnsettelse))
   }

   @Test
   fun `søker har oppfylt opptjeningstid når han har jobbet mer enn 28 dager`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-30")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      assertJa(søkerHarVærtIArbeid(førsteSykdomsdag, datoForAnsettelse))
   }

   @Test
   fun `søker har ikke oppfylt opptjeningstid når han har jobbet mindre enn 28 dager`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-28")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      assertNei(søkerHarVærtIArbeid(førsteSykdomsdag, datoForAnsettelse))
   }
}
