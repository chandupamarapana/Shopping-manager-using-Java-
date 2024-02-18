public abstract class Product implements Comparable<Product> {
    private String productId; //instance variables
    private String productName;

    private int number_of_available_products;
    private double price;
    private String productType;


    //constructor
    public Product(String productId, String productName, int number_of_available_products, double price) {
        this.productId = productId;
        this.productName = productName;
        this.number_of_available_products = number_of_available_products;
        this.price = price;
    }

    //getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumber_of_available_products() {
        return number_of_available_products;
    }

    public double getPrice() {
        return price;
    }

    public String getProductType() {
        return productType;
    }

    //setters


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNumber_of_available_products(int number_of_available_products) {
        this.number_of_available_products = number_of_available_products;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @Override
    public int compareTo(Product otherProduct) {
        return this.getProductId().compareTo(otherProduct.getProductId());
    }

    @Override
    public String toString() {
        return   productId + ", " + productName + ", " + number_of_available_products + ", "+ price ;
    }
}
