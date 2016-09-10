package task.shopify.www.shopifytask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * The POJO containing details related to a product
 * @author Devesh Shetty
 */
public class Product {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("handle")
    @Expose
    private String handle;

    @SerializedName("body_html")
    @Expose
    private String bodyHtml;

    @SerializedName("published_at")
    @Expose
    private String publishedAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("vendor")
    @Expose
    private String vendor;

    @SerializedName("product_type")
    @Expose
    private String productType;

    @SerializedName("tags")
    @Expose
    private List<String> tags = new ArrayList<String>();

    @SerializedName("variants")
    @Expose
    private List<Variant> variants = new ArrayList<Variant>();

    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();

    @SerializedName("options")
    @Expose
    private List<Option> options = new ArrayList<Option>();

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
     * The handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     *
     * @param handle
     * The handle
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     *
     * @return
     * The bodyHtml
     */
    public String getBodyHtml() {
        return bodyHtml;
    }

    /**
     *
     * @param bodyHtml
     * The body_html
     */
    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    /**
     *
     * @return
     * The publishedAt
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     *
     * @param publishedAt
     * The published_at
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
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

    /**
     *
     * @return
     * The vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     *
     * @param vendor
     * The vendor
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     *
     * @return
     * The productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     *
     * @param productType
     * The product_type
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     *
     * @return
     * The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     * The variants
     */
    public List<Variant> getVariants() {
        return variants;
    }

    /**
     *
     * @param variants
     * The variants
     */
    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    /**
     *
     * @return
     * The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     *
     * @return
     * The options
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     *
     * @param options
     * The options
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", handle='" + handle + '\'' +
                ", bodyHtml='" + bodyHtml + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", vendor='" + vendor + '\'' +
                ", productType='" + productType + '\'' +
                ", tags=" + tags +
                ", variants=" + variants +
                ", images=" + images +
                ", options=" + options +
                '}';
    }
}