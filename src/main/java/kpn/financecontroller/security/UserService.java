package kpn.financecontroller.security;

import kpn.financecontroller.data.domain.User;
import kpn.financecontroller.data.entity.UserEntity;
import kpn.financecontroller.data.repo.UserRepo;
import kpn.financecontroller.i18n.I18nService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final I18nService i18nService;

    public UserService(UserRepo userRepo, I18nService i18nService) {
        this.userRepo = userRepo;
        this.i18nService = i18nService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserEntity> userEntities = userRepo.findByUsername(username);
        if (userEntities.size() != 0){
            UserEntity userEntity = userEntities.get(0);
            return User.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .role(userEntity.getRole())
                    .build();
        }

        String message = i18nService.getTranslation("exception.user.notFound", username);
        throw new UsernameNotFoundException(message);
    }
}
