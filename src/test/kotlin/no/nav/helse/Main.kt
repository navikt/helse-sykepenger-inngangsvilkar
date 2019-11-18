package no.nav.helse

import com.google.gson.Gson
import no.nav.helse.sykepenger.vilkar.Vilkårsgrunnlag
import no.nav.helse.sykepenger.vilkar.inngangsvilkår
import spark.Request
import spark.Response
import spark.Spark
import java.time.LocalDate

fun main() {
   val søknad = Vilkårsgrunnlag(
      inntekterMedArbeidsforhold = emptyList(),
      tidspunktForArbeidsuførhet = LocalDate.now(),
      opptjeningstid = 15,
      erMedlem = true,
      ytelser = emptyList(),
      alder = 35,
      søknadSendt = LocalDate.now(),
      førsteDagSøknadGjelderFor = LocalDate.now(),
      sisteDagSøknadenGjelderFor = LocalDate.now().plusWeeks(2),
      sisteMuligeSykepengedag = LocalDate.now().plusWeeks(4),
      fastsattÅrsinntekt = 420000,
      grunnbeløp = 96883
   )

   Spark.port(8282)
   Spark.staticFiles.location("/public")
   Spark.get("/api", { _: Request, _: Response -> inngangsvilkår.evaluer(søknad) }) {
      Gson().toJson(it)
   }
}
