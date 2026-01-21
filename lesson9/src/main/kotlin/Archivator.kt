import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.math.pow
fun addAllFiles(mainPath: File, filePath: File, zos: ZipOutputStream) {
    if (filePath.isDirectory) {
        for (file in filePath.listFiles()?: return) {
            addAllFiles(mainPath, file, zos)
        }
        return;
    }
    if (!filePath.isFile) {
        return;
    }
    val ext = filePath.extension.lowercase();
    val flag = ext == "txt" || ext == "log";
    if (!flag) {
        return;
    }
    val nameOfFile = mainPath.toPath().relativize(filePath.toPath()).toString()
    val writings = ZipEntry(nameOfFile);
    zos.putNextEntry(writings)
    println("Archived: $nameOfFile, size: ${filePath.length()}")
    FileInputStream(filePath).use { fis ->
        val max = ByteArray(16384);
        while (true) {
            val read = fis.read(max)
            if (read == -1) {
                break;
            }
            zos.write(max, 0, read)
        }

    }
    zos.closeEntry()
}
fun archiver(fromPath: File, zipPath: File) {
    if (!fromPath.exists() || !fromPath.isDirectory) {
        throw IllegalArgumentException("Path from convert does not exist")
    }
    zipPath.parentFile?.mkdirs()
    FileOutputStream(zipPath).use {fos ->
        ZipOutputStream(fos).use { zos ->
            addAllFiles(fromPath, fromPath, zos);
        }
    }
}
//fun main() {
//    val from = File("lesson9/testForArchivator")
//    val to = File("lesson9/archivedTest.zip")
//    archiver(from, to)
//}