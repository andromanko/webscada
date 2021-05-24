package webscada.services.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.experimental.UtilityClass;
import webscada.api.dto.UserDto;

@UtilityClass
public class LogoFileUploader {

    private static final String IMAGE_EXTENSION = ".jpg";
    //private static final String LOGOS_FOLDER_PATH = "classpath:static/images/";
    private static final String LOGOS_FOLDER_PATH = "static/images/";

    public void updateOrCreateLogo(MultipartFile file, UserDto dto) throws IOException {
        if (file != null && !file.isEmpty()) {
            String login = dto.getLogin();
            String filePath = new StringBuilder(LOGOS_FOLDER_PATH).append(login).append(IMAGE_EXTENSION)
                    .toString();
            File userLogo;
            try {
                userLogo = ResourceUtils.getFile(filePath);
            } catch (FileNotFoundException e) {
                URL fileUrl = ResourceUtils.getURL(LOGOS_FOLDER_PATH);
                userLogo = new File(
                        new StringBuilder(fileUrl.getPath()).append(login).append(IMAGE_EXTENSION).toString());
            }
            Path path = Paths.get(userLogo.getPath());
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        }
    }

    public void updateLogo(MultipartFile file, UserDto dto) throws IOException {
        if (file != null && !file.isEmpty()) {
            File existingFile = ResourceUtils.getFile(new StringBuilder(LOGOS_FOLDER_PATH).append(dto.getLogin())
                    .append(IMAGE_EXTENSION).toString());
            String strForDebug = existingFile.getCanonicalPath();   //.getPath();
            Path path = Paths.get(strForDebug);
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        }
    }
}
