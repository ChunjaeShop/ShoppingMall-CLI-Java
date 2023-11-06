package user;

import java.sql.Timestamp;

public class ItemOrderDTO {
    private int orderId;
    private String userId;
    private int itemId;
    private Timestamp orderDate;
    private String status;

    public ItemOrderDTO() {
    }

    public ItemOrderDTO(int orderId, String userId, int itemId, Timestamp orderDate, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.itemId = itemId;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}