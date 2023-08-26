package com.keetr.comicsnac.details

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatDate(): String = format(DateTimeFormatter.ofPattern("d MMM uuuu"))