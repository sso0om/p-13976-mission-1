package com.ll

import com.ll.domain.wiseSaying.controller.WiseSayingController
import com.ll.global.rq.Rq

class App {
    private val wiseSayingController = WiseSayingController()

    fun run() {
        println("== 명언 앱 ==")
        wiseSayingController.loadData()

        while (true) {
            print("명령) ")
            val cmd = readLine()?.trim() ?: ""
            val rq = Rq(cmd);

            when (rq.getActionName) {
                "등록" -> wiseSayingController.createWiseSaying()
                "목록" -> wiseSayingController.readWiseSayings()
                "수정" -> wiseSayingController.modifyWiseSaying(rq)
                "삭제" -> wiseSayingController.deleteWiseSaying(rq)
                "빌드" -> wiseSayingController.buildJson()
                "종료" -> break
            }
        }
    }
}