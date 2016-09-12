package org.nbme.vca.users.model;

/**
 * Created by rwang on 8/17/2016.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "accountEnabled",
        "creationType",
        "displayName",
        "givenName",
        "surName",
        "passwordProfile",
        "signInNames"
})
public class Vca2AdUser {

    @JsonProperty("accountEnabled")
    private boolean accountEnabled;
    @JsonProperty("creationType")
    private String creationType;
    @JsonProperty("displayName")
    private String displayName;
    
    @JsonProperty("givenName")
    private String givenName;
    
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("passwordProfile")
    private PasswordProfile passwordProfile;
    @JsonProperty("signInNames")
    private List<SignInName> signInNames = new ArrayList<SignInName>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Vca2AdUser() {
    }

    /**
     *
     * @param passwordProfile
     * @param signInNames
     * @param accountEnabled
     * @param displayName
     * @param creationType
     */
    public Vca2AdUser(boolean accountEnabled, String creationType, String displayName, PasswordProfile passwordProfile, List<SignInName> signInNames) {
        this.accountEnabled = accountEnabled;
        this.creationType = creationType;
        this.displayName = displayName;
        this.passwordProfile = passwordProfile;
        this.signInNames = signInNames;
    }

    /**
     *
     * @return
     * The accountEnabled
     */
    @JsonProperty("accountEnabled")
    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    /**
     *
     * @param accountEnabled
     * The accountEnabled
     */
    @JsonProperty("accountEnabled")
    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    /**
     *
     * @return
     * The creationType
     */
    @JsonProperty("creationType")
    public String getCreationType() {
        return creationType;
    }

    /**
     *
     * @param creationType
     * The creationType
     */
    @JsonProperty("creationType")
    public void setCreationType(String creationType) {
        this.creationType = creationType;
    }

    /**
     *
     * @return
     * The displayName
     */
    @JsonProperty("givenName")
    public String getGivenName() {
        return givenName;
    }

    /**
     *
     * @param displayName
     * The displayName
     */
    @JsonProperty("givenName")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    


    /**
     *
     * @return
     * The surName
     */
    @JsonProperty("surName")
    public String getSurName() {
        return surName;
    }

    /**
     *
     * @param displayName
     * The surName
     */
    @JsonProperty("surName")
    public void setSurName(String surName) {
        this.surName = surName;
    }
    


    /**
     *
     * @return
     * The displayName
     */
    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     * The displayName
     */
    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return
     * The passwordProfile
     */
    @JsonProperty("passwordProfile")
    public PasswordProfile getPasswordProfile() {
        return passwordProfile;
    }

    /**
     *
     * @param passwordProfile
     * The passwordProfile
     */
    @JsonProperty("passwordProfile")
    public void setPasswordProfile(PasswordProfile passwordProfile) {
        this.passwordProfile = passwordProfile;
    }

    /**
     *
     * @return
     * The signInNames
     */
    @JsonProperty("signInNames")
    public List<SignInName> getSignInNames() {
        return signInNames;
    }

    /**
     *
     * @param signInNames
     * The signInNames
     */
    @JsonProperty("signInNames")
    public void setSignInNames(List<SignInName> signInNames) {
        this.signInNames = signInNames;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}



