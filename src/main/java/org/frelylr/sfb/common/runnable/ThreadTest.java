package org.frelylr.sfb.common.runnable;

public class ThreadTest implements Runnable {

    private String name;

    public ThreadTest(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " running start");
        for (int i = 0; i < 5; i++) {
            System.out.println(name + " running: " + i);
            try {
                Thread.sleep((int) (Math.random() * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " running end");
    }
}
