package kpn.financecontroller.data.service;

import kpn.financecontroller.data.domain.User;
import kpn.financecontroller.data.entities.UserEntity;
import kpn.financecontroller.data.repo.UserRepo;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final StandardPBEStringEncryptor encryptor;

    @Autowired
    public UserService(UserRepo userRepo, StandardPBEStringEncryptor encryptor) {
        this.userRepo = userRepo;
        this.encryptor = encryptor;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserEntity> userEntities = userRepo.findByUsername(username);
        if (userEntities.size() != 0){
            UserEntity userEntity = userEntities.get(0);
            return User.builder()
                    .username(userEntity.getUsername())
                    .password(encryptor.decrypt(userEntity.getPassword()))
                    .role(userEntity.getRole())
                    .build();
        }
        throw new UsernameNotFoundException(String.format("User with name %s not found", username));
    }
}
