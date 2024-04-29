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

import static com.example.helping_animals.util.CryptoUtils.*;

@Component
public class PassportSaver {

    private final String passportUserDirectoryUploadTemp = "c:/JavaCode/helping_animals/upload/passports/temp";

    private final String passportUserDirectoryUploadConst = "c:/JavaCode/helping_animals/upload/passports/const";

    public String save(MultipartFile passport) throws IOException, CryptoException {
        Path filePathAndName = null;
        File uploadDirTemp = null;
        File uploadDirConst = null;
        StringBuilder name = null;
        if (passport != null && passport.getOriginalFilename() != null){
            uploadDirTemp = new File(passportUserDirectoryUploadTemp);
            uploadDirConst = new File(passportUserDirectoryUploadConst);
            if (!uploadDirTemp.exists()){
                uploadDirTemp.mkdir();
            }
            if (!uploadDirConst.exists()){
                uploadDirConst.mkdir();
            }
            name = new StringBuilder();
            name.append("p")
                    .append(new Random().nextInt(10000000, 99999998))
                    .append(passport.getOriginalFilename().substring(passport.getOriginalFilename().lastIndexOf(".")-1));
            filePathAndName = Paths.get(passportUserDirectoryUploadTemp, name.toString());
            Files.write(filePathAndName, passport.getBytes());
            Path filePathAndNameEcn = Paths.get(passportUserDirectoryUploadConst, name.toString());
            encrypt(filePathAndName.toFile(), filePathAndNameEcn.toFile());
            Files.deleteIfExists(filePathAndName);
        }
        return "/img/passports/" + name.toString();
    }

    public void decrypt(String name) throws CryptoException {
        CryptoUtils.decrypt(Paths.get(passportUserDirectoryUploadConst, name).toFile(), Paths.get(passportUserDirectoryUploadTemp, name).toFile());
    }
}
