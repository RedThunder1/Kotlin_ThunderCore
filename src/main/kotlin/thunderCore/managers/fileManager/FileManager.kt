package thunderCore.managers.fileManager

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files

object FileManager {

    fun readFile(fileName: File?): String? {
        try {
            val fileReader = FileReader(fileName!!)
            val builder = StringBuilder()
            var ch: Int
            while (fileReader.read().also { ch = it } != -1) {
                builder.appendCodePoint(ch)
            }
            val message = builder.toString()
            fileReader.close()
            return message
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun writeFile(fileName: File, toWrite: String) {
        try {
            fileName.createNewFile()
            val fileWriter = FileWriter(fileName)
            for (element in toWrite) {
                fileWriter.write(element.code)
            }
            fileWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(RuntimeException::class)
    private fun copyFileStructure(source: File, target: File) {
        try {
            val ignore = ArrayList(mutableListOf("uid.dat", "session.lock"))
            if (!ignore.contains(source.name)) {
                if (source.isDirectory) {
                    if (!target.exists() && !target.mkdirs()) throw IOException("Couldn't create world directory!")
                    val files = source.list()
                    for (file in files!!) {
                        val srcFile = File(source, file)
                        val destFile = File(target, file)
                        copyFileStructure(srcFile, destFile)
                    }
                } else {
                    val `in` = Files.newInputStream(source.toPath())
                    val out = Files.newOutputStream(target.toPath())
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (`in`.read(buffer).also { length = it } > 0) out.write(buffer, 0, length)
                    `in`.close()
                    out.close()
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun copyWorld(originalWorld: World, newWorldName: String?) {
        copyFileStructure(originalWorld.worldFolder, File(Bukkit.getWorldContainer(), newWorldName!!))
        WorldCreator(newWorldName).createWorld()
    }

}