package org.nbme.vca.users.model;

/**
 * Created by rwang on 8/23/2016.
 */
public class UrlObject {
    public UrlObject(String oid) {
        this.url = "https://graph.windows.net/myorganization/directoryObjects/" + oid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;

}
