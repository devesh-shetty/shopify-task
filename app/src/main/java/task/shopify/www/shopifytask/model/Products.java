package task.shopify.www.shopifytask.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deveshshetty on 10/09/16.
 */
public class Products {

    private List<Product> products = new ArrayList<Product>();

    /**
     *
     * @return
     * The products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     *
     * @param products
     * The products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Products{" +
                "products=" + products +
                '}';
    }
}


