package no.nav.helse.sykepenger.inngangsvilkar

import no.nav.helse.assertJa
import no.nav.helse.sykepenger.inngangsvilkar.Vilkårsgrunnlag
import no.nav.helse.sykepenger.inngangsvilkar.inngangsvilkår
import org.junit.jupiter.api.Test
import java.time.LocalDate

class InngangsvilkårTest {

   @Test
   fun `søker må oppfylle samtlige inngangsvilkår`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-29")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      val bostedLandISykdomsperiode = "Norge"
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")
      val alder = 35
      val aktuellMånedsinntekt = 38000L
      val rapportertMånedsinntekt = 400000L
      val fastsattÅrsinntekt = aktuellMånedsinntekt * 12
      val grunnbeløp = 96883L
      val harVurdertInntekt = false

      val soknad = Vilkårsgrunnlag(førsteSykdomsdag, datoForAnsettelse, alder, bostedLandISykdomsperiode, emptyList(), søknadSendt,
         førsteDagSøknadGjelderFor, aktuellMånedsinntekt, rapportertMånedsinntekt, fastsattÅrsinntekt, grunnbeløp, harVurdertInntekt)


      assertJa(inngangsvilkår.evaluer(soknad))
   }
}
