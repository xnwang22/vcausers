package org.nbme.vca.users.security.model;

        import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginKey {

    @SerializedName("keys")
    @Expose
    private List<Key> keys = new ArrayList<Key>();

    /**
     *
     * @return
     * The keys
     */
    public List<Key> getKeys() {
        return keys;
    }

    /**
     *
     * @param keys
     * The keys
     */
    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }



}
