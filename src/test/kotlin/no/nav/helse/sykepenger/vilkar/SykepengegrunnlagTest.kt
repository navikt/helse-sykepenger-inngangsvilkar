package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test

class SykepengegrunnlagTest {

   @Test
   fun `sykepengegrunnlaget utgjør halvparten av grunnbeløpet`() {
      assertJa(erInntektHalvpartenAvGrunnbeløpet(500, 1000))
   }

   @Test
   fun `sykepengegrunnlaget utgjør mer enn halvparten av grunnbeløpet`() {
      assertJa(erInntektHalvpartenAvGrunnbeløpet(5000, 1000))
   }

   @Test
   fun `det ytes ikke sykepenger når sykepengegrunnlaget utgjør mindre enn halvparten av grunnbeløpet`() {
      assertNei(erInntektHalvpartenAvGrunnbeløpet(400, 1000))
   }
}
