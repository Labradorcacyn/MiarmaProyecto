package com.miarma.cynthia.users.services;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.service.FileService;
import com.miarma.cynthia.service.base.BaseService;
import com.miarma.cynthia.users.dto.users.CreateUserDto;
import com.miarma.cynthia.users.model.UserEntity;
import com.miarma.cynthia.users.model.UserRole;
import com.miarma.cynthia.users.repos.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }
    public UserEntity findByFullName(String fullName) throws UsernameNotFoundException {
        return repository.findByFullName(fullName)
                .orElseThrow(()-> new UsernameNotFoundException(fullName + " no encontrado"));
    }

    public UserEntity registrarUsuario(CreateUserDto createUserDto, MultipartFile file) throws Exception /*throws IOException*/ {

        String resize = fileService.storeResized(file, 128);

        String uriResized = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(resize)
                .toUriString();

       /* byte[] byteFile = Files.readAllBytes(Paths.get(filename));
        BufferedImage image =  ImageIO.read(
                new ByteArrayInputStream(byteFile)
        );

        BufferedImage scale = Scalr.resize(image,128);

        OutputStream out = Files.newOutputStream(Paths.get("foto-thumb.jpeg"));

        ImageIO.write(scale, "jpg", out);*/

        if(createUserDto.getPassword().contentEquals((createUserDto.getPassword2()))){
            UserEntity usuario = UserEntity.builder()
                    .birthday(createUserDto.getBirthday())
                    .privacy(createUserDto.isPrivacy())
                    .avatar(uriResized)
                    .fullName(createUserDto.getFullName())
                    .email(createUserDto.getEmail())
                    .password(passwordEncoder.encode(createUserDto.getPassword()))
                    .role(UserRole.USER)
                    .build();
            try{
                return save(usuario);
            }catch (DataIntegrityViolationException ex){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ese usuario ya existe");
            }
        }else{
            return null;
        }
    }

    public List<Follow> GetMyFollowers(UUID id){
        return repository.findAllFollower(id);
    }

    public Optional<UserEntity> findByEmail (String email){
        return this.repository.findFirstByEmail(email);
    }
}