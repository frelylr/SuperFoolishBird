package org.frelylr.sfb.pojo.food;

import java.util.ArrayList;
import java.util.List;

public class Fruits implements Food {

    private List<String> foodList;

    public Fruits() {
        foodList = new ArrayList<>();
        foodList.add("apple");
        foodList.add("banana");
        foodList.add("orange");
        foodList.add("peach");
    }

    public List<String> getFoodList() {

        return foodList;
    }
}
