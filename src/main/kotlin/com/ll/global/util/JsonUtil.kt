package com.ll.global.util

object JsonUtil {
    fun jsonStrToMap(json: String): Map<String, Any> {
        val result = mutableMapOf<String, Any>()

        // 중괄호, 앞뒤 공백 제거
        val trimmed = json.trim().removePrefix("{").removeSuffix("}")
        if (trimmed.isBlank()) return result

        // 콤마 기준 key:value 분리
        val pairs = trimmed.split(",")

        for (pair in pairs) {
            val keyValue = pair.split(":", limit = 2)
            if (keyValue.size != 2) continue

            // 쌍따옴표 제거 및 세팅
            val key = keyValue[0].trim().removeSurrounding("\"")
            val rawVal = keyValue[1].trim()

            val value = if (rawVal.startsWith("\"") && rawVal.endsWith("\"")) {
                rawVal.removeSurrounding("\"")
            } else {
                rawVal.toIntOrNull() ?: rawVal
            }

            result[key] = value
        }
        return result
    }
}