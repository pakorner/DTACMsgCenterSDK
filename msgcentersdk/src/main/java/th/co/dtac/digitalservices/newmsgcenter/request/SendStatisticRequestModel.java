package th.co.dtac.digitalservices.newmsgcenter.request;

import com.google.gson.annotations.SerializedName;

public class SendStatisticRequestModel {

    @SerializedName("message_id")
    private String messageId;
    @SerializedName("dtac_id")
    private String dtacId;
    @SerializedName("status")
    private String status;

    public String getMessageId() {
        return messageId;
    }

    public SendStatisticRequestModel setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public String getDtacId() {
        return dtacId;
    }

    public SendStatisticRequestModel setDtacId(String dtacId) {
        this.dtacId = dtacId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SendStatisticRequestModel setStatus(String status) {
        this.status = status;
        return this;
    }
}