package org.frelylr.sfb.common.runnable;

public class ThreadABC1 implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    public ThreadABC1(String name, Object prev, Object self) {

        this.name = name;
        this.prev = prev;
        this.self = self;
    }


    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    self.notify();
                }
                if (i < 9) {
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
