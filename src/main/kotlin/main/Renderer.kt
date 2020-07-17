package main

import org.lwjgl.*
import org.lwjgl.glfw.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*

import java.nio.*

import org.lwjgl.glfw.Callbacks.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION

import primitive.Triangle
import primitive.LineLoop

class Renderer {

    fun run() {       

        val window = init()

        printGLInfo()

        loop(window)

        glfwFreeCallbacks(window)

    }

    fun init() : Long {
        GLFWErrorCallback.createPrint(System.err).set()

        check(glfwInit()) { "Unable to initialize GLFW" }

        glfwDefaultWindowHints()
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        val window = glfwCreateWindow(800, 800, "Renderer",0L,0L)
        check(window != 0L) { "Unable to initialize GLFW window" }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)
        GL.createCapabilities()

        return window
    }

    fun loop(window: Long) {
        glClearColor(0f, 0f, 0f, 1f)
        val obj = Triangle()

        while( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            // draw
            obj.draw()

            glfwSwapBuffers(window)
            glfwPollEvents()
        }
    }

    fun printGLInfo() {
        val lwjglVersion = Version.getVersion()
        val glVersion = glGetString(GL_VERSION)
        val glVendor = glGetString(GL_VENDOR)
        val glRenderer = glGetString(GL_RENDERER)
        val glShaderVersion = glGetString(GL_SHADING_LANGUAGE_VERSION)
        println("LWJGL version is " + lwjglVersion)
        println("OpenGL version is " + glVersion)
        println("OpenGL vendor is " + glVendor)
        println("OpenGL renderer is " + glRenderer)
        println("GLSL Version is " + glShaderVersion)
    }
}

fun main(args: Array<String>) {
    Renderer().run()
}