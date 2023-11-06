package ex01.cart;

//결제 테이블에 필요한 구매리스트DTO
public class Purchase_listDTO {
    private int purchaseNo;
    private String userId;
    private String itemName;
    private int price;
    private String orderDate;
    private String phone;


    public Purchase_listDTO(int purchaseNo, String userId, String itemName, int price, String purchaseDate, String phone) {
        this.purchaseNo = purchaseNo;
        this.userId = userId;
        this.itemName = itemName;
        this.price = price;
        this.orderDate = orderDate;
        this.phone = phone;
    }

    public Purchase_listDTO() {

    }

    public int getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(int purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getPurchaseDate() {
        return orderDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.orderDate = purchaseDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Purchase_listDTO{" +
                "purchaseNo=" + purchaseNo +
                ", userId='" + userId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", orderDate='" + orderDate + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
