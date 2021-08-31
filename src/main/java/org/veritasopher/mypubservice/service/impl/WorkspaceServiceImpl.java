package org.veritasopher.mypubservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.veritasopher.mypubservice.config.GlobalProperties;
import org.veritasopher.mypubservice.exception.WorkspaceException;
import org.veritasopher.mypubservice.exception.WorkspaceFileNotFoundException;
import org.veritasopher.mypubservice.service.WorkspaceService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final Path rootLocation;

    private final String encryptor;

    @Autowired
    public WorkspaceServiceImpl(GlobalProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.encryptor = properties.getEncryptor();
    }

    @Override
    public void store(String filename, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new WorkspaceException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(filename))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new WorkspaceException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new WorkspaceException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new WorkspaceException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new WorkspaceFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new WorkspaceFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new WorkspaceException("Could not initialize workspace", e);
        }

        if (!Files.exists(Path.of(rootLocation + "/" + encryptor))) {
            throw new WorkspaceException("Encryptor not found");
        }
    }
}
