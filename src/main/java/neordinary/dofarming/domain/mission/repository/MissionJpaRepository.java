package neordinary.dofarming.domain.mission.repository;

import neordinary.dofarming.domain.category.Category;
import neordinary.dofarming.domain.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionJpaRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByCategory(Category category);
}
