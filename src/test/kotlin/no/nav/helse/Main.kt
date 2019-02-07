package no.nav.helse

import com.google.gson.Gson
import no.nav.helse.sykepenger.inngangsvilkar.Vilkårsgrunnlag
import no.nav.helse.sykepenger.inngangsvilkar.inngangsvilkår
import spark.Request
import spark.Response
import spark.Spark
import java.time.LocalDate

fun main() {
   val søknad = Vilkårsgrunnlag(
      førsteSykdomsdag = LocalDate.now(),
      datoForAnsettelse = LocalDate.now().minusDays(15),
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

   Spark.port(8282)
   Spark.staticFiles.location("/public")
   Spark.get("/api", { _: Request, _: Response -> inngangsvilkår.evaluer(søknad) }) {
      Gson().toJson(it)
   }


   /*HttpServer.create(InetSocketAddress(8080), 0).apply {

      createContext("/api") { http ->
         http.responseHeaders.add("Content-type", "application/json")
         http.sendResponseHeaders(200, 0)
         PrintWriter(http.responseBody).use { out ->
            out.println(inngangsvilkår.evaluer(søknad).let {
               Gson().toJson(it)
            })
         }
      }

      start()
   }*/


}
