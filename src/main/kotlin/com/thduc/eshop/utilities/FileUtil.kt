package com.thduc.eshop.utilities


import com.thduc.eshop.constant.UploadConstant
import com.thduc.eshop.constant.UploadType
import com.thduc.eshop.entity.Media
import com.thduc.eshop.entity.User
import org.apache.commons.io.FilenameUtils
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File
import java.nio.file.*
import java.util.stream.Stream

@Component
class FileUtil {
    val log: Logger? = LoggerFactory.getLogger(this::class.java)
    val rootLocation = Paths.get(UploadConstant.UPLOAD_PATH)

    fun store(folder: String, user: User, file: MultipartFile?, type: UploadType): Media {
        val folderPath: String = UploadConstant.UPLOAD_PATH + folder
        val relativePath: String = folder
        val newFolder = File(folderPath)
        if (!newFolder.exists())
            newFolder.mkdir()
        val extension: String = FilenameUtils.getExtension(file!!.originalFilename)
        val newFileName = Helper.currentTime(type)+"."+extension
        var fileName: String = "$folderPath/$newFileName"
        val destinationPaths = Paths.get(fileName)
        file!!.transferTo(destinationPaths)
        return Media(mediaPath = "$relativePath/$newFileName", mediaType = extension, authorId = user.id)
    }

    fun loadFile(filename: String): Resource {
        val file = rootLocation.resolve(filename)
        val resource = UrlResource(file.toUri())
        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw RuntimeException("FAIL!")
        }
    }

    fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    fun init() {
        Files.createDirectory(rootLocation)
    }

    fun loadFiles(): Stream<Path> {
        return Files.walk(this.rootLocation, 1)
            .filter { path -> !path.equals(this.rootLocation) }
            .map(this.rootLocation::relativize)
    }
}