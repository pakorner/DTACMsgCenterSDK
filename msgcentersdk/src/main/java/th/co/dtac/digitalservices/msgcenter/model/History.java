package th.co.dtac.digitalservices.msgcenter.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

@SerializedName("_id")
@Expose
private String id;
@SerializedName("phone_number")
@Expose
private String phoneNumber;
@SerializedName("title")
@Expose
private Title title;
@SerializedName("message")
@Expose
private Message message;
@SerializedName("category")
@Expose
private String category;
//@SerializedName("data_ref")
//@Expose
//private DataRef dataRef;
@SerializedName("data_ref")
@Expose
private JsonObject dataRef;
@SerializedName("create_date")
@Expose
private long createDate;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getPhoneNumber() {
return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}

public Title getTitle() {
return title;
}

public void setTitle(Title title) {
this.title = title;
}

public Message getMessage() {
return message;
}

public void setMessage(Message message) {
this.message = message;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
}

//public DataRef getDataRef() {
//    return dataRef;
//}
//
//public void setDataRef(DataRef dataRef) {
//    this.dataRef = dataRef;
//}

public JsonObject getDataRef() {
    return dataRef;
}

public void setDataRef(JsonObject dataRef) {
    this.dataRef = dataRef;
}

public long getCreateDate() {
return createDate;
}

public void setCreateDate(long createDate) {
this.createDate = createDate;
}

    public class Title {

        @SerializedName("title_en")
        @Expose
        private String titleEn;
        @SerializedName("title_th")
        @Expose
        private String titleTh;

        public String getTitleEn() {
            return titleEn;
        }

        public void setTitleEn(String titleEn) {
            this.titleEn = titleEn;
        }

        public String getTitleTh() {
            return titleTh;
        }

        public void setTitleTh(String titleTh) {
            this.titleTh = titleTh;
        }

    }


    public class Message {

        @SerializedName("message_en")
        @Expose
        private String messageEn;
        @SerializedName("message_th")
        @Expose
        private String messageTh;

        public String getMessageEn() {
            return messageEn;
        }

        public void setMessageEn(String messageEn) {
            this.messageEn = messageEn;
        }

        public String getMessageTh() {
            return messageTh;
        }

        public void setMessageTh(String messageTh) {
            this.messageTh = messageTh;
        }

    }
}