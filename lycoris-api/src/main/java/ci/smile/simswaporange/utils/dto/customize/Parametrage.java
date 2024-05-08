package ci.smile.simswaporange.utils.dto.customize;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parametrage {
	 private String key;  // Ajoutez cette ligne

	    private List<Jour> hours;

	    public Parametrage() {
	    }

	    public Parametrage(String key, List<Jour> hours) {
	        this.key = key;
	        this.hours = hours;
	    }

	    public String getKey() {
	        return key;
	    }

	    public List<Jour> getHours() {
	        return hours;
	    }
}
