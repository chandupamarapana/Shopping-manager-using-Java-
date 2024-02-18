import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
        private List<Product> products ;
        public ShoppingCart(){
            this.products = new ArrayList<>();
        }
        public void addProduct(Product product){  //the method to add products into the shopping cart
            if (product != null){
                products.add(product);
                System.out.println("The product is added to the shopping cart "+ product.getProductName());
            }
            else {
                System.out.println("Invalid Product. Cannot add to the shopping cart. ");
            }
        }
        public void removeProduct(Product product){
            if(product != null ){
                products.remove(product);
                System.out.println("The product is removed from the cart "+ product.getProductName());
            }
            else {
                System.out.println("The product is not found in the cart ");
            }
        }
       public double calculateTotalCost(){         // the method that is used to calculate the total cost in the shopping cart
            double totalCost = 0.0;             // initialise the variable totalcost
            for(Product product : products){  //iterate through the arraylist and get the total price
                totalCost += product.getPrice();    //get the total cost by adding each price of the product
            }
            return totalCost; //return the total cost


        }




    }

