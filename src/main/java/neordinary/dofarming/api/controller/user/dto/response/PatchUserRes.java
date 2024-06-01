package neordinary.dofarming.api.controller.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PatchUserRes {

        private String nickname;
        private String profileImageUrl;
}
