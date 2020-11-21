package api.Models;

public class ChatModel {
    private String uname;
    private String cname;
    private String content;
    private String date;
    private String uavatar;
    private String cavatar;

    public ChatModel(String uname, String cname, String content, String date, String uavatar, String cavatar) {
        super();
        this.uname = uname;
        this.cname = cname;
        this.content = content;
        this.date = date;
        this.uavatar = uavatar;
        this.cavatar = cavatar;
    }

    public ChatModel() {
        super();
    }


    public String getUname() {
        return this.uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getCname() {
        return this.cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getContent() {
        return this.content;
    }
    public String getDate() {
        return this.date;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setUavatar(String uavatar) {
        this.uavatar = uavatar;
    }
    public String getUavatar() {
        return this.uavatar;
    }

    public void setCavatar(String cavatar) {
        this.cavatar = cavatar;
    }
    public String getCavatar() {
        return this.cavatar;
    }

    @Override
    public String toString() {
        return "MsgObj [uname=" + uname + ", cname=" + cname + ", content=" + content + ", date=" + date + ", uavatar=" + uavatar +", cavatar=" + cavatar +"]";
    }
}
