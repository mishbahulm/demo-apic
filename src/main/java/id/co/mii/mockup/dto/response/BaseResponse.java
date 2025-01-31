package id.co.mii.mockup.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseResponse {

    private String responseCode;
    private String responseMessage;
    @JsonIgnore
    private transient HttpStatus responseStatus;

}
