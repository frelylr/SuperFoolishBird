package org.frelylr.sfb.pojo;

import java.util.ArrayList;
import java.util.List;

public class Meat implements Food {

    private List<String> foodList;

    public Meat() {
        foodList = new ArrayList<>();
        foodList.add("pork");
        foodList.add("beef");
        foodList.add("mutton");
        foodList.add("chicken");
    }

    public List<String> getFoodList() {

        return foodList;
    }
}
