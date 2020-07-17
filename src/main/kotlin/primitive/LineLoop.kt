package primitive

import org.lwjgl.opengl.GL11.*

// シェーダを使用せずに頂点情報を与える

class LineLoop : Primitive {
    override fun draw() {
        glLineWidth(5f)
        glColor3f(0f, 1f, 0f) //色がセットされない？
        glBegin(GL_LINE_LOOP)
            glVertex3f(-0.5f, 0.5f, 0.0f)
            glVertex3f(0.5f, 0.5f, 0.0f)
            glVertex3f(0f, -0.5f, 0.0f)
        glEnd()
    }
}