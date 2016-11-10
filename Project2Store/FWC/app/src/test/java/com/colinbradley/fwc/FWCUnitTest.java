package com.colinbradley.fwc;

/**
 * Created by colinbradley on 11/9/16.
 */

import android.content.Context;

import com.colinbradley.fwc.DatabaseAndData.DatabaseHelper;
import com.colinbradley.fwc.DatabaseAndData.FWCGear;
import com.colinbradley.fwc.DatabaseAndData.ShoppingCartSingleton;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class FWCUnitTest {
    @Test
    public void doesGearGenerateCorrectly(){
        FWCGear gear = new FWCGear(1, "Name", 1234567890, "Description", "type", 999);

        String id = Integer.toString(gear.getId());
        String name = gear.getName();
        String image = Integer.toString(gear.getImage());
        String description = gear.getDescription();
        String type = gear.getType();
        String price = Integer.toString(gear.getPrice());

        List<String> actual = new ArrayList<>();
        actual.add(id);
        actual.add(name);
        actual.add(image);
        actual.add(description);
        actual.add(type);
        actual.add(price);

        List<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("Name");
        expected.add("1234567890");
        expected.add("Description");
        expected.add("type");
        expected.add("999");

        assertEquals(expected, actual);
    }

    @Test
    public void doesShoppingCartListAddItems(){
        FWCGear gear1 = new FWCGear(1, "Name", 1234567890, "Description", "type", 999);
        FWCGear gear2 = new FWCGear(2, "Name", 1234567890, "Description", "type", 999);
        FWCGear gear3 = new FWCGear(3, "Name", 1234567890, "Description", "type", 999);

        List<FWCGear> shoppinglist = new LinkedList<>();
        shoppinglist = ShoppingCartSingleton.getInstance().getCart();

        shoppinglist.add(gear1);
        shoppinglist.add(gear2);
        shoppinglist.add(gear3);

        int expectedSize = 3;
        int actualSize = shoppinglist.size();

        assertEquals(expectedSize,actualSize);
    }

}
