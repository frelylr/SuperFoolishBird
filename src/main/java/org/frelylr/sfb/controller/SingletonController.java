package org.frelylr.sfb.controller;

import org.frelylr.sfb.common.singleton.EnumSingleton;
import org.frelylr.sfb.common.singleton.Singleton;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api("单例模式")
@RestController
public class SingletonController {

    @GetMapping("/enumSingleton")
    public String testEnumSingleton() {

        return EnumSingleton.INSTANCE.get();
    }

    @GetMapping("/singleton")
    public String testSingleton() {

        return Singleton.getSingleton().get();
    }
}
