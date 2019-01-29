package no.nav.helse

import org.junit.jupiter.api.Test
import java.time.LocalDate

class OpptjeningTest {

   @Test
   fun `søker oppfyller opptjeningstid`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-29")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      val soknad = Søknad(førsteSykdomsdag, datoForAnsettelse, "", emptyList(), LocalDate.now(), LocalDate.now())

      assertJa(harOppfyltOpptjeningstid.evaluer(soknad))
   }

   @Test
   fun `søker kan kanskje oppfylle opptjeningstid selv om han har jobbet mindre enn 28 dager`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-28")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      val soknad = Søknad(førsteSykdomsdag, datoForAnsettelse, "", emptyList(), LocalDate.now(), LocalDate.now())

      assertKanskje(harOppfyltOpptjeningstid.evaluer(soknad))
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
