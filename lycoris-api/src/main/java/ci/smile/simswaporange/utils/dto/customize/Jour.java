package ci.smile.simswaporange.utils.dto.customize;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Jour {
	private String key;
    private List<PlageHoraire> plages;

    public Jour() {
    }

    public Jour(String key, List<PlageHoraire> plages) {
        this.key = key;
        this.plages = plages;
    }

    public String getKey() {
        return key;
    }

    public List<PlageHoraire> getPlages() {
        return plages;
    }
}
