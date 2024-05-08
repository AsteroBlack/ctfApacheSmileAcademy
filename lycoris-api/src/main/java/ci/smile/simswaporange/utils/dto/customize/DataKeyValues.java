package ci.smile.simswaporange.utils.dto.customize;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataKeyValues {
    private String key;
    private List<DataKey> values;
}
