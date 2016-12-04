package com.richodemus.advent_of_code.two_thousand_sixteen

import java.io.File
import java.net.URL

fun String.toFile() = ClassLoader.getSystemClassLoader().getResource(this).toFile()
private fun URL.toFile(): File = File(this.file)
