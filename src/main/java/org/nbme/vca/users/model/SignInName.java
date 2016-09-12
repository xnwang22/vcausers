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
        "type",
        "value"
})
public class SignInName {

    @JsonProperty("type")
    private String type;
    @JsonProperty("value")
    private String value;

    /**
     * No args constructor for use in serialization
     *
     */
    public SignInName() {
    }

    /**
     *
     * @param value
     * @param type
     */
    public SignInName(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     *
     * @return
     * The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The value
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}