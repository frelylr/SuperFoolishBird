package org.frelylr.sfb.common.factory;

import org.frelylr.sfb.pojo.food.Food;
import org.frelylr.sfb.pojo.food.Fruits;
import org.frelylr.sfb.pojo.food.Meat;
import org.frelylr.sfb.pojo.food.Vegetables;

public class FoodFactory {

    private FoodFactory() {

    }

    public static Food getFruits() {

        return new Fruits();
    }

    public static Food getMeat() {

        return new Meat();
    }

    public static Food getVegetables() {

        return new Vegetables();
    }

}
