package in.bitcode.productassignment;

import java.io.Serializable;

public class Product implements Serializable {

    public static final String KEY_PRODUCT = "product";
    private int mId;
    private String mName;
    private String mDescription;
    private int mPrice;
    private String mImageUrl;

    public Product(int id, String name, String description, int price, String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
        this.mPrice = price;
        this.mImageUrl = imageUrl;
    }

    public void setId( int id ) {
        this.mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setName( String name ) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setDescription(String description ) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setPrice( int price ) {
        mPrice = price;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setImageUrl(String imageUrl ) {
        mImageUrl = imageUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
