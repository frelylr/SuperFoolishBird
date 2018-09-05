package org.frelylr.sfb.pojo.food;

import java.util.ArrayList;
import java.util.List;

public class Vegetables implements Food {

    private List<String> foodList;

    public Vegetables() {

        foodList = new ArrayList<>();
        foodList.add("beans");
        foodList.add("potato");
        foodList.add("eggplant");
        foodList.add("pepper");
    }

    public List<String> getFoodList() {

        return foodList;
    }
}
