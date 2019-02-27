package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test

class MedlemskapTest {

   @Test
   fun `søker oppfyller medlemskap`() {
      val søknad = testSøknad(erMedlem = true)
      assertJa(harOppfyltMedlemskap.evaluer(søknad))
   }

   @Test
   fun `søker får avslag om vedkommende ikke er medlem`() {
      val søknad = testSøknad(erMedlem = false)
      assertNei(harOppfyltMedlemskap.evaluer(søknad))
   }

}
