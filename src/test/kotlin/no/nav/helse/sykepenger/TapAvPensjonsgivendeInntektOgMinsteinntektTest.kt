package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import kotlin.test.Test

class TapAvPensjonsgivendeInntektOgMinsteinntektTest {

   @Test
   fun `søker_kan_ha_rett_på_sykepenger_dersom_han_ikke_er_for_gammel_og_inntekt_utgjør_minst_halvparten_av_grunnbeløpet`() {
      val søknad = testSøknad(alder = 35, fastsattÅrsinntekt = 250000, grunnbeløp = 96883, harVurdertInntekt = false)
      assertJa(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }

   @Test
   fun `søker_kan_ha_rett_på_sykepenger_dersom_sykepengegrunnlaget_utgjør_mindre_enn_halvparten_av_grunnbeløpet`() {
      val søknad = testSøknad(alder = 35, fastsattÅrsinntekt = 45000, grunnbeløp = 96883, harVurdertInntekt = false)
      assertKanskje(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }

   @Test
   fun `søker_har_ikke_rett_på_sykepenger_dersom_sykepengegrunnlaget_utgjør_mindre_enn_halvparten_av_grunnbeløpet_og_saksbehandler_har_vurdert`() {
      val søknad = testSøknad(alder = 35, fastsattÅrsinntekt = 45000, grunnbeløp = 96883, harVurdertInntekt = true)
      assertKanskje(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }

   @Test
   fun `søker_har_ikke_rett_på_sykepenger_dersom_han_er_for_gammel`() {
      val søknad = testSøknad(alder = 70, fastsattÅrsinntekt = 450000, grunnbeløp = 96883, harVurdertInntekt = false)
      assertKanskje(tapAvPensjonsgivendeInntektOgMinsteInntekt.evaluer(søknad))
   }
}
