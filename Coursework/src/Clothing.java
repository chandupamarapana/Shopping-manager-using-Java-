public class Clothing extends Product {
    private String size ;
    private String colour ;

    public Clothing(String product_ID, String productName, int number_of_available_items, double price, String size, String colour){
        super(product_ID, productName, number_of_available_items, price);
        this.size=size;
        this.colour=colour;
    }

    public String getColour() {
        return colour;
    }

    public String getSize() {
        return size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Clothing " +", " +super.toString() + ", " + size + ", "+ colour  ;
    }
}



