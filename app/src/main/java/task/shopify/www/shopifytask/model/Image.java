package task.shopify.www.shopifytask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * The POJO containing details related to Image of a product
 * @author Devesh Shetty
 */
public class Image {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("position")
    @Expose
    private long position;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("product_id")
    @Expose
    private long productId;

    @SerializedName("variant_ids")
    @Expose
    private List<Object> variantIds = new ArrayList<Object>();

    @SerializedName("src")
    @Expose
    private String src;

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
     * The variantIds
     */
    public List<Object> getVariantIds() {
        return variantIds;
    }

    /**
     *
     * @param variantIds
     * The variant_ids
     */
    public void setVariantIds(List<Object> variantIds) {
        this.variantIds = variantIds;
    }

    /**
     *
     * @return
     * The src
     */
    public String getSrc() {
        return src;
    }

    /**
     *
     * @param src
     * The src
     */
    public void setSrc(String src) {
        this.src = src;
    }


    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", position=" + position +
                ", updatedAt='" + updatedAt + '\'' +
                ", productId=" + productId +
                ", variantIds=" + variantIds +
                ", src='" + src + '\'' +
                '}';
    }
}