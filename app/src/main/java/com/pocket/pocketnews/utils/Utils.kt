package com.pocket.pocketnews.utils

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class Utils {
    companion object {
        fun convertInputStreamToString(inputStream: InputStream): String {
            val reader = BufferedReader(InputStreamReader(inputStream))
            val total = StringBuilder()
            var line: String? = ""
            while (reader.readLine().also({ line = it }) != null) {
                total.append(line!!.trim())
            }
          return  total.toString()

        }

    }
}