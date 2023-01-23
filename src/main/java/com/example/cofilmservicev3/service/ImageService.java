package com.example.cofilmservicev3.service;

import com.example.cofilmservicev3.model.Photo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class ImageService {
    private final String rootFolder = "images";
    private String filmPosterRoot = rootFolder + "/film/";
    private String personPosterRoot = rootFolder + "/person/";
    private String personPhotosRoot = personPosterRoot + "/photo/";

    public String saveFilmPoster(MultipartFile multipartFile) throws IOException {
        return saveImage(filmPosterRoot, multipartFile);
    }

    public String savePersonPoster(MultipartFile multipartFile) throws IOException {
        return saveImage(personPosterRoot, multipartFile);
    }

    public String savePersonPhoto(MultipartFile multipartFile) throws IOException {
        return saveImage(personPhotosRoot, multipartFile);
    }

    public String updateImage(String imageNginxUri, MultipartFile image) throws IOException {

        if (image == null)
            return imageNginxUri;

        if (Files.deleteIfExists(Path.of(toFileSystemPath(imageNginxUri))))
            log.info("{} was deleted", imageNginxUri);


        return saveFilmPoster(image);
    }

    public void deleteImage(String imageNginxUri) throws IOException {
        if (Files.deleteIfExists(Path.of(toFileSystemPath(imageNginxUri)))) {
            log.info("{} was deleted.", imageNginxUri);
        } else {
            log.warn("{} wasn't deleted. Not found.");
        }
    }

    public void deletePersonPhotos(List<Photo> photos) throws IOException {
        for (String imageUri : photos.stream().map(Photo::getUri).toList()) {
            deleteImage(imageUri);
        }
    }

    private String saveImage(String destPath, MultipartFile image) throws IOException {
        String fileExtension = getExtension(image);
        String projectRelativePath = buildImagePath(destPath, fileExtension);
        File file = new File(projectRelativePath);
        Path absolutePath = Path.of(file.getAbsolutePath());
        log.info("Trying to write file to: {}", absolutePath);
        image.transferTo(absolutePath);
        log.info("File: " + absolutePath + " was saved");
        return toNginxUri(projectRelativePath);
    }

    private String buildImagePath(String directoryPath, String extension) {
        return directoryPath + UUID.randomUUID() + extension;
    }

    /**
     * @param imagePathUri
     * @return Image path in file system
     */
    private String toFileSystemPath(String imagePathUri) {

        return rootFolder + imagePathUri;
    }

    private String toNginxUri(String fileSystemPath) {
        return fileSystemPath.replaceFirst(rootFolder, "");
    }

    /**
     * Return extension with leading dot included. Example: .jpeg
     *
     * @param multipartFile
     * @return
     */
    @JsonIgnore
    private String getExtension(MultipartFile multipartFile) {

        return "." + multipartFile.getContentType().split("/")[1];
    }
}
