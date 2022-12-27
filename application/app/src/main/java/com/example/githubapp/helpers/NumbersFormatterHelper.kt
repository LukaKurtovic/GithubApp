package com.example.githubapp.helpers

import java.text.DecimalFormat

object NumbersFormatterHelper {
    val thousandFormat = DecimalFormat("#,###")
    val tenThousandFormat = DecimalFormat("##,###")
    val hundredThousandFormat = DecimalFormat("###,###")
    val millionFormat = DecimalFormat("#,###,###")
}