package task.shopify.www.shopifytask.model;

/**
 * @author Devesh Shetty
 */
public class Item {

    private String productTitle;
    private String productImageSrc;

    public Item(String productTitle, String productImageSrc) {
        this.productTitle = productTitle;
        this.productImageSrc = productImageSrc;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImageSrc() {
        return productImageSrc;
    }

    public void setProductImageSrc(String productImageSrc) {
        this.productImageSrc = productImageSrc;
    }
}


