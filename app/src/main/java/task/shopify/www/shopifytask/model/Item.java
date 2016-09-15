package task.shopify.www.shopifytask.model;

import java.util.Map;

/**
 * @author Devesh Shetty
 */
public class Item {

    private String productTitle;
    private String productImageSrc;
    private Map<String, String> variantMap;


    public Item(String productTitle, String productImageSrc, Map<String, String> variantMap) {
        this.productTitle = productTitle;
        this.productImageSrc = productImageSrc;
        this.variantMap = variantMap;
    }

    public Map<String, String> getVariantMap() {
        return variantMap;
    }

    public void setVariantMap(Map<String, String> variantMap) {
        this.variantMap = variantMap;
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


