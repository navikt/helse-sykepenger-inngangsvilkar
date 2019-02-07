package no.nav.helse.sykepenger.vilkar.beregningsvilkar

import no.nav.helse.sykepenger.vilkar.Vilkårsgrunnlag
import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Evaluering.Companion.ja
import no.nav.nare.core.evaluations.Evaluering.Companion.nei
import no.nav.nare.core.specifications.Spesifikasjon


internal val erDetAvvikMellomAktuellInntektOgRapportertInntekt = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Er det avvik mellom aktuell inntekt og rapportert inntekt?",
   identitet = "§§ 8-29 8-30"
) { søknad ->
   behovForSkjønnsmessigVurderingAvInntekt(søknad.harVurdertInntekt, søknad.aktuellMånedsinntekt, søknad.rapportertMånedsinntekt)
}

val beregningsvilkår = erDetAvvikMellomAktuellInntektOgRapportertInntekt.ikke()

fun behovForSkjønnsmessigVurderingAvInntekt(harVurdertInntekt: Boolean, aktuellMånedsinntekt: Long, rapportertInntekt: Long): Evaluering =
   if (harVurdertInntekt) {
      nei("Saksbehandler har skjønnsmessig vurdert inntekt")
   } else {
      val avvik = Math.abs((12 * aktuellMånedsinntekt - rapportertInntekt) / rapportertInntekt.toDouble())
      if (avvik > 0.25) {
         ja("Det er behov for skjønnsmessig vurdering av inntekt, avvik er ${Math.ceil(avvik * 100)} %")
      } else {
         nei("Saksbehandler trenger ikke å vurdere inntekten")
      }
   }

