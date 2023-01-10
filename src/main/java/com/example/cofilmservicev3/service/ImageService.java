package com.example.cofilmservicev3.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    private final String filmPosterRoot = "/data/images/film/";
    private final String personPosterRoot = "/data/images/person/";
    private final String personPhotosRoot = "/data/images/person/photo/";

    /**
     *
     * @param multipartFile
     * @return Absolute path to saved file
     * @throws IOException
     */
    public String saveFilmPoster(MultipartFile multipartFile) throws IOException {

        String fileExtension = getExtension(multipartFile);
        File file = new File(filmPosterRoot + UUID.randomUUID() + fileExtension);
        multipartFile.transferTo(file);
        log.info("File: " + file.getAbsolutePath() + " was saved");
        return file.getAbsolutePath();
    }

    public String savePersonPoster(MultipartFile multipartFile) throws IOException {

        String fileExtension = getExtension(multipartFile);
        File file = new File(personPosterRoot + UUID.randomUUID() + fileExtension);
        multipartFile.transferTo(file);
        log.info("File: " + file.getAbsolutePath() + " was saved");
        return file.getAbsolutePath();
    }

    public String updateImage(String oldImagePath, MultipartFile image) throws IOException {

        if (image == null)
            return oldImagePath;

        Files.deleteIfExists(Path.of(oldImagePath));

        return saveFilmPoster(image);
    }

    private String getExtension(MultipartFile multipartFile) {
        return "." + multipartFile.getContentType().split("/")[1];
    }

    public String savePersonPhoto(MultipartFile multipartFile) throws IOException {

        String fileExtension = getExtension(multipartFile);
        File file = new File(personPhotosRoot + UUID.randomUUID() + fileExtension);
        multipartFile.transferTo(file);
        log.info("File: " + file.getAbsolutePath() + " was saved");
        return file.getAbsolutePath();
    }
}
