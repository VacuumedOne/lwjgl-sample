package util

import java.io.File
import java.nio.file.Paths

open class FileLoader {
    companion object {
        fun loadShader(filename:String):String {
            val rootDir = Paths.get("").toAbsolutePath().toString()
            val shaderPath = "$rootDir/src/main/resources/shader/$filename"
            println(shaderPath)

            val text = File(shaderPath).readText()
            return text            
        }
        // fun getStringFromFile(file: File) {

        // }
    }

}