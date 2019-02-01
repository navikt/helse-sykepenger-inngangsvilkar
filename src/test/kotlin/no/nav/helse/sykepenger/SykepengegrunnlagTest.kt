package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import kotlin.test.Test

class SykepengegrunnlagTest {

   @Test
   fun `sykepengegrunnlaget_utgjør_halvparten_av_grunnbeløpet`() {
      assertJa(erInntektHalvpartenAvGrunnbeløpet(500, 1000, false))
   }

   @Test
   fun `sykepengegrunnlaget_utgjør_mer_enn_halvparten_av_grunnbeløpet`() {
      assertJa(erInntektHalvpartenAvGrunnbeløpet(5000, 1000, false))
   }

   @Test
   fun `saksbehandler_må_vurdere_om_oppgitt_inntekt_er_riktig_dersom_sykepengegrunnlaget_utgjør_mindre_enn_halvparten_av_grunnbeløpet`() {
      assertKanskje(erInntektHalvpartenAvGrunnbeløpet(400, 1000, false))
   }

   @Test
   fun `det_ytes_ikke_sykepenger_når_sykepengegrunnlaget_utgjør_mindre_enn_halvparten_av_grunnbeløpet_og_saksbehandler_har_vurdert`() {
      assertNei(erInntektHalvpartenAvGrunnbeløpet(400, 1000, true))
   }
}
