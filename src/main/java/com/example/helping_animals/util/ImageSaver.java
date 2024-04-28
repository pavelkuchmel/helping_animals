package com.example.helping_animals.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Component
public class ImageSaver {

    @Value("${photo.animal.directory.upload}")
    private String photoAnimalDirectoryUpload;

    public boolean delete(String image){
        if (image != null){
            String name = image.substring(image.lastIndexOf("/") + 1);
            Path pathAndName = Paths.get("c:/JavaCode/helping_animals/upload/animals", name);
            File file = new File(pathAndName.toUri());
            if (file.exists()){
                return file.delete();
            }
        }
        return false;
    }

    public String save(MultipartFile image) throws IOException {
        Path filePathAndName = null;
        File uploadDir = null;
        StringBuilder name = null;
        if (image != null && image.getOriginalFilename() != null){
            uploadDir = new File(photoAnimalDirectoryUpload);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            name = new StringBuilder();
            name.append("s")
                    .append(new Random().nextInt(10000000, 99999999))
                    .append(image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".")-1));
            filePathAndName = Paths.get(photoAnimalDirectoryUpload, name.toString());
            Files.write(filePathAndName, image.getBytes());
        }
        return "/img/animals/" + name.toString();
        /*StringBuilder filePathAndPathForView;
        String fileName;
        Path filePathAndName;
        do{
            fileName = "s"+ new Random().nextInt(10000000, 99999999) +".jpg";
            filePathAndName = Paths.get(photoAnimalDirectoryUpload, fileName);
        }while (new File(filePathAndName.toString()).exists());
        filePathAndPathForView = new StringBuilder(photoAnimalDirectoryLoad);
        Files.write(filePathAndName, image.getBytes());
        filePathAndPathForView.append(fileName);
        return filePathAndPathForView.toString();*/
    }
}
