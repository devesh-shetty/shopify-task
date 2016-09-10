package task.shopify.www.shopifytask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Devesh Shetty
 */
public class Option {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("position")
    @Expose
    private long position;

    @SerializedName("values")
    @Expose
    private List<String> values = new ArrayList<String>();


    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
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
     * The values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     *
     * @param values
     * The values
     */
    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", values=" + values +
                '}';
    }
}