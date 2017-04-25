package th.co.dtac.digitalservices.msgcenter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RespHistory implements Serializable {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("history")
    @Expose
    private List<History> history = null;
    @SerializedName("history_total")
    @Expose
    private Integer historyTotal;
    @SerializedName("now")
    @Expose
    private Long now;

    public Integer getStatus() {
    return status;
    }

    public void setStatus(Integer status) {
    this.status = status;
    }

    public String getMessage() {
    return message;
    }

    public void setMessage(String message) {
this.message = message;
}

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public Integer getHistoryTotal() {
        return historyTotal;
    }

    public void setHistoryTotal(Integer historyTotal) {
        this.historyTotal = historyTotal;
    }

    public Long getNow() {
        return now;
    }

    public void setNow(Long now) {
        this.now = now;
    }
}