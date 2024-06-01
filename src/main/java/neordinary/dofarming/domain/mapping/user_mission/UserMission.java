package neordinary.dofarming.domain.mapping.user_mission;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;
import neordinary.dofarming.common.BaseEntity;
import neordinary.dofarming.domain.mission.Mission;
import neordinary.dofarming.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Tag(name = "user_misson", description = "유저와 미션 매핑 정보")
@EqualsAndHashCode(callSuper = false)
@Getter
@Builder
@Entity
public class UserMission extends BaseEntity {

    @Id
    @Column(name = "user_mission_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission; // 미션
}
