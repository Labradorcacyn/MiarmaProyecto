package com.miarma.cynthia.service;

import com.miarma.cynthia.config.StorageProperties;
import com.miarma.cynthia.exception.FileNotFoundException;
import com.miarma.cynthia.exception.StorageException;
import com.miarma.cynthia.repository.FileRepository;
import com.miarma.cynthia.utils.MediaTypeUrlResource;
import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.ResizeResolution;
import io.github.techgnious.dto.VideoFormats;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public String storeResized(MultipartFile file,int width) throws Exception {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(filename);
        String name = filename.replace("."+extension,"");

        BufferedImage original = ImageIO.read(file.getInputStream());

        /*IVCompressor compressor = new IVCompressor();

        IVSize customRes=new IVSize();
        customRes.setWidth(width);

        byte[] bytes = compressor.resizeImageWithCustomRes(file.getBytes(), ImageFormats.JPEG, customRes);

        BufferedImage scaled = ImageIO.read(new ByteArrayInputStream(bytes));*/

        BufferedImage scaled = Scalr.resize(original, width);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ImageIO.write(scaled, "jpg", out);

        InputStream newImage = new ByteArrayInputStream(out.toByteArray());

        try {

            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            while(Files.exists(rootLocation.resolve(filename))) {
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                filename = name + "_" + suffix + "." + extension;
            }

            try (InputStream inputStream = newImage) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + filename, ex);
        }
        return filename;
    }

    @Override
    public String storeVideoResized(MultipartFile file,int width) throws Exception {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(filename);
        String name = filename.replace("."+extension,"");

        BufferedImage original = ImageIO.read(file.getInputStream());

        ByteArrayOutputStream outIm = new ByteArrayOutputStream();

        ImageIO.write(original , "avi", outIm);

        byte[] bytesImage = outIm.toByteArray();

        IVCompressor compressor = new IVCompressor();

        IVSize customRes=new IVSize();
        customRes.setWidth(width);

        BufferedImage scaled = ImageIO.read(new ByteArrayInputStream(
                compressor.convertAndResizeVideo(bytesImage, VideoFormats.MP4,VideoFormats.MOV, ResizeResolution.R720P)));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ImageIO.write(scaled, "jpg", out);

        InputStream newImage = new ByteArrayInputStream(out.toByteArray());

        try {

            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            while(Files.exists(rootLocation.resolve(filename))) {
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                filename = name + "_" + suffix + "." + extension;
            }

            try (InputStream inputStream = newImage) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + filename, ex);
        }
        return filename;
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
        try {
            Boolean delete = Files.deleteIfExists(this.rootLocation.resolve(filename));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}