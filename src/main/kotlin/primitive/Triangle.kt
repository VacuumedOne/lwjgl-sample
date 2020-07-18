package primitive

import org.lwjgl.opengl.GL30.*
import com.jme3.math.Vector3f
import org.lwjgl.BufferUtils

import util.*

class Triangle : Primitive {
    var vbo:Int = 0
    var vao:Int = 0
    var program:Int = 0

    constructor() {

        val vertices:FloatArray = floatArrayOf(
             1.0f, 1.0f, 0.0f,
             1.0f,-1.0f, 0.0f,
            -1.0f,-1.0f, 0.0f
        )

        val program = setProgram("triangle.vert", "triangle.frag")
        vbo = createVBO(vertices)
        vao = createVAO()
        registerVBO(vao, vbo, 0, 3)

    }
    override fun draw() {

        glBindVertexArray(vao)

        glDrawArrays(GL_TRIANGLES, 0, 3)

    }
}