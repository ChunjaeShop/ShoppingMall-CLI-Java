package ex01.cart;

public class MemberDTO {

    private int uno;
    private String name;
    private String userId;

    private String userPw;

    private String address;

    private String gender;

    private int height;

    private String phone;

    private String birth;

    public MemberDTO() {
    }

    public MemberDTO(int uno, String userId, String userPw, String address, String gender, int height, String phone, String birth, String name) {
        this.uno = uno;
        this.name=name;
        this.userId = userId;
        this.userPw = userPw;
        this.address = address;
        this.gender = gender;
        this.height = height;
        this.phone = phone;
        this.birth = birth;
    }

    public int getUno() {
        return uno;
    }


    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Board{" +
                "uno=" + uno +
                "name=" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", height=" + height +
                ", phone='" + phone + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
