package nl.sweatdaddy.fileUpload.service;

import nl.sweatdaddy.common.exception.NotFoundException;
import nl.sweatdaddy.fileUpload.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {

    private final Path fileStoragePath;
    private final FileUploadRepository fileUploadRepository;

    public FileService(@Value("${app.upload.dir}") String fileStorageLocation, FileUploadRepository fileUploadRepository) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileUploadRepository = fileUploadRepository;

        Files.createDirectories(fileStoragePath);
    }

    public String storeFile(MultipartFile file) throws IOException {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        Path filePath = fileStoragePath.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        fileUploadRepository.save(new nl.sweatdaddy.fileUpload.entity.File(uniqueFileName));
        return uniqueFileName;
    }

    public Resource downloadFile(String fileName) {
        Path path = fileStoragePath.resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new NotFoundException("Er is een probleem met het uitlezen van het bestand");
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new NotFoundException("Profielfoto niet gevonden");
        }
    }

}
