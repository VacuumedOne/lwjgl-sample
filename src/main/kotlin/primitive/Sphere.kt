package primitive

import java.nio.FloatBuffer
import org.lwjgl.opengl.GL30.*
import com.jme3.math.*

import util.*

class Sphere : Primitive {
    var vbo_position:Int = 0
    var vbo_normal:Int = 0
    var vao:Int = 0
    var ibo:Int = 0
    var index_count = 0
    var modelLoc = -1
    var modelMat:Matrix4f = Matrix4f()
    var time:Float = 1f
    var timeLoc:Int = -1
    var nowL:Int = 0
    var nowLLoc:Int = -1
    var nowM:Int = 0
    var nowMLoc:Int = -1
    var nextL:Int = 1
    var nextLLoc:Int = -1
    var nextM:Int = 0
    var nextMLoc:Int = -1

    var rotX = 0f
    var rotY = 0f
    var rotZ = 0f

    constructor() {
        val DX = 100 // x 格子点の数
        val DY = 101 // y 格子点の数

        val vertices:FloatArray = FloatArray(DX*DY*3)
        for (x in 0..DX-1) {
            for (y in 0..DY-1) {
                vertices[(x*DY + y)*3 + 0] = -1f + x * 2f / DX
                vertices[(x*DY + y)*3 + 1] = -1f + y * 2f / (DY-1)
                vertices[(x*DY + y)*3 + 2] = 0.1f
            }
        }

        val indices:IntArray = IntArray(DX*(DY-1)*6)
        for (x in 0..DX-1) {
            for (y in 0..DY-2) {
                indices[(x*(DY-1) + y)*6 + 0] = x*DY + y
                indices[(x*(DY-1) + y)*6 + 2] = ((x+1)%DX)*DY + y
                indices[(x*(DY-1) + y)*6 + 1] = x*DY + (y+1)
                indices[(x*(DY-1) + y)*6 + 3] = ((x+1)%DX)*DY + y
                indices[(x*(DY-1) + y)*6 + 5] = ((x+1)%DX)*DY + (y+1)
                indices[(x*(DY-1) + y)*6 + 4] = x*DY + (y+1)
            }
        }
        index_count = indices.size

        val program = setProgram("sphere.vert", "sphere.frag")
        val positionLoc = glGetAttribLocation(program, "position")
        modelLoc = glGetUniformLocation(program, "model")
        timeLoc = glGetUniformLocation(program, "time")
        nowLLoc = glGetUniformLocation(program, "nowL")
        nowMLoc = glGetUniformLocation(program, "nowM")
        nextLLoc = glGetUniformLocation(program, "nextL")
        nextMLoc = glGetUniformLocation(program, "nextM")
        checkError()

        vbo_position = createVBO(vertices)
        ibo = createIBO(indices)
        vao = createVAO()
        registerVBO(vao, vbo_position, positionLoc, 3)
    }
    
    override fun draw() {
        rotX += 0.005f
        rotY += 0.0025f
        rotZ += 0.01f
        time += 1f
        if (time % 300f < 0.0001f) {
            nowL = nextL
            nowM = nextM
            if (nextL == nextM) {
                nextL += 1
                nextM = 0
            } else {
                nextM += 1
            }
        }
        

        modelMat.setRotationQuaternion(Quaternion().fromAngles(rotX, rotY, rotZ))
        // println(modelMat)

        glBindVertexArray(vao)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glUniformMatrix4fv(modelLoc, false, modelMat.toFloatBuffer(true))
        glUniform1f(timeLoc, time)
        glUniform1i(nowLLoc, nowL)
        glUniform1i(nowMLoc, nowM)
        glUniform1i(nextLLoc, nextL)
        glUniform1i(nextMLoc, nextM)

        glDrawElements(GL_TRIANGLES, index_count, GL_UNSIGNED_INT, 0)
    }
}