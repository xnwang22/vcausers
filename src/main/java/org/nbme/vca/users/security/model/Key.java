package org.nbme.vca.users.security.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rwang on 8/30/2016.
 */
public class Key {

    @SerializedName("kty")
    @Expose
    private String kty;
    @SerializedName("use")
    @Expose
    private String use;
    @SerializedName("kid")
    @Expose
    private String kid;
    @SerializedName("x5t")
    @Expose
    private String x5t;
    @SerializedName("n")
    @Expose
    private String n;
    @SerializedName("e")
    @Expose
    private String e;
    @SerializedName("x5c")
    @Expose
    private List<String> x5c = new ArrayList<String>();

    /**
     * @return The kty
     */
    public String getKty() {
        return kty;
    }

    /**
     * @param kty The kty
     */
    public void setKty(String kty) {
        this.kty = kty;
    }

    /**
     * @return The use
     */
    public String getUse() {
        return use;
    }

    /**
     * @param use The use
     */
    public void setUse(String use) {
        this.use = use;
    }

    /**
     * @return The kid
     */
    public String getKid() {
        return kid;
    }

    /**
     * @param kid The kid
     */
    public void setKid(String kid) {
        this.kid = kid;
    }

    /**
     * @return The x5t
     */
    public String getX5t() {
        return x5t;
    }

    /**
     * @param x5t The x5t
     */
    public void setX5t(String x5t) {
        this.x5t = x5t;
    }

    /**
     * @return The n
     */
    public String getN() {
        return n;
    }

    /**
     * @param n The n
     */
    public void setN(String n) {
        this.n = n;
    }

    /**
     * @return The e
     */
    public String getE() {
        return e;
    }

    /**
     * @param e The e
     */
    public void setE(String e) {
        this.e = e;
    }

    /**
     * @return The x5c
     */
    public List<String> getX5c() {
        return x5c;
    }

    /**
     * @param x5c The x5c
     */
    public void setX5c(List<String> x5c) {
        this.x5c = x5c;
    }
}