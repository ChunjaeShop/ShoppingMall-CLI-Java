package item;

public class Item { // vo
    private String categoryId;
    private  int itemId;
    private String itemName;
    private String size;
    private int purchaseCnt;
    private int remain;
    private int price;
    private String content;

    public Item() {
    }

    public Item(String categoryId, int itemId, String itemName, String size, int purchaseCnt, int remain, int price, String content) {
        this.categoryId = categoryId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.size = size;
        this.purchaseCnt = purchaseCnt;
        this.remain = remain;
        this.price = price;
        this.content = content;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPurchaseCnt() {
        return purchaseCnt;
    }

    public void setPurchaseCnt(int purchaseCnt) {
        this.purchaseCnt = purchaseCnt;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
