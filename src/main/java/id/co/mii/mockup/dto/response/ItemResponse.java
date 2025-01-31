package id.co.mii.mockup.dto.response;

import id.co.mii.mockup.dto.request.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponse extends BaseResponse{
    private Item itemData;
}
