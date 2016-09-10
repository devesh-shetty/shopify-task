package task.shopify.www.shopifytask.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The POJO containing details related to Image of a product
 * @author Devesh Shetty
 */
public class Image {

    private long id;
    private String createdAt;
    private long position;
    private String updatedAt;
    private long productId;
    private List<Object> variantIds = new ArrayList<Object>();
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