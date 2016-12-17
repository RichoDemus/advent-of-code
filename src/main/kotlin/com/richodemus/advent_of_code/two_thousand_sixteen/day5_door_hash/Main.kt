package com.richodemus.advent_of_code.two_thousand_sixteen.day5_door_hash

import org.assertj.core.api.Assertions.assertThat
import java.security.MessageDigest

fun main(args: Array<String>) {
    assertThat("abc3231929".hash().startsWith5Zeros()).isTrue()

    assertThat("abc".solve()).isEqualTo("18f47a30")
    assertThat("abc".solve2()).isEqualTo("05ace8e3")

    val doorId = "abbhdwsy"
    val password = doorId.solve()
    println("The password for door $doorId is $password")
    assertThat(password).isEqualTo("801b56a7")

    val password2 = doorId.solve2()
    println("The second password for door $doorId is $password2")
    assertThat(password2).isEqualTo("424a0197")
}

private fun String.solve(): String {
    var password = ""
    var index = 0
    while (password.length != 8) {
        while ((this + index.toString()).hash().startsWith5Zeros() == false) {
            index++
        }
        val hit = bytesToHex((this + index.toString()).hash())
        password += hit.getFirstPiece()
        index++
    }
    return password
}

private fun String.solve2(): String {
    var password = "åååååååå"
    var index = 0
    while (password.count { it == 'å' } != 0) {
        while ((this + index.toString()).hash().startsWith5Zeros() == false) {
            index++
        }
        val hit = bytesToHex((this + index.toString()).hash())
        val position: Int
        try {
            position = hit.getFirstPiece().toString().toInt()
        } catch(e: Exception) {
            index++
            continue
        }
        if (position >= password.length) {
            index++
            continue
        }
        val char = hit.getSecondPiece()
        if (password[position] == 'å') {
            password = password.replaceRange(position, position + 1, char.toString())
        }
        index++
    }
    return password
}

private fun String.getFirstPiece(): Char {
    return this[5]
}

private fun String.getSecondPiece(): Char {
    return this[6]
}

private fun String.hash(): ByteArray {
    val instance = MessageDigest.getInstance("md5")
    instance.update(this.toByteArray())
    return instance.digest()
}

private fun ByteArray.startsWith5Zeros(): Boolean {
    // To be padded with 5 zeros in hex, the first two bytes must both be zero
    if (this[0].toInt() != 0 || this[1].toInt() != 0) {
        return false
    }
    val hex = bytesToHex(this)
    if (hex[4] == '0') return true
    return false
}

private val hexArray = "0123456789abcdef".toCharArray()
private fun bytesToHex(bytes: ByteArray): String {
    val hexChars = CharArray(bytes.size * 2)
    for (j in bytes.indices) {
        val v = bytes[j].toInt() and 0xFF
        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}
