package no.nav.helse

import com.google.gson.Gson
import no.nav.helse.sykepenger.Søknad
import no.nav.helse.sykepenger.inngangsvilkår
import spark.Request
import spark.Response
import spark.Spark
import java.time.LocalDate

fun main() {
   val søknad = Søknad(
      førsteSykdomsdag = LocalDate.now(),
      datoForAnsettelse = LocalDate.now().minusDays(30),
      bostedlandISykdomsperiode = "Norge",
      ytelser = emptyList(),
      alder = 35,
      søknadSendt = LocalDate.now(),
      førsteDagSøknadGjelderFor = LocalDate.now(),
      aktuellMånedsinntekt = 35000,
      rapportertMånedsinntekt = 450000,
      grunnbeløp = 96883,
      harVurdertInntekt = false
   )

   Spark.port(8080)
   Spark.staticFiles.location("/public")
   Spark.get("/api", { _: Request, _: Response -> inngangsvilkår.evaluer(søknad) }) {
      Gson().toJson(it)
   }
}
