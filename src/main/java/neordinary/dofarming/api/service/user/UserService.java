package neordinary.dofarming.api.service.user;

import neordinary.dofarming.api.controller.user.dto.response.GetUserRes;
import neordinary.dofarming.api.controller.user.dto.response.PatchUserRes;
import neordinary.dofarming.domain.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    PatchUserRes patchProfile(User user, MultipartFile profile, String nickname);

    GetUserRes getMyPage(User user);
}
