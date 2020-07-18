package util

import org.lwjgl.opengl.GL30.*

fun createVBO(vertices: FloatArray): Int {
    val vbo = glGenBuffers()
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)
    glBindBuffer(GL_ARRAY_BUFFER, 0)
    return vbo
}

fun createIBO(indexes: IntArray): Int {
    val ibo = glGenBuffers()
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexes, GL_STATIC_DRAW)
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    return ibo
}

fun createVAO(): Int {
    val vao = glGenVertexArrays()
    return vao
}

fun registerVBO(vao:Int, vbo:Int, loc:Int, size:Int) {
    glBindVertexArray(vao)

    glEnableVertexAttribArray(loc)
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glVertexAttribPointer(loc, size, GL_FLOAT, false, 0, 0)

    glBindVertexArray(0)
    glBindBuffer(GL_ARRAY_BUFFER, 0)
}