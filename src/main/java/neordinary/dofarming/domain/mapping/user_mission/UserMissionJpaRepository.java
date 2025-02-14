package neordinary.dofarming.domain.mapping.user_mission;

import neordinary.dofarming.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserMissionJpaRepository extends JpaRepository<UserMission, Long> {
    List<UserMission> findByUserAndCreatedAtBetween(User currentUser, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
