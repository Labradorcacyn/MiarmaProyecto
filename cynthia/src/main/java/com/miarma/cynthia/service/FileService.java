package com.miarma.cynthia.service;

import com.miarma.cynthia.config.StorageProperties;
import com.miarma.cynthia.exception.FileNotFoundException;
import com.miarma.cynthia.exception.StorageException;
import com.miarma.cynthia.repository.FileRepository;
import com.miarma.cynthia.utils.MediaTypeUrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;


@Service
public class FileService implements FileRepository {

    private final Path rootLocation;

    @Autowired
    public FileService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @PostConstruct
    @Override
    public void init() {
        try{
            Files.createDirectories(rootLocation);
        }catch (IOException ex){
            throw new StorageException("No se pudo inicializar la ubicación de almacenamiento", ex);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("El fichero está vacío");

            newFilename = filename;
            while(Files.exists(rootLocation.resolve(newFilename))) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("."+extension,"");

                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                newFilename = name + "_" + suffix + "." + extension;

            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new StorageException("Error al almacenar el fichero: " + newFilename, ex);
        }
        return newFilename;
    }


    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }catch (IOException ex){
            throw new StorageException("Error al leer los ficheros", ex);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename);
            MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new FileNotFoundException("No se puede leer el fichero: " + filename);
            }
        }catch (MalformedURLException ex){
            throw new FileNotFoundException("No se puede leer el fichero: " + filename, ex);
        }
    }

    @Override
    public void deleteFile(String filename) {

    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
