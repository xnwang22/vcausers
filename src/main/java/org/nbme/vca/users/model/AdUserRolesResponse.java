package org.nbme.vca.users.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by rwang on 8/23/2016.
 */
public class AdUserRolesResponse
{
    @JsonProperty("odata.metadata")
    private String odataMetadata;
    @JsonProperty("value")
    private List<AdUserRole> adUserRoles;


    public List<AdUserRole> getAdUserRoles() {
        return adUserRoles;
    }

    public void setAdUserRoles(List<AdUserRole> adUserRoles) {
        this.adUserRoles = adUserRoles;
    }

    public String getOdataMetadata() {
        return odataMetadata;
    }

    public void setOdataMetadata(String odataMetadata) {
        this.odataMetadata = odataMetadata;
    }
}
