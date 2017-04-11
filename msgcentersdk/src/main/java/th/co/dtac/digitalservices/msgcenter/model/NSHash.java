package th.co.dtac.digitalservices.msgcenter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NSHash implements Serializable {

    @SerializedName("nonce")
    @Expose
    private String nonce = "";
    @SerializedName("signature")
    @Expose
    private String signature = "";

    public NSHash(String nonce, String signature) {
        this.nonce = nonce;
        this.signature = signature;
    }

    /**
    *
    * @return
    * The nonce
    */
    public String getNonce() {
    return nonce;
    }

    /**
    *
    * @param nonce
    * The nonce
    */
    public void setNonce(String nonce) {
    this.nonce = nonce;
    }

    /**
    *
    * @return
    * The signature
    */
    public String getSignature() {
    return signature;
    }

    /**
    *
    * @param signature
    * The signature
    */
    public void setSignature(String signature) {
    this.signature = signature;
    }

}