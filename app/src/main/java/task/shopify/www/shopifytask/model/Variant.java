package task.shopify.www.shopifytask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Devesh Shetty
 */
public class Variant {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("option1")
    @Expose
    private String option1;

    @SerializedName("option2")
    @Expose
    private Object option2;

    @SerializedName("option3")
    @Expose
    private Object option3;

    @SerializedName("sku")
    @Expose
    private String sku;

    @SerializedName("requires_shipping")
    @Expose
    private boolean requiresShipping;

    @SerializedName("taxable")
    @Expose
    private boolean taxable;

    @SerializedName("featured_image")
    @Expose
    private Object featuredImage;

    @SerializedName("available")
    @Expose
    private boolean available;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("grams")
    @Expose
    private long grams;

    @SerializedName("compare_at_price")
    @Expose
    private Object compareAtPrice;

    @SerializedName("position")
    @Expose
    private long position;

    @SerializedName("product_id")
    @Expose
    private long productId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The option1
     */
    public String getOption1() {
        return option1;
    }

    /**
     *
     * @param option1
     * The option1
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     *
     * @return
     * The option2
     */
    public Object getOption2() {
        return option2;
    }

    /**
     *
     * @param option2
     * The option2
     */
    public void setOption2(Object option2) {
        this.option2 = option2;
    }

    /**
     *
     * @return
     * The option3
     */
    public Object getOption3() {
        return option3;
    }

    /**
     *
     * @param option3
     * The option3
     */
    public void setOption3(Object option3) {
        this.option3 = option3;
    }

    /**
     *
     * @return
     * The sku
     */
    public String getSku() {
        return sku;
    }

    /**
     *
     * @param sku
     * The sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     *
     * @return
     * The requiresShipping
     */
    public boolean isRequiresShipping() {
        return requiresShipping;
    }

    /**
     *
     * @param requiresShipping
     * The requires_shipping
     */
    public void setRequiresShipping(boolean requiresShipping) {
        this.requiresShipping = requiresShipping;
    }

    /**
     *
     * @return
     * The taxable
     */
    public boolean isTaxable() {
        return taxable;
    }

    /**
     *
     * @param taxable
     * The taxable
     */
    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    /**
     *
     * @return
     * The featuredImage
     */
    public Object getFeaturedImage() {
        return featuredImage;
    }

    /**
     *
     * @param featuredImage
     * The featured_image
     */
    public void setFeaturedImage(Object featuredImage) {
        this.featuredImage = featuredImage;
    }

    /**
     *
     * @return
     * The available
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     *
     * @param available
     * The available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The grams
     */
    public long getGrams() {
        return grams;
    }

    /**
     *
     * @param grams
     * The grams
     */
    public void setGrams(long grams) {
        this.grams = grams;
    }

    /**
     *
     * @return
     * The compareAtPrice
     */
    public Object getCompareAtPrice() {
        return compareAtPrice;
    }

    /**
     *
     * @param compareAtPrice
     * The compare_at_price
     */
    public void setCompareAtPrice(Object compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

    /**
     *
     * @return
     * The position
     */
    public long getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(long position) {
        this.position = position;
    }

    /**
     *
     * @return
     * The productId
     */
    public long getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     * The product_id
     */
    public void setProductId(long productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2=" + option2 +
                ", option3=" + option3 +
                ", sku='" + sku + '\'' +
                ", requiresShipping=" + requiresShipping +
                ", taxable=" + taxable +
                ", featuredImage=" + featuredImage +
                ", available=" + available +
                ", price='" + price + '\'' +
                ", grams=" + grams +
                ", compareAtPrice=" + compareAtPrice +
                ", position=" + position +
                ", productId=" + productId +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}