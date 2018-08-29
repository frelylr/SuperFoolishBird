package org.frelylr.sfb.common;

import org.frelylr.sfb.pojo.Food;
import org.frelylr.sfb.pojo.Fruits;
import org.frelylr.sfb.pojo.Meat;
import org.frelylr.sfb.pojo.Vegetables;

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
