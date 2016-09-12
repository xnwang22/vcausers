package org.nbme.vca.users.model;

/**
 * Created by rwang on 8/17/2016.
 */



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "password",
        "forceChangePasswordNextLogin"
})
public class PasswordProfile {

    @JsonProperty("password")
    private String password;
    @JsonProperty("forceChangePasswordNextLogin")
    private boolean forceChangePasswordNextLogin;

    /**
     * No args constructor for use in serialization
     *
     */
    public PasswordProfile() {
    }

    /**
     *
     * @param forceChangePasswordNextLogin
     * @param password
     */
    public PasswordProfile(String password, boolean forceChangePasswordNextLogin) {
        this.password = password;
        this.forceChangePasswordNextLogin = forceChangePasswordNextLogin;
    }

    /**
     *
     * @return
     * The password
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     * The forceChangePasswordNextLogin
     */
    @JsonProperty("forceChangePasswordNextLogin")
    public boolean isForceChangePasswordNextLogin() {
        return forceChangePasswordNextLogin;
    }

    /**
     *
     * @param forceChangePasswordNextLogin
     * The forceChangePasswordNextLogin
     */
    @JsonProperty("forceChangePasswordNextLogin")
    public void setForceChangePasswordNextLogin(boolean forceChangePasswordNextLogin) {
        this.forceChangePasswordNextLogin = forceChangePasswordNextLogin;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
