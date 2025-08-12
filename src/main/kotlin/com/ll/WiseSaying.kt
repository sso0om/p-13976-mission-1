package com.ll

import com.ll.global.util.JsonUtil.jsonStrToMap

data class WiseSaying(
    val id: Int,
    var content: String,
    var author: String
) {
    companion object {
        fun fromJsonStr(jsonStr: String): WiseSaying {
            val map = jsonStrToMap(jsonStr)

            return WiseSaying(
                id = (map["id"] as? Int) ?: 0,
                content = (map["content"] as? String) ?: "",
                author = (map["author"] as? String) ?: "",
            )
        }
    }

    fun modify(content: String, author: String) {
        this.content = content
        this.author = author
    }

    val jsonStr: String
        get() {
            return """
                {
                    "id": $id,
                    "content": "$content",
                    "author": "$author"
                }
            """.trimIndent()
        }
}