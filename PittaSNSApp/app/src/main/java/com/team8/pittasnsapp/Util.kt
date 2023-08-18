package com.team8.pittasnsapp

object Util {
    private fun hasSpecialCharacter(string: String): Boolean {
        for (i in string.indices) {
            if (!Character.isLetterOrDigit(string[i])) {
                return true
            }
        }
        return false
    } // 특수기호 제약 함수

    private fun hasAlphabet(string: String): Boolean {
        for (i in string.indices) {
            if (Character.isAlphabetic(string[i].code)) {
                return true
            }
        } // 알파벳 제약 함수
        return false
    }

    fun idRegex(id: String): Boolean {
        if ((!hasSpecialCharacter(id)) and (hasAlphabet(id)) and (id.length >= 5)) {
            return true
        }
        return false
    }

    fun pwRegax(pw: String): Boolean {
        return pw.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,16}$".toRegex())
    } //비밀번호 제약 같은 경우는 손대기가 어려워서 일단 공부해온 블로그에서 긁어 왔습니다. 제약이 너무 많다 싶으면  없애셔도 됩니다.
}