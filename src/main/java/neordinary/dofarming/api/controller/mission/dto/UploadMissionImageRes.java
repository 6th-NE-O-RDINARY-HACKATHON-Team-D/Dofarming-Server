package neordinary.dofarming.api.controller.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UploadMissionImageRes {
    private String nickname;
    private String imageUrl;
    private LocalDateTime updatedAt;
}
