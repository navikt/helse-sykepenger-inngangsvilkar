package no.nav.helse.sykepenger.vilkar.inngangsvilkar

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import org.junit.jupiter.api.Test

class TapAvPensjonsgivendeInntektOgMinsteinntektTest {

   @Test
   fun `søker kan ha rett på sykepenger dersom han ikke er for gammel og inntekt utgjør minst halvparten av grunnbeløpet`() {
      val søknad = testSøknad(alder = 35, fastsattÅrsinntekt = 250000, grunnbeløp = 96883, harVurdertInntekt = false)
      assertJa(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }

   @Test
   fun `søker kan ha rett på sykepenger dersom sykepengegrunnlaget utgjør mindre enn halvparten av grunnbeløpet`() {
      val søknad = testSøknad(alder = 35, fastsattÅrsinntekt = 45000, grunnbeløp = 96883, harVurdertInntekt = false)
      assertKanskje(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }

   @Test
   fun `søker har ikke rett på sykepenger dersom sykepengegrunnlaget utgjør mindre enn halvparten av grunnbeløpet og saksbehandler har vurdert`() {
      val søknad = testSøknad(alder = 35, fastsattÅrsinntekt = 45000, grunnbeløp = 96883, harVurdertInntekt = true)
      assertKanskje(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }

   @Test
   fun `søker har ikke rett på sykepenger dersom han er for gammel`() {
      val søknad = testSøknad(alder = 70, fastsattÅrsinntekt = 450000, grunnbeløp = 96883, harVurdertInntekt = false)
      assertKanskje(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }
}
