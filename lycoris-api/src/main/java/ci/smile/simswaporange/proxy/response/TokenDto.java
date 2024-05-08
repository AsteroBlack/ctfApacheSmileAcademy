package ci.smile.simswaporange.proxy.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
@Data
@NoArgsConstructor
public class TokenDto {
    private String access_token;
    private String scope;
    private String token_type;
    private long expires_in;

    // Ajoutez un constructeur ou une méthode de fabrique avec l'annotation @JsonCreator
    @JsonCreator
    public TokenDto(@JsonProperty("access_token") String access_token,
                    @JsonProperty("scope") String scope,
                    @JsonProperty("token_type") String token_type,
                    @JsonProperty("expires_in") long expires_in) {
        this.access_token = access_token;
        this.scope = scope;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    // Ajoutez les getters et les setters ici
}

