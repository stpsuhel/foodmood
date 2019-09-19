package app.circle.foodmood.controller.commonUtils

import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.springframework.stereotype.Service


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

    fun getCurrentDate(): Int? {
        val date = LocalDate.now()
        /**
         * Date Format should be yyyyMMdd.
         */
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val str = date.toString(fmt)

        return str.toIntOrNull()
    }

    fun getCurrentTime(): Int? {
        val time = LocalTime.now()
        val fmt = DateTimeFormat.forPattern("hhmm")
        val str = time.toString(fmt)

        return str.toIntOrNull()
    }

    fun getDateInInteger(date: String): Int {
        val fmt = DateTimeFormat.forPattern("yyyy-MM-dd")
        val dateTime = fmt.parseDateTime(date)

        val myFormat = DateTimeFormat.forPattern("yyyyMMdd")
        val dateFormate = myFormat.print(dateTime)
        return dateFormate.toInt()
    }

    fun getOneWeekBeforeDate(date: LocalDate): Int? {
        val beforeSevenDays = date.minusDays(7)
        /**
         * Date Format should be yyyyMMdd.
         */
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val str = beforeSevenDays.toString(fmt)

        return str.toIntOrNull()
    }

    fun formatDate(date: Int, format: String): String? {
        val dtf = DateTimeFormat.forPattern("yyyyMMdd")
        val jodatime = dtf.parseDateTime(date.toString())
        val dtfOut = DateTimeFormat.forPattern(format)
        return dtfOut.print(jodatime)
    }

    fun getDateForInputField(date: Int): String {
        val dateString = date.toString()
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val dateTime = fmt.parseDateTime(dateString)

        val myFormat = DateTimeFormat.forPattern("yyyy-MM-dd")
        return myFormat.print(dateTime)
    }

    fun getDateInString(date: Int): String {
        val dateString = date.toString()
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val dateTime = fmt.parseDateTime(dateString)

        val myFormat = DateTimeFormat.forPattern("dd MMM, YYYY")
        return myFormat.print(dateTime)
    }

    fun getTimeInString(time: Int): String {
        var timeString = time.toString()

        if(timeString.length == 3){
            timeString = "0$timeString"
        }

        val fmt = DateTimeFormat.forPattern("hhmm")
        val str = fmt.parseDateTime(timeString)

        val myTimeFormat = DateTimeFormat.forPattern("hh:mm a")

        return myTimeFormat.print(str)
    }

    fun getCurrentMonthDate(): Int? {
        val date = LocalDate.now()
        /**
         * Date Format should be yyyyMMdd.
         */
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val str = date.toString(fmt)
        val thisMonth = str.take(6) + "01"

        return thisMonth.toIntOrNull()
    }

    fun getMonthEndDate(month: Int): Int? {
        val date = LocalDate.now()
        var monthString = month.toString()
        if(monthString.length == 1){
            monthString = "0$monthString"
        }
        /**
         * Date Format should be yyyyMMdd.
         */
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val str = date.toString(fmt)
        val thisMonth = str.take(4) + monthString + "30"

        return thisMonth.toIntOrNull()
    }

    fun getMonthStartDate(month: Int): Int? {
        val date = LocalDate.now()
        var monthString = month.toString()
        if(monthString.length == 1){
            monthString = "0$monthString"
        }
        /**
         * Date Format should be yyyyMMdd.
         */
        val fmt = DateTimeFormat.forPattern("yyyyMMdd")
        val str = date.toString(fmt)
        val thisMonth = str.take(4) + monthString + "01"

        return thisMonth.toIntOrNull()
    }
}
