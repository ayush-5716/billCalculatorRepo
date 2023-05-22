
import java.util.Scanner;

public class billCalculator extends Exception{

    static float discountedPrice=0;
    static String discName = "";
    static float discAmount = 0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int prices[] = new int[3];
        int quantities[] = new int[3];
        float totCosts[] = new float[3];
        boolean wrapped;

        prices[0] = 20;
        prices[1] = 40;
        prices[2] = 50;
        
        try{
            takeInput(quantities, sc);        //function to take input quantities
            System.out.println("Would you like your order to be wrapped?(true/false):");
            wrapped = sc.nextBoolean();
        }catch(Exception e){
            System.out.println("Data entered is invalid, kindly try again!");
            return;
        }

        totCosts[0] = (float) (quantities[0] * prices[0]);
        totCosts[1] = (float) (quantities[1] * prices[1]);
        totCosts[2] = (float) (quantities[2] * prices[2]);

        float totalAmount = 0;
        for(int i=0;i<3;i++){
            totalAmount += (float) totCosts[i];
        }
        discountedPrice = totalAmount;      //initilly discounted price is equal to total Amount

        //If cart total exceeds $200, apply a flat $10 discount on the cart total
        if(totalAmount> 200) {
            flat_10_discount(prices,quantities,totCosts,totalAmount);
        }

        //If quantity of any single product exceeds 10 units, apply a 5% discount on that item's total price
        int exceedingItem = exceeds(quantities,10); //this function calculates if a any quantity exceeds the given limit and returns that number
        if(exceedingItem != -1){
            bulk_5_discount(prices, quantities, totCosts, totalAmount, exceedingItem);
        }

        //If total quantity exceeds 20 units, apply a 10% discount on the cart total
        if(quantities[0] + quantities[1] + quantities[2] > 20){
            bulk_10_discount(prices, quantities, totCosts, totalAmount);
        }

        // If total quantity exceeds 30 units & any single product quantity greater than 15, The first 15 quantity have the original price and unit above 15 will get 50% discount
        int exceeding15Item = exceeds(quantities,15);  //this function calculates if a any quantity exceeds the given limit and returns that number
        if(quantities[0] + quantities[1] + quantities[2] > 30 && exceeding15Item!=-1 ){
            tiered_50_discount(prices, quantities, totCosts, totalAmount, exceeding15Item);
        }

        //if wrapping is set to true it will calculate wrapping cost
        float wrapCost =0;
        if(wrapped){                          
            wrapCost = (quantities[0] + quantities[1] + quantities[2]) * 1;
        }

        //calculating shipping cost
        float shippingCost = ((quantities[0] + quantities[1] + quantities[2])/10) * 5;

        printOutput(quantities, totCosts, totalAmount, shippingCost, wrapCost);
    }

    //printing the output in the required format
    public static void printOutput(int quantities[], float totCosts[],float totalAmount,float shippingCost,float wrapCost) {
        System.out.println("----------------------------------------------------");
        System.out.println("Product Name: " + "A" + " | Quantity: " + quantities[0] + " | total Cost: $" + totCosts[0]);
        System.out.println("Product Name: " + "B" + " | Quantity: " + quantities[1] + " | total Cost: $" + totCosts[1]);
        System.out.println("Product Name: " + "C" + " | Quantity: " + quantities[2] + " | total Cost: $" + totCosts[2]);
        System.out.println("----------------------------------------------------");
        System.out.println("Subtotal: " + totalAmount);
        System.out.println("Discount Name: " + discName + " | Discount Amount: $" + discAmount);
        System.out.println("Shipping fees: $" + shippingCost + " | Gift Wrap fee: $" + wrapCost);
        System.out.println("----------------------------------------------------");
        System.out.println("Total Cost: $" + (double) (discountedPrice + shippingCost + wrapCost));
        System.out.println("----------------------------------------------------");
    }

    public static void takeInput(int quantities[],Scanner sc) {
        
        System.out.println("Enter the quantity of item A: ");
        quantities[0] = sc.nextInt();
        System.out.println("Enter the quantity of item B: ");
        quantities[1] = sc.nextInt();
        System.out.println("Enter the quantity of item C: ");
        quantities[2] = sc.nextInt();
        
    }

    //Discount number 1
    public static void flat_10_discount(int prices[],int quantities[],float totCosts[],float totalAmount){
        float discountAmount = 10;
        float tempdiscountedPrice = totalAmount - discountAmount;
        update(tempdiscountedPrice,"flat_10_discount",discountAmount);
    }

    //Discount number 2
    public static void bulk_5_discount(int prices[],int quantities[],float totCosts[],float totalAmount,int exceedingItem){
        float discountAmount = (float) (totCosts[exceedingItem] * .05);
        float tempdiscountedPrice = totalAmount - discountAmount;
        update(tempdiscountedPrice,"bulk_5_discount",discountAmount);
    }

    //Discount number 3
    public static void bulk_10_discount(int prices[],int quantities[],float totCosts[],float totalAmount){
        float discountAmount = totalAmount * (float) (0.1);
        float tempDiscountedPrice = totalAmount - discountAmount;
        update(tempDiscountedPrice, "bulk_10_discount", discountAmount);
    }

    //Discount number 4
    public static void tiered_50_discount(int prices[],int quantities[],float totCosts[],float totalAmount,int exceeding15Item){
        float discountAmount = ((quantities[exceeding15Item] - 15) * prices[exceeding15Item]) / 2;
        float tempDiscountedPrice = totalAmount - discountAmount;
        update(tempDiscountedPrice,"tiered_50_discount", discountAmount);
    }

    //calculates if a any quantity exceeds the given limit and returns that number
    public static int exceeds(int quantites[],int limit){
        if(quantites[0] > limit){
            return 0 ;
        }else if(quantites[1] > limit){
            return 1 ;
        }else if(quantites[2] > limit){
            return 2 ;
        }else{
            return -1;
        }
    }

    public static void update(float tempDiscountedPrice,String discountName, float discountAmount){
        if(tempDiscountedPrice < discountedPrice){
            discountedPrice = tempDiscountedPrice;
            discName = discountName;
            discAmount = discountAmount;
        }
    }
}