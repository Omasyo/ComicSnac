package com.keetr.comicsnac.model.issue

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatDate() = format(DateTimeFormatter.ofPattern("d MMM uuuu"))