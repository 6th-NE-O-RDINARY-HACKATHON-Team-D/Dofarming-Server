package neordinary.dofarming.api.controller.auth.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSocialRes {

    @NotNull
    private Long id;

    private Boolean isFinished;
    private Boolean isEvaluated;
    private String accessToken;
    @NotNull
    private String refreshToken;
}
