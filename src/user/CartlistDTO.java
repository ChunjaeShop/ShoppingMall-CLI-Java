package user;

//장바구니 리스트 DTO
public class CartlistDTO {
    private int cartId;
    private String userId;
    private String itemName;
    private int price;
    private String phone;

    public CartlistDTO(){

    }

    public CartlistDTO(int cartId, String userId, String itemName, int price, String phone) {
        this.cartId = cartId;
        this.userId = userId;
        this.itemName = itemName;
        this.price = price;
        this.phone = phone;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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
        return "CartlistDTO{" +
                "cartId=" + cartId +
                ", userId='" + userId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                ", phone='" + phone + '\'' +
                '}';
    }
}