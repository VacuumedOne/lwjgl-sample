package primitive

import org.lwjgl.opengl.GL30.*
import util.*

class Sphere : Primitive {
    var vbo:Int = 0
    var vao:Int = 0
    var ibo:Int = 0
    var index_count = 0

    constructor() {
        val DX = 50 // x 格子点の数
        val DY = 50 // y 格子点の数
        val vertices:FloatArray = FloatArray(DX*DY*3)
        
        for (x in 0..DX-1) {
            for (y in 0..DY-1) {
                vertices[(x*DY + y)*3 + 0] = -1f + x * 2f / (DX-1)
                vertices[(x*DY + y)*3 + 1] = -1f + y * 2f / (DY-1)
                vertices[(x*DY + y)*3 + 2] = 0.0f
            }
        }

        val indices:IntArray = IntArray((DX-1)*(DY-1)*3)
        for (x in 0..DX-2) {
            for (y in 0..DY-2) {
                indices[(x*(DY-1) + y)*3 + 0] = x*DX + y
                indices[(x*(DY-1) + y)*3 + 1] = x*DX + (y+1)
                indices[(x*(DY-1) + y)*3 + 2] = (x+1)*DX + y
            }
        }
        index_count = indices.size

        setProgram("sphere.vert", "sphere.frag")
        vbo = createVBO(vertices)
        ibo = createIBO(indices)
        vao = createVAO()
        registerVBO(vao, vbo, 0, 3)
    }
    
    override fun draw() {
        glBindVertexArray(vao)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)

        glDrawElements(GL_TRIANGLES, index_count, GL_UNSIGNED_INT, 0)
    }
}