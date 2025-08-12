package com.ll.domain.wiseSaying.service

import com.ll.domain.wiseSaying.entity.WiseSaying
import com.ll.domain.wiseSaying.repository.WiseSayingRepository

class WiseSayingService {
    private val wiseSayingRepository = WiseSayingRepository()

    fun getWiseSayings(): List<WiseSaying> {
        return wiseSayingRepository.findAllReversed()
    }

    fun getWiseSayingById(id: Int): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun createWiseSaying(content: String, author: String): WiseSaying {
        val wiseSaying = WiseSaying(content, author)
        wiseSayingRepository.save(wiseSaying)

        return wiseSaying
    }

    fun modifyWiseSaying(wiseSaying: WiseSaying, content: String, author: String) {
        wiseSaying.modify(content, author)
        wiseSayingRepository.save(wiseSaying)
    }

    fun deleteWiseSaying(wiseSaying: WiseSaying) {
        wiseSayingRepository.delete(wiseSaying)
    }


    // --- File
    fun loadData() {
        wiseSayingRepository.loadData()
    }

    fun buildJson() {
        wiseSayingRepository.buildJson()
    }
}