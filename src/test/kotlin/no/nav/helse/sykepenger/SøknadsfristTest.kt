package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import java.time.LocalDate
import kotlin.test.Test

class SøknadsfristTest {

   @Test
   fun `søker_oppfyller_krav_om_innsendingsfrist`() {
      val søknad = testSøknad(søknadSendt = LocalDate.parse("2019-04-30"), førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29"))
      assertJa(erKravetFremsattInnenFrist.evaluer(søknad))
   }

   @Test
   fun `søker_oppfyller_ikke_krav_om_innsendingsfrist`() {
      val søknad = testSøknad(søknadSendt = LocalDate.parse("2019-05-01"), førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29"))
      assertKanskje(erKravetFremsattInnenFrist.evaluer(søknad))
   }

   @Test
   fun `søknad_kan_være_sendt_mindre_enn_3_måneder_etter_første_måned_i_søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-04-30")

      assertJa(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }

   @Test
   fun `søknad_kan_være_sendt_3_måneder_etter_første_måned_i_søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")

      assertJa(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }

   @Test
   fun `søknad_kan_ikke_være_sendt_mer_enn_3_måneder_etter_første_måned_i_søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2019-05-01")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")

      assertNei(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }

   @Test
   fun `søknad_kan_ikke_være_sendt_før_søknadsperioden`() {
      val søknadSendt = LocalDate.parse("2018-12-31")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")

      assertNei(søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor))
   }
}
