package com.ll.domain.wiseSaying.controller

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.domain.wiseSaying.service.WiseSayingService
import com.ll.global.rq.Rq

class WiseSayingController {
    private val wiseSayingService = WiseSayingService()

    fun readWiseSayings() {
        println("번호 / 작가 / 명언")
        println("----------------------")

        for (ws in wiseSayingService.getWiseSayings()) {
            println("${ws.id} / ${ws.author} / ${ws.content}")
        }
    }

    fun createWiseSaying() {
        print("명언 : ")
        val content = readNotBlankLine()

        print("작가 : ")
        val author = readNotBlankLine()

        val wiseSaying = wiseSayingService.createWiseSaying(content, author)

        println("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun modifyWiseSaying(rq: Rq) {
        val id = getParamId(rq) ?: return
        val wiseSaying = getWiseSayingById(id) ?: return

        println("명언(기존) : ${wiseSaying.content}")
        print("명언 : ")
        val newContent = readNotBlankLine()

        println("작가(기존) : ${wiseSaying.author}")
        print("작가 : ")
        val newAuthor = readNotBlankLine()

        wiseSayingService.modifyWiseSaying(wiseSaying, newContent, newAuthor)
    }

    fun deleteWiseSaying(rq: Rq) {
        val id = getParamId(rq) ?: return
        val wiseSaying = getWiseSayingById(id) ?: return

        wiseSayingService.deleteWiseSaying(wiseSaying)

        println("${id}번 명언이 삭제 되었습니다.")
    }


    // --- helper

    private fun getParamId(rq: Rq): Int? {
        val id = rq.getParamInt("id")

        if (id == null) {
            println("명언 번호를 입력해주세요.")
        }
        return id
    }

    private fun getWiseSayingById(id: Int): WiseSaying? {
        val wiseSaying = wiseSayingService.getWiseSayingById(id)

        if (wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return null
        }
        return wiseSaying
    }

    private fun readNotBlankLine(): String {
        while (true) {
            val input = readLine()?.trim()
            if (!input.isNullOrEmpty()) {
                return input
            }
            println("값을 반드시 입력해야 합니다.")
        }
    }


    // --- File

    fun buildJson() {
        wiseSayingService.buildJson()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }

     fun loadData() {
        wiseSayingService.loadData()
    }
}