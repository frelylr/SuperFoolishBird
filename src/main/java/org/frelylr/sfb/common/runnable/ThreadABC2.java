package org.frelylr.sfb.common.runnable;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadABC2 implements Runnable {

    private String name;
    private final ReentrantLock reentrantLock;
    private final Condition thisCondition;
    private final Condition nextCondition;

    public ThreadABC2(String name, ReentrantLock reentrantLock, Condition thisCondition, Condition nextCondition) {

        this.name = name;
        this.reentrantLock = reentrantLock;
        this.thisCondition = thisCondition;
        this.nextCondition = nextCondition;
    }

    @Override
    public void run() {

        reentrantLock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print(name);
                nextCondition.signal();
                if (i < 9) {
                    try {
                        thisCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}
