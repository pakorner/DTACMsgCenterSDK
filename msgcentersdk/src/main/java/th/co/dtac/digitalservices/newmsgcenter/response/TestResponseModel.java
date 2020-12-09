package th.co.dtac.digitalservices.newmsgcenter.response;

import com.google.gson.annotations.SerializedName;

public class TestResponseModel {

    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("reg_token")
    private String reg_token;
    @SerializedName("_id")
    private String _id;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("device")
    private String device;
    @SerializedName("udid")
    private String udid;
    @SerializedName("dtac_id")
    private String dtac_id;
    @SerializedName("apns")
    private String apns;
    @SerializedName("tel_type")
    private String tel_type;
    @SerializedName("os_version")
    private String os_version;
    @SerializedName("model")
    private String model;
    @SerializedName("app_name")
    private String app_name;
    @SerializedName("app_version_name")
    private String app_version_name;
    @SerializedName("app_version_code")
    private String app_version_code;
    @SerializedName("sdk_version_code")
    private String sdk_version_code;
    @SerializedName("lang")
    private String lang;
    @SerializedName("token")
    private String token;
    @SerializedName("dtac_register_date")
    private Long dtac_register_date;
    @SerializedName("create_date")
    private Long create_date;
    @SerializedName("last_update")
    private Long last_update;

    public int getStatus() {
        return status;
    }

    public TestResponseModel setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public TestResponseModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getReg_token() {
        return reg_token;
    }

    public TestResponseModel setReg_token(String reg_token) {
        this.reg_token = reg_token;
        return this;
    }

    public String get_id() {
        return _id;
    }

    public TestResponseModel set_id(String _id) {
        this._id = _id;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public TestResponseModel setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getDevice() {
        return device;
    }

    public TestResponseModel setDevice(String device) {
        this.device = device;
        return this;
    }

    public String getUdid() {
        return udid;
    }

    public TestResponseModel setUdid(String udid) {
        this.udid = udid;
        return this;
    }

    public String getDtac_id() {
        return dtac_id;
    }

    public TestResponseModel setDtac_id(String dtac_id) {
        this.dtac_id = dtac_id;
        return this;
    }

    public String getApns() {
        return apns;
    }

    public TestResponseModel setApns(String apns) {
        this.apns = apns;
        return this;
    }

    public String getTel_type() {
        return tel_type;
    }

    public TestResponseModel setTel_type(String tel_type) {
        this.tel_type = tel_type;
        return this;
    }

    public String getOs_version() {
        return os_version;
    }

    public TestResponseModel setOs_version(String os_version) {
        this.os_version = os_version;
        return this;
    }

    public String getModel() {
        return model;
    }

    public TestResponseModel setModel(String model) {
        this.model = model;
        return this;
    }

    public String getApp_name() {
        return app_name;
    }

    public TestResponseModel setApp_name(String app_name) {
        this.app_name = app_name;
        return this;
    }

    public String getApp_version_name() {
        return app_version_name;
    }

    public TestResponseModel setApp_version_name(String app_version_name) {
        this.app_version_name = app_version_name;
        return this;
    }

    public String getApp_version_code() {
        return app_version_code;
    }

    public TestResponseModel setApp_version_code(String app_version_code) {
        this.app_version_code = app_version_code;
        return this;
    }

    public String getSdk_version_code() {
        return sdk_version_code;
    }

    public TestResponseModel setSdk_version_code(String sdk_version_code) {
        this.sdk_version_code = sdk_version_code;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public TestResponseModel setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public String getToken() {
        return token;
    }

    public TestResponseModel setToken(String token) {
        this.token = token;
        return this;
    }

    public Long getDtac_register_date() {
        return dtac_register_date;
    }

    public TestResponseModel setDtac_register_date(Long dtac_register_date) {
        this.dtac_register_date = dtac_register_date;
        return this;
    }

    public Long getCreate_date() {
        return create_date;
    }

    public TestResponseModel setCreate_date(Long create_date) {
        this.create_date = create_date;
        return this;
    }

    public Long getLast_update() {
        return last_update;
    }

    public TestResponseModel setLast_update(Long last_update) {
        this.last_update = last_update;
        return this;
    }
}

