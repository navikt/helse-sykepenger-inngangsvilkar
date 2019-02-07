package no.nav.helse.sykepenger.vilkar

import no.nav.helse.sykepenger.vilkar.beregningsvilkar.beregningsvilkår
import no.nav.helse.sykepenger.vilkar.inngangsvilkar.inngangsvilkår
import no.nav.helse.sykepenger.vilkar.inngangsvilkar.toBeDecided

val sykepengevilkår = (inngangsvilkår og beregningsvilkår) eller toBeDecided
