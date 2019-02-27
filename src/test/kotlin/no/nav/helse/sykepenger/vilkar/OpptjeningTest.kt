package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test

class OpptjeningTest {

   @Test
   fun `søker oppfyller opptjeningstid`() {
      val søknad = testSøknad(opptjeningstid = 28)
      assertJa(harOppfyltOpptjeningstid.evaluer(søknad))
   }

   @Test
   fun `søker kan kanskje oppfylle opptjeningstid selv om han har jobbet mindre enn 28 dager`() {
      val søknad = testSøknad(opptjeningstid = 0)
      assertKanskje(harOppfyltOpptjeningstid.evaluer(søknad))
   }

   @Test
   fun `søker har oppfylt opptjeningstid når han har jobbet i 28 dager`() {
      assertJa(søkerHarVærtIArbeid(opptjeningstid = 28))
   }

   @Test
   fun `søker har oppfylt opptjeningstid når han har jobbet mer enn 28 dager`() {
      assertJa(søkerHarVærtIArbeid(opptjeningstid = 29))
   }

   @Test
   fun `søker har ikke oppfylt opptjeningstid når han har jobbet mindre enn 28 dager`() {
      assertNei(søkerHarVærtIArbeid(opptjeningstid = 27))
   }
}
