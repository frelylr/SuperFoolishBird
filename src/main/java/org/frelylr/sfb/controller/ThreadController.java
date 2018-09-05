package org.frelylr.sfb.controller;


import org.frelylr.sfb.common.runnable.ThreadABC1;
import org.frelylr.sfb.common.runnable.ThreadABC2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadController {


    public static void main(String[] args) {

        // example 1: implements Runnable
//        new Thread(new ThreadTest("A")).start();
//        new Thread(new ThreadTest("B")).start();


        // example 2: join()
//        System.out.println(Thread.currentThread().getName() + " start");
//        Thread t1 = new Thread(new ThreadTest("A"));
//        Thread t2 = new Thread(new ThreadTest("B"));
//        t1.start();
//        t2.start();
//        try {
//            t1.join();
//            t2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName() + " end");

        // example 3: setPriority() does not always work
//        Thread t1 = new Thread(new ThreadTest("A"));
//        Thread t2 = new Thread(new ThreadTest("B"));
//        t1.setPriority(Thread.MIN_PRIORITY);
//        t2.setPriority(Thread.MAX_PRIORITY);
//        t1.start();
//        t2.start();

        // example 4: wait() and notify()
//        Object a = new Object();
//        Object b = new Object();
//        Object c = new Object();
//        Thread t1 = new Thread(new ThreadABC1("A", c, a));
//        Thread t2 = new Thread(new ThreadABC1("B", a, b));
//        Thread t3 = new Thread(new ThreadABC1("C", b, c));
//        try {
//            t1.start();
//            Thread.sleep(100);
//            t2.start();
//            Thread.sleep(100);
//            t3.start();
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // example 5: ReentrantLock and Condition
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition a = reentrantLock.newCondition();
        Condition b = reentrantLock.newCondition();
        Condition c = reentrantLock.newCondition();
        Thread t1 = new Thread(new ThreadABC2("A", reentrantLock, a, b));
        Thread t2 = new Thread(new ThreadABC2("B", reentrantLock, b, c));
        Thread t3 = new Thread(new ThreadABC2("C", reentrantLock, c, a));
        try {
            t1.start();
            Thread.sleep(100);
            t2.start();
            Thread.sleep(100);
            t3.start();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
