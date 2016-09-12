package org.nbme.vca.users.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  This Class holds together all the members of a user group from AD
 *
 */

@JsonPropertyOrder({
        "objectType",
        "objectId",
        "deletionTimestamp",
        "description",
        "dirSyncEnabled",
        "displayName",
        "lastDirSyncTime",
        "mail",
        "mailNickname",
        "mailEnabled",
        "onPremisesSecurityIdentifier",
        "provisioningErrors",
        "proxyAddresses",
        "securityEnabled"
})
public class AdUserRole extends DirectoryObject{

    @JsonProperty("objectType")
    private String objectType;

    @JsonProperty("objectId")
    private String objectId;

    @JsonProperty("deletionTimestamp")
    private Object deletionTimestamp;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dirSyncEnabled")
    private Object dirSyncEnabled;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("lastDirSyncTime")
    private Object lastDirSyncTime;

    @JsonProperty("mail")
    private Object mail;

    @JsonProperty("mailNickname")
    private String mailNickname;

    @JsonProperty("mailEnabled")
    private Boolean mailEnabled;

    @JsonProperty("onPremisesSecurityIdentifier")
    private Object onPremisesSecurityIdentifier;

    @JsonProperty("provisioningErrors")
    private List<Object> provisioningErrors = new ArrayList<Object>();

    @JsonProperty("proxyAddresses")
    private List<Object> proxyAddresses = new ArrayList<Object>();

    @JsonProperty("securityEnabled")
    private Boolean securityEnabled;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The objectType
     */
    @JsonProperty("objectType")
    public String getObjectType() {
        return objectType;
    }

    /**
     *
     * @param objectType
     * The objectType
     */
    @JsonProperty("objectType")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     *
     * @return
     * The objectId
     */
    @JsonProperty("objectId")
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     * The objectId
     */
    @JsonProperty("objectId")
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     *
     * @return
     * The deletionTimestamp
     */
    @JsonProperty("deletionTimestamp")
    public Object getDeletionTimestamp() {
        return deletionTimestamp;
    }

    /**
     *
     * @param deletionTimestamp
     * The deletionTimestamp
     */
    @JsonProperty("deletionTimestamp")
    public void setDeletionTimestamp(Object deletionTimestamp) {
        this.deletionTimestamp = deletionTimestamp;
    }

    /**
     *
     * @return
     * The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The dirSyncEnabled
     */
    @JsonProperty("dirSyncEnabled")
    public Object getDirSyncEnabled() {
        return dirSyncEnabled;
    }

    /**
     *
     * @param dirSyncEnabled
     * The dirSyncEnabled
     */
    @JsonProperty("dirSyncEnabled")
    public void setDirSyncEnabled(Object dirSyncEnabled) {
        this.dirSyncEnabled = dirSyncEnabled;
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
     * The lastDirSyncTime
     */
    @JsonProperty("lastDirSyncTime")
    public Object getLastDirSyncTime() {
        return lastDirSyncTime;
    }

    /**
     *
     * @param lastDirSyncTime
     * The lastDirSyncTime
     */
    @JsonProperty("lastDirSyncTime")
    public void setLastDirSyncTime(Object lastDirSyncTime) {
        this.lastDirSyncTime = lastDirSyncTime;
    }

    /**
     *
     * @return
     * The mail
     */
    @JsonProperty("mail")
    public Object getMail() {
        return mail;
    }

    /**
     *
     * @param mail
     * The mail
     */
    @JsonProperty("mail")
    public void setMail(Object mail) {
        this.mail = mail;
    }

    /**
     *
     * @return
     * The mailNickname
     */
    @JsonProperty("mailNickname")
    public String getMailNickname() {
        return mailNickname;
    }

    /**
     *
     * @param mailNickname
     * The mailNickname
     */
    @JsonProperty("mailNickname")
    public void setMailNickname(String mailNickname) {
        this.mailNickname = mailNickname;
    }

    /**
     *
     * @return
     * The mailEnabled
     */
    @JsonProperty("mailEnabled")
    public Boolean getMailEnabled() {
        return mailEnabled;
    }

    /**
     *
     * @param mailEnabled
     * The mailEnabled
     */
    @JsonProperty("mailEnabled")
    public void setMailEnabled(Boolean mailEnabled) {
        this.mailEnabled = mailEnabled;
    }

    /**
     *
     * @return
     * The onPremisesSecurityIdentifier
     */
    @JsonProperty("onPremisesSecurityIdentifier")
    public Object getOnPremisesSecurityIdentifier() {
        return onPremisesSecurityIdentifier;
    }

    /**
     *
     * @param onPremisesSecurityIdentifier
     * The onPremisesSecurityIdentifier
     */
    @JsonProperty("onPremisesSecurityIdentifier")
    public void setOnPremisesSecurityIdentifier(Object onPremisesSecurityIdentifier) {
        this.onPremisesSecurityIdentifier = onPremisesSecurityIdentifier;
    }

    /**
     *
     * @return
     * The provisioningErrors
     */
    @JsonProperty("provisioningErrors")
    public List<Object> getProvisioningErrors() {
        return provisioningErrors;
    }

    /**
     *
     * @param provisioningErrors
     * The provisioningErrors
     */
    @JsonProperty("provisioningErrors")
    public void setProvisioningErrors(List<Object> provisioningErrors) {
        this.provisioningErrors = provisioningErrors;
    }

    /**
     *
     * @return
     * The proxyAddresses
     */
    @JsonProperty("proxyAddresses")
    public List<Object> getProxyAddresses() {
        return proxyAddresses;
    }

    /**
     *
     * @param proxyAddresses
     * The proxyAddresses
     */
    @JsonProperty("proxyAddresses")
    public void setProxyAddresses(List<Object> proxyAddresses) {
        this.proxyAddresses = proxyAddresses;
    }

    /**
     *
     * @return
     * The securityEnabled
     */
    @JsonProperty("securityEnabled")
    public Boolean getSecurityEnabled() {
        return securityEnabled;
    }

    /**
     *
     * @param securityEnabled
     * The securityEnabled
     */
    @JsonProperty("securityEnabled")
    public void setSecurityEnabled(Boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }


}

