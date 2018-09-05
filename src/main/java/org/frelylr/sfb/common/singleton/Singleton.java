package org.frelylr.sfb.common.singleton;

public class Singleton {

    private static volatile Singleton singleton = null;

    private Singleton() {

    }

    public static Singleton getSingleton() {

        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public String get() {

        System.out.println("singleton");

        return "singleton";
    }
}
