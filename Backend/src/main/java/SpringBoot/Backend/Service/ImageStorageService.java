package SpringBoot.Backend.Service;

import SpringBoot.Backend.DTO.IconDTO;
import SpringBoot.Backend.Entity.Icon;
import SpringBoot.Backend.Repository.IconRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService {
    @Autowired
    private IconRepository iconRepository;
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());
    }
    public IconDTO storeFile(MultipartFile file, String id) {
        try {
            if(file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }

            if(!isImageFile(file)) {
                throw new RuntimeException("You can only upload image file");
            }

            float fileSizeInMegaBytes = file.getSize() / 1_000_000.0f;
            if(fileSizeInMegaBytes > 5.0f) {
                throw new RuntimeException("File must be < 5Mb");
            }

            // lấy ra đuôi file
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

            //random tên file
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            System.out.println(generatedFileName);
            generatedFileName = generatedFileName + "." + fileExtension;

            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            //System.out.println(destinationFilePath);

            if(!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory");
            }

            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }

            Optional<Icon> foundIcon = iconRepository.findById(id);
            if(foundIcon.isPresent()) {
                foundIcon.get().setImage(generatedFileName);
                iconRepository.save(foundIcon.get());
                return new IconDTO(foundIcon.get());
            }else {
                IconDTO iconDTO = new IconDTO(id, generatedFileName);
                Icon newIcon = new Icon(iconDTO);
                iconRepository.save(newIcon);
                return iconDTO;
            }
        }catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            //list all files in storageFolder
            //How to fix this ?
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to load stored files", e);
        }
    }

    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }
}
