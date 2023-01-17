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
import java.util.stream.Collectors;


@Service
@PropertySource("classpath:application-nginx.yml")
@ConfigurationProperties(prefix = "nginx.server")
@Slf4j
public class ImageService {
    @Setter
    private String address;
    @Setter
    private String port;
    private String bootstrapNginx;
    private  String fileSystemRoot = new File(".").getAbsolutePath() + "/images";
    private  String filmPosterRoot = fileSystemRoot + "/film/";
    private  String personPosterRoot = fileSystemRoot + "/person/";
    private  String personPhotosRoot = personPosterRoot + "/photo/";

    @PostConstruct
    public void init() {
        log.info("Loaded config: address - {}, port - {}", address, port);
        this.bootstrapNginx = address + ":" + port;
    }


    public String saveFilmPoster(MultipartFile multipartFile) throws IOException {

        String fileExtension = getExtension(multipartFile);
        File file = new File(buildImagePath(filmPosterRoot, fileExtension));
        log.info("Trying to write file to: {}", file.getAbsolutePath());
        multipartFile.transferTo(file);
        log.info("File: " + file.getAbsolutePath() + " was saved");
        return bootstrapNginx + file.getAbsolutePath().replaceFirst(fileSystemRoot, "");
    }

    public String savePersonPoster(MultipartFile multipartFile) throws IOException {
        String fileExtension = getExtension(multipartFile);
        File file = new File(buildImagePath(personPosterRoot, fileExtension));
        log.info("Trying to write file to: {}", file.getAbsolutePath());
        multipartFile.transferTo(file);
        log.info("File: " + file.getAbsolutePath() + " was saved");
        return bootstrapNginx + file.getAbsolutePath().replaceFirst(fileSystemRoot, "");
    }

    public String savePersonPhoto(MultipartFile multipartFile) throws IOException {

        String fileExtension = getExtension(multipartFile);
        File file = new File(buildImagePath(personPhotosRoot, fileExtension));
        log.info("Trying to write file to: {}", file.getAbsolutePath());
        multipartFile.transferTo(file);
        log.info("File: " + file.getAbsolutePath() + " was saved");
        return bootstrapNginx + file.getAbsolutePath().replaceFirst(fileSystemRoot, "");
    }

    public String updateImage(String imagePathUri, MultipartFile image) throws IOException {

        if (image == null)
            return imagePathUri;

        if (Files.deleteIfExists(Path.of(parseImageUri(imagePathUri))))
            log.info("{} was deleted", imagePathUri);


        return saveFilmPoster(image);
    }

    public void deletePhotos(List<Photo> photos) throws IOException {

        List<String> photosUri = photos.stream().map(Photo::getPath).toList();

        String imagePath;
        for (String photoUri : photosUri) {
            imagePath = parseImageUri(photoUri);
            if (Files.deleteIfExists(Path.of(imagePath)))
                log.info("{} was deleted", imagePath);
            else
                log.warn("{} can not delete", imagePath);
        }
    }

    private String buildImagePath(String directoryPath, String extension) {
        return directoryPath + UUID.randomUUID() + extension;
    }
    /**
     *
     * @param imagePathUri
     * @return Image path in file system
     */
    private String parseImageUri(String imagePathUri) {

        int start = imagePathUri.indexOf("/", 10);

        return fileSystemRoot + imagePathUri.substring(start);
    }

    /**
     * Return extenstion with leading dot included. Example: .jpeg
     * @param multipartFile
     * @return
     */
    @JsonIgnore
    private String getExtension(MultipartFile multipartFile) {

        return "." + multipartFile.getContentType().split("/")[1];
    }
}
