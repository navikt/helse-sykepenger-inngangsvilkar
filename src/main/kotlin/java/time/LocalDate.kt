package java.time

import kotlin.js.Date
import kotlin.math.roundToInt

class LocalDate(private val dato:Double) {

    val datodato = Date(dato)

    companion object {
       fun parse(s:String) : LocalDate = LocalDate(Date.parse(s))
       fun now() : LocalDate = LocalDate(Date.now())

    }

    operator fun compareTo(other: LocalDate): Int {
        val ret =  (this.dato - other.dato).roundToInt()
        return ret
    }

    fun minusDays(days:Int) : LocalDate {
        return LocalDate(this.dato - (days * 24 * 60 * 60 * 1000.0))
    }

    private fun minusOneMonth() : LocalDate {
        val d = Date(dato);
        if (d.getMonth() == 0) {
            return LocalDate(Date(d.getFullYear()-1, 11, d.getDate()).getTime());
        }
        val d2 = Date(d.getFullYear(), d.getMonth()-1, d.getDate())
        return LocalDate(Date(d.getFullYear(), d.getMonth()-1, d.getDate()).getTime())
    }

    fun minusMonths(m : Int) : LocalDate {
        // vi setter dag til 1 for å unngå problemer når vi feks ruller forbi februar dersom dagen er 30
        var nd = this.withDayOfMonth(1);
        for (i in 1..m) {
            nd = nd.minusOneMonth();
        }
        return nd.withDayOfMonth(this.datodato.getDate());
    }

    fun withDayOfMonth(dayOfMonth : Int) : LocalDate {
        val d1 = Date(dato)
        return LocalDate(Date(d1.getFullYear(), d1.getMonth(), dayOfMonth).getTime())
    }

}