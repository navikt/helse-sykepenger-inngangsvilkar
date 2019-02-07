package no.nav.helse.sykepenger.vilkar.inngangsvilkar

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test

class SykepengegrunnlagTest {

   @Test
   fun `sykepengegrunnlaget utgjør halvparten av grunnbeløpet`() {
      assertJa(erInntektHalvpartenAvGrunnbeløpet(500, 1000, false))
   }

   @Test
   fun `sykepengegrunnlaget utgjør mer enn halvparten av grunnbeløpet`() {
      assertJa(erInntektHalvpartenAvGrunnbeløpet(5000, 1000, false))
   }

   @Test
   fun `saksbehandler må vurdere om oppgitt inntekt er riktig dersom sykepengegrunnlaget utgjør mindre enn halvparten av grunnbeløpet`() {
      assertKanskje(erInntektHalvpartenAvGrunnbeløpet(400, 1000, false))
   }

   @Test
   fun `det ytes ikke sykepenger når sykepengegrunnlaget utgjør mindre enn halvparten av grunnbeløpet og saksbehandler har vurdert`() {
      assertNei(erInntektHalvpartenAvGrunnbeløpet(400, 1000, true))
   }
}
