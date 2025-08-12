package com.ll

class App {
    var idx:Int = 0;
    var wiseSayings = mutableListOf<WiseSaying>()

    fun run() {
        println("== 명언 앱 ==")

        while (true) {
            print("명령) ")
            val cmd = readLine()?.trim()

            when (cmd) {
                "등록" -> createWS()
                "목록" -> readWSs()
                "종료" -> break
            }
        }
    }

    fun createWS() {
        print("명언 : ")
        val content = readLine()?.trim()

        print("작가 : ")
        val author = readLine()?.trim()

        wiseSayings.add(WiseSaying(++idx, content!!, author!!))

        println("${idx}번 명언이 등록되었습니다.")
    }

    fun readWSs() {
        println("번호 / 작가 / 명언")
        println("----------------------")

        for (ws in wiseSayings.reversed()) {
            println("${ws.id} / ${ws.author} / ${ws.content}")
        }
    }
}