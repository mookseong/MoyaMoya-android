package com.kbu.lib.adapter

/**
 *  키보드 관련 이벤트를 처리할 수 있는 인터페이스
 */
interface KeyEventListener {
    /**
     * 키보드 이벤트 발생시킵니다.
     */
    fun keyEvent()

    /**
     * 키보드 이벤트 발생 이후 다시 처리할 로직을 지정합니다.
     */
    fun keyEventEnd()
}