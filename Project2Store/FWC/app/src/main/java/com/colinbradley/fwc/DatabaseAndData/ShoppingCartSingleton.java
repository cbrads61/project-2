package com.colinbradley.fwc.DatabaseAndData;

import android.widget.TextView;

import com.colinbradley.fwc.ShoppingCartActivity;

import java.util.LinkedList;

/**
 * Created by colinbradley on 11/7/16.
 */
//CREATED THIS SINGLETON TO KEEP THE SHOPPING CART CORRECT
    //ALSO HOLDS METHODS THAT CALCULATES TOTAL PRICE OF THE CART

public class ShoppingCartSingleton {
    private static ShoppingCartSingleton cart = null;
    private static LinkedList<FWCGear> shoppingList;

    private ShoppingCartSingleton(){
        shoppingList = new LinkedList<>();
    }

    public static ShoppingCartSingleton getInstance(){
        if (cart == null){
            cart = new ShoppingCartSingleton();
        }
        return cart;
    }

    public void removeGear(FWCGear gear){
        shoppingList.remove(gear);
    }

    public void addGear(FWCGear gear){
        shoppingList.add(gear);
    }

    public LinkedList<FWCGear> getCart(){
        return shoppingList;
    }

    public String updateTotalPrice(){
        int totalPrice = getTotalPrice();
        String priceAsString = Integer.toString(totalPrice);
        returnNewPrice(priceAsString);

        return priceAsString;
    }

    public int getTotalPrice(){
        int i;
        int add = 0;
        for (i = 0; i < shoppingList.size(); i++){
            int itemPrice = shoppingList.get(i).getPrice();
            add = add + itemPrice;
        }
        return add;
    }

    public String returnNewPrice(String priceAsString){
        return priceAsString;
    }
}
