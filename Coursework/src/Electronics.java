public class Electronics extends Product{
    private String brand ;
    private String warranty;
    public Electronics(String product_ID, String productName,int number_of_available_items, double price, String brand, String warranty ){
        super(product_ID,productName,number_of_available_items,price);  //Because these variables are inherited from the super class products
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    @Override
    public String toString() {
        return "Electronics " +", "+ super.toString() + ", " + brand + ", " +  warranty ;
    }
}


