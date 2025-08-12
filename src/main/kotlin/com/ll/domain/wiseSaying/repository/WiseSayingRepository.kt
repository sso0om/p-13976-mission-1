package com.ll.domain.wiseSaying.repository

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.global.util.FileUtil
import java.io.File

class WiseSayingRepository {
    private val BASE_DIR = "db/wiseSaying"
    private val LAST_ID = "$BASE_DIR/lastId.txt"

    var idx:Int = 0;
    var wiseSayings = mutableListOf<WiseSaying>()

    fun findAllReversed(): List<WiseSaying> {
        return wiseSayings.reversed()
    }

    fun findById(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    fun save(wiseSaying: WiseSaying) {
        if(wiseSaying.isNew()) {
            wiseSaying.id = ++idx
            wiseSayings.add(wiseSaying)
            saveLastId(idx)
        }
        saveWSFile(wiseSaying)
    }

    fun delete(wiseSaying: WiseSaying) {
        wiseSayings.remove(wiseSaying)
        deleteWSFile(wiseSaying)
    }


    // --- File

    fun loadData() {
        wiseSayings.clear()

        val dir = File(BASE_DIR)
        if (!dir.exists()) return

        val lastIdStr = FileUtil.readFileText(LAST_ID)
        idx = lastIdStr.toIntOrNull() ?: 0

        val files = dir.listFiles() ?: arrayOf()

        for (file in files) {
            if (file.name.endsWith(".json") && file.name != "data.json") {
                val jsonStr = FileUtil.readFileText(file.absolutePath)
                val wiseSaying = WiseSaying.fromJsonStr(jsonStr)
                wiseSayings.add(wiseSaying)
            }
        }
    }

    fun buildJson() {
        val path = "$BASE_DIR/data.json"
        val sb = StringBuilder()

        sb.append("[").append("\n")
        wiseSayings.forEachIndexed { index, ws ->
            sb.append(ws.jsonStr)

            if (index != wiseSayings.lastIndex) {
                sb.append(",")
            }
            sb.append("\n")
        }
        sb.append("]")

        File(path).writeText(sb.toString())
    }

    private fun saveWSFile(wiseSaying: WiseSaying) {
        FileUtil.writeFileText("$BASE_DIR/${wiseSaying.id}.json", wiseSaying.jsonStr)
    }

    private fun saveLastId(id: Int) {
        FileUtil.writeFileText(LAST_ID, id.toString())
    }

    private fun deleteWSFile(wiseSaying: WiseSaying) {
        val file = File("$BASE_DIR/${wiseSaying.id}.json")

        if (file.exists()) {
            file.delete()
        }
    }
}