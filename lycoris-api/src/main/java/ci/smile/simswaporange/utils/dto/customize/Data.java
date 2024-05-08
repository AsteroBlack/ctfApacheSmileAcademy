package ci.smile.simswaporange.utils.dto.customize;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Data {
	   // Constructeur par défaut
	private List<Parametrage> datas;

    public Data() {
    }

    public Data(@JsonProperty("datas") List<Parametrage> datas) {
        this.datas = datas;
    }

    public List<Parametrage> getDatas() {
        return datas;
    }
}
