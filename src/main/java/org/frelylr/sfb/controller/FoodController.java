package org.frelylr.sfb.controller;

import org.frelylr.sfb.common.factory.FoodFactory;
import org.frelylr.sfb.pojo.food.Food;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodController {

    @GetMapping("/getFood")
    public String getFood(@RequestParam(required = false) String kind) {

        Food food = null;
        if ("fruits".equals(kind)) {
            food = FoodFactory.getFruits();
        } else if ("vegetables".equals(kind)) {
            food = FoodFactory.getVegetables();
        } else if ("meat".equals(kind)) {
            food = FoodFactory.getMeat();
        } else {
            return "no food of this kind.";
        }
        List<String> foodList = food.getFoodList();

        return foodList.toString();
    }
}
