package ci.smile.simswaporange.utils.dto.customize;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PlageHoraire {

	private List<Integer> values;

	public PlageHoraire() {
	}

	public PlageHoraire(List<Integer> values) {
		this.values = values;
	}

	public List<Integer> getValues() {
		return values;
	}

}
