package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SøknadsfristTest {

   @Test
   fun `søker oppfyller krav om innsendingsfrist`() {
      val søknad = testSøknad(søknadSendt = LocalDate.parse("2019-04-30"), førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29"))
      assertJa(erKravetFremsattInnenFrist.evaluer(søknad))
   }

   @Test
   fun `søker oppfyller ikke krav om innsendingsfrist`() {
      val søknad = testSøknad(søknadSendt = LocalDate.parse("2019-05-01"), førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29"))
      assertNei(erKravetFremsattInnenFrist.evaluer(søknad))
   }

   @Test
   fun `søknad kan være sendt mindre enn 3 måneder etter første måned i søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-04-30")

      assertJa(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }

   @Test
   fun `søknad kan være sendt 3 måneder etter første måned i søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")

      assertJa(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }

   @Test
   fun `søknad kan ikke være sendt mer enn 3 måneder etter første måned i søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2019-05-01")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")

      assertNei(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }

   @Test
   fun `søknad kan ikke være sendt før søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2018-12-31")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")

      assertNei(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }
}
