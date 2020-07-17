package primitive

import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import com.jme3.math.Vector3f
import main.FileLoader

class Triangle : Primitive {
    val size:Float = 0.5f
    val vertices:FloatArray = FloatArray(9)
    var vbo:Int = 0
    var vao:Int = 0
    var vs:Int = 0
    var fs:Int = 0
    var program:Int = 0

    constructor() {
        val vs_text = FileLoader.loadShader("triangle.vert")
        vs = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vs, vs_text)
        glCompileShader(vs)

        val fs_text = FileLoader.loadShader("triangle.frag")
        fs = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fs, fs_text)
        glCompileShader(fs)

        program = glCreateProgram()
        glAttachShader(program, vs)
        glAttachShader(program, fs)
        glLinkProgram(program)
        glUseProgram(program)
        // TODO: コンパイルチェック

        val posLocation = glGetAttribLocation(program, "position")

        val r = 0.2f
        for (i in 0..2) {
            vertices[3*i + 0] = Math.cos(i * Math.PI * 2f / 3f).toFloat()
            vertices[3*i + 1] = Math.sin(i * Math.PI * 2f / 3f).toFloat()
            vertices[3*i + 2] = 0f
        }

        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0) // ?
        
        glBindBuffer(GL_ARRAY_BUFFER, 0) //deselect
        glBindVertexArray(0) //deselect
    }
    override fun draw() {
        
        glBindVertexArray(vao)
        glEnableVertexAttribArray(0) // ?

        glDrawArrays(GL_TRIANGLES, 0, 3)

        glDisableVertexAttribArray(0)
        glBindVertexArray(0)

    }
}