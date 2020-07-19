package util

import org.lwjgl.opengl.GL30.*

fun checkError() {
    val err = glGetError()
    if (err == GL_INVALID_VALUE) println("invalud value")
    if (err == GL_INVALID_OPERATION) println("invalud operation")
}