package com.miarma.cynthia.users.services;

import com.miarma.cynthia.services.base.BaseService;
import com.miarma.cynthia.users.dto.CreateUserDto;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }

    public UserEntity registrarUsuario(CreateUserDto createUserDto){
        if(createUserDto.getPassword().contentEquals((createUserDto.getPassword2()))){
            UserEntity usuario = UserEntity.builder()
                    .fullName(createUserDto.getFullname())
                    .avatar(createUserDto.getAvatar())
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

    public Optional<UserEntity> findByEmail (String email){
        return this.repository.findFirstByEmail(email);
    }
}