package app.circle.foodmood.controller.commonUtils

import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
class GlobalUtils {


    fun getAttendanceDate(): Int? {
        val date = LocalDate.now()
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val str = date.toString(fmt)
/*
        var dateValue: String = date.toLocalDate().year.toString() + "" + date.toLocalDate().monthOfYear + "" + date.toLocalDate().dayOfMonth
*/

        return str.toIntOrNull()
    }

    fun getCurrentDate(): Int?{
        val date = LocalDate.now()
        /**
         * Date Format should be yyyyMMdd.
         */
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val str = date.toString(fmt)




        return str.toIntOrNull()
    }

    fun getCurrentTime(): Int?{
        val time = LocalTime.now()
        val fmt = DateTimeFormat.forPattern("hhmm")
        val str = time.toString(fmt)

        return str.toIntOrNull()
    }
}
