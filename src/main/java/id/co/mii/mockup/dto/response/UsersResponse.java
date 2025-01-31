package id.co.mii.mockup.dto.response;

import java.util.Collection;

import id.co.mii.mockup.dto.request.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersResponse extends BaseResponse{
    private Collection<User> usersData;
}
