package no.nav.helse.sykepenger.vilkar.beregningsvilkar

import no.nav.helse.assertJa
import no.nav.helse.assertNei
import no.nav.helse.sykepenger.vilkar.inngangsvilkar.testSøknad
import org.junit.jupiter.api.Test

class BeregningsvilkårTest {

   @Test
   fun `beregningsvilkår er oppfylt når saksbehandler har vurdert inntekt`() {
      assertJa(beregningsvilkår.evaluer(testSøknad(harVurdertInntekt = true)))
   }

   @Test
   fun `beregningsvilkår er oppfylt når avvik er 25 prosent`() {
      assertJa(beregningsvilkår.evaluer(testSøknad(aktuellMånedsinntekt = 125L, rapportertMånedsinntekt = 1200)))
   }

   @Test
   fun `beregningsvilkår er ikke oppfylt når avvik er mer enn 25 prosent`() {
      assertNei(beregningsvilkår.evaluer(testSøknad(aktuellMånedsinntekt = 126L, rapportertMånedsinntekt = 1200)))
   }

   @Test
   fun `beregningsvilkår er oppfylt når avvik er mer enn 25 prosent og saksbehandler har vurdert`() {
      assertJa(beregningsvilkår.evaluer(testSøknad(harVurdertInntekt = true, aktuellMånedsinntekt = 126L, rapportertMånedsinntekt = 1200)))
   }

   @Test
   fun `skal ikke vurdere inntekt når saksbehandler har gjort det`() {
      assertNei(behovForSkjønnsmessigVurderingAvInntekt(true, 1, 9999))
   }

   @Test
   fun `skal vurdere inntekt når det er mer enn 25 prosent avvik`() {
      assertJa(behovForSkjønnsmessigVurderingAvInntekt(false, 126, 1200))
   }

   @Test
   fun `skal vurdere inntekt når det er mer enn -25 prosent avvik`() {
      assertJa(behovForSkjønnsmessigVurderingAvInntekt(false, 74, 1200))
   }

   @Test
   fun `skal ikke vurdere inntekt når det er 25 prosent avvik`() {
      assertNei(behovForSkjønnsmessigVurderingAvInntekt(false, 125, 1200))
   }

   @Test
   fun `skal ikke vurdere inntekt når det er -25 prosent avvik`() {
      assertNei(behovForSkjønnsmessigVurderingAvInntekt(false, 75, 1200))
   }

   @Test
   fun `skal ikke vurdere inntekt når det er under 25 prosent avvik`() {
      assertNei(behovForSkjønnsmessigVurderingAvInntekt(false, 124, 1200))
   }

   @Test
   fun `skal ikke vurdere inntekt når det er under -25 prosent avvik`() {
      assertNei(behovForSkjønnsmessigVurderingAvInntekt(false, 76, 1200))
   }
}
