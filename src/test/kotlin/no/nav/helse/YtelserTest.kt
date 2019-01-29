package no.nav.helse

import org.junit.jupiter.api.Test
import java.time.LocalDate

class YtelserTest {

   @Test
   fun `du kan ha rett til sykepenger om du ikke har andre ytelser`() {
      val soknad = Søknad(LocalDate.now(), LocalDate.now(), "", emptyList(), LocalDate.now(), LocalDate.now())
      assertJa(harIngenYtelserSomIkkeKanKombineresMedSykepenger.evaluer(soknad))
   }

   @Test
   fun `du kan ha rett til sykepenger om du har andre ytelser`() {
      val soknad = Søknad(LocalDate.now(), LocalDate.now(), "", listOf("Dagpenger"), LocalDate.now(), LocalDate.now())
      assertKanskje(harIngenYtelserSomIkkeKanKombineresMedSykepenger.evaluer(soknad))
   }

   @Test
   fun `søker har ingen ytelser dersom listen er tom`() {
      assertJa(søkerHarAndreYtelser(emptyList()))
   }

   @Test
   fun `søker har ytelser dersom listen ikke er tom`() {
      assertNei(søkerHarAndreYtelser(listOf("Dagpenger")))
   }
}
