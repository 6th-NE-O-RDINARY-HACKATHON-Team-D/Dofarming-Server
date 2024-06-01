package neordinary.dofarming.domain.mission.repository;

import neordinary.dofarming.domain.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionJpaRepository extends JpaRepository<Mission, Long> {
}
