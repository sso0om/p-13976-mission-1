package com.ll.global.util

import java.io.File

object FileUtil {
    fun readFileText(path: String): String {
        return try {
            File(path).readText()
        } catch (e: Exception) {
            ""
        }
    }

    fun writeFileText(path: String, text: String) {
        val file = File(path)
        file.parentFile.mkdirs()
        file.writeText(text)
    }
}