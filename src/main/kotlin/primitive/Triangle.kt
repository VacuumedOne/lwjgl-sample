package primitive

// import org.lwjgl.opengl.GL15.*
// import org.lwjgl.opengl.GL20.*
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
        if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
            println("vertex shader compile error")
        }

        val fs_text = FileLoader.loadShader("triangle.frag")
        fs = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fs, fs_text)
        glCompileShader(fs)
        if (glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
            println("fragment shader compile error")
        }

        program = glCreateProgram()
        glAttachShader(program, vs)
        glAttachShader(program, fs)
        glLinkProgram(program)

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            println("program failed to link")
        }

        glUseProgram(program)

        val err = glGetError()
        if (err != GL_NO_ERROR) println("Error happens!")
        if (err == GL_INVALID_VALUE) println("invalud value")
        if (err == GL_INVALID_OPERATION) println("invalud operation")
        
        // TODO: コンパイルチェック

        // val posLocation = glGetAttribLocation(program, "position")

        for (i in 0..2) {
            vertices[3*i + 0] = Math.cos(i * Math.PI * 2f / 3f).toFloat()
            vertices[3*i + 1] = Math.sin(i * Math.PI * 2f / 3f).toFloat()
            vertices[3*i + 2] = 0f
        }

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        // position location
        glEnableVertexAttribArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
        
        glBindBuffer(GL_ARRAY_BUFFER, 0) //deselect
        glBindVertexArray(0) //deselect

    }
    override fun draw() {
        glBindVertexArray(vao)
        glDrawArrays(GL_TRIANGLES, 0, 3)

        // glDisableVertexAttribArray(0)
        glBindVertexArray(0)

    }
}