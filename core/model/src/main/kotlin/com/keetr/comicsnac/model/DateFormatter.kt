package com.keetr.comicsnac.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatDate(): String = format(DateTimeFormatter.ofPattern("d MMM uuuu"))