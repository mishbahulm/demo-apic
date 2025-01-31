package id.co.mii.mockup.dto.response;

import id.co.mii.mockup.dto.request.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseResponse{
    private User userData;
}
