package neordinary.dofarming.domain.user.repository;


import neordinary.dofarming.common.BaseEntity;
import neordinary.dofarming.common.BaseEntity.State;
import neordinary.dofarming.domain.user.User;
import neordinary.dofarming.domain.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndState(String username, State state);
    boolean existsByUsernameAndSocialTypeAndState(String username, SocialType socialType, State state);

}
