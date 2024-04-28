package com.example.helping_animals.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QRUtilAndSaver {

    @Value("${qr.animal.directory.upload}")
    private String qrAnimalDirectoryUpload;
    @Value("${qr.char-set}")
    private String qrCharSet;
    @Value("${qr.size}")
    private int height;
    @Value("${qr.size}")
    private int width;
    public void createQRandSave(String data, String name)
            throws WriterException, IOException {

        File uploadDir = null;
        Path pathAndName = null;
        name += ".png";

        if (!data.isBlank() && !name.isBlank()) {
            uploadDir = new File(qrAnimalDirectoryUpload);
            pathAndName = Paths.get(qrAnimalDirectoryUpload, name);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(data.getBytes(qrCharSet), qrCharSet),
                    BarcodeFormat.QR_CODE, width, height);

            MatrixToImageWriter.writeToFile(
                    matrix,
                    pathAndName.toString().substring(pathAndName.toString().lastIndexOf('.') + 1),
                    new File(pathAndName.toUri()));

        }

    }
    public static String readQR(String path, String charset, Map hashMap)
            throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap
                = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(
                                new FileInputStream(path)))));

        Result result
                = new MultiFormatReader().decode(binaryBitmap);

        return result.getText();
    }
    // Driver code
    /*public static void main(String[] args) throws WriterException, IOException, NotFoundException {

        // The data that the QR code will contain
        String data = "www.geeksforgeeks.org";

        // The path where the image will get saved
        String path = "demo.png";

        // Encoding charset
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                ErrorCorrectionLevel>();

        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);

        // Create the QR code and save
        // in the specified folder
        // as a jpg file
        createQR(data, path, charset, hashMap, 200, 200);
        System.out.println("QR Code Generated!!! ");
        System.out.println("QRCode output: " + readQR(path, charset, hashMap));
    }*/
}