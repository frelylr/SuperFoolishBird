package org.frelylr.sfb.common.singleton;

public enum EnumSingleton {

    INSTANCE;

    public String get() {

        System.out.println("enum singleton");

        return "enum singleton";
    }
}
