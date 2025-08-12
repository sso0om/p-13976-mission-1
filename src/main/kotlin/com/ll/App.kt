package com.ll

import com.ll.global.rq.Rq
import com.ll.global.util.FileUtil
import java.io.File

class App {
    private val BASE_DIR = "db/wiseSaying"
    private val LAST_ID = "$BASE_DIR/lastId.txt"

    var idx:Int = 0;
    var wiseSayings = mutableListOf<WiseSaying>()

    fun run() {
        println("== 명언 앱 ==")
        loadData()

        while (true) {
            print("명령) ")
            val cmd = readLine()?.trim() ?: ""
            val rq = Rq(cmd);

            when (rq.getActionName) {
                "등록" -> createWS()
                "목록" -> readWSs()
                "수정" -> modifyWS(rq)
                "삭제" -> deleteWS(rq)
                "빌드" -> buildJson()
                "종료" -> break
            }
        }
    }

    fun createWS() {
        print("명언 : ")
        val content = readNotBlankLine()

        print("작가 : ")
        val author = readNotBlankLine()

        val wiseSaying = WiseSaying(++idx, content, author)
        wiseSayings.add(wiseSaying)

        saveWSFile(wiseSaying)
        saveLastId(idx)

        println("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun readWSs() {
        println("번호 / 작가 / 명언")
        println("----------------------")

        for (ws in wiseSayings.reversed()) {
            println("${ws.id} / ${ws.author} / ${ws.content}")
        }
    }

    fun deleteWS(rq: Rq) {
        val id = getParamId(rq) ?: return
        val wiseSaying = getWSById(id) ?: return

        wiseSayings.remove(wiseSaying)

        deleteWSFile(wiseSaying)

        println("${id}번 명언이 삭제 되었습니다.")
    }

    fun modifyWS(rq: Rq) {
        val id = getParamId(rq) ?: return
        val wiseSaying = getWSById(id) ?: return

        println("명언(기존) : ${wiseSaying.content}")
        print("명언 : ")
        val newContent = readNotBlankLine()

        println("작가(기존) : ${wiseSaying.author}")
        print("작가 : ")
        val newAuthor = readNotBlankLine()

        wiseSaying.modify(newContent, newAuthor)

        saveWSFile(wiseSaying)
    }


    // --- helper

    fun getParamId(rq: Rq): Int? {
        val id = rq.getParamInt("id")

        if (id == null) {
            println("명언 번호를 입력해주세요.")
        }
        return id
    }

    fun getWSById(id: Int): WiseSaying? {
        val wiseSaying = wiseSayings.find { it.id == id }

        if (wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return null
        }
        return wiseSaying
    }

    fun readNotBlankLine(): String {
        while (true) {
            val input = readLine()?.trim()
            if (!input.isNullOrEmpty()) {
                return input
            }
            println("값을 반드시 입력해야 합니다.")
        }
    }


    // --- File

    private fun buildJson() {
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
        println("data.json 파일의 내용이 갱신되었습니다.")
    }

    private fun loadData() {
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