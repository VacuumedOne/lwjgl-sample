package util

import org.lwjgl.opengl.GL30.*
import util.*

fun setProgram(vspath: String, fspath: String):Int {
    val vs_text = FileLoader.loadShader(vspath)
    val vs = glCreateShader(GL_VERTEX_SHADER)
    glShaderSource(vs, vs_text)
    glCompileShader(vs)
    if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
        println("vertex shader compile error")
    }

    val fs_text = FileLoader.loadShader(fspath)
    val fs = glCreateShader(GL_FRAGMENT_SHADER)
    glShaderSource(fs, fs_text)
    glCompileShader(fs)
    if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
        println("fragment shader compile error")
    }

    val program = glCreateProgram()
    glAttachShader(program, vs)
    glDeleteShader(vs)
    glAttachShader(program, fs)
    glDeleteShader(fs)
    glLinkProgram(program)

    if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
        println("program failed to link")
    }

    glUseProgram(program)

    checkError()

    return program
}

