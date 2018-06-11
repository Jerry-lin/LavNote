# 第八章 Java中的并发工具类

## 8.1等待多线程完成的CountDownLatch

CountDownLatch允许一个或多个线程等待其他线程完成操作。

类似于Thread.join()

```java
package cn.edu.hust.atomic;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"正在运行");
            }
        },"thread-1");

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"正在运行");
            }
        },"thread-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

```

该方式是让当前执行线程等待join线程执行结束。

CountDownLatch的使用

```java
package cn.edu.hust.atomic;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    static CountDownLatch count=new CountDownLatch(2);
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"正在运行");
            }
        },"thread-1");

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"正在运行");
            }
        },"thread-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        new Thread(new Runnable() {
            @Override
            public void run() {
                count.countDown();
                System.out.println(1);
                count.countDown();
                System.out.println(2);
            }
        },"thread-3").start();
        count.await();
        System.out.println(3);
    }
}

```

构造器接受一个int参数，如果你想等待N个点完成，这里就传入N。

使用await()方法阻塞当前线程，直到N变得0.

计数器必须大于等于0，计数器等于0，计数器就是0调用await方法不会组塞当前线程。

## 8.2同步屏障CyclicBarrier

​	CyclicBarrier的字面意思是可循环使用的屏障。做的事情是让一组线程到达一个屏障时被阻塞，直到最后一个线程达到屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行。

### 8.2.1CyclicBarrier简介

​	构造器方法 CyclicBarrier(int parties)，参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达屏障，然后当前线程被组塞。

```java
package cn.edu.hust.atomic;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    static CyclicBarrier c=new CyclicBarrier(2);
    public static void main(String[] args)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        },"thread-1").start();
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
}

```

这里的结果不确定，因为主线程和子线程由CPU决定。

如果将构造器改为3，那么将会永远等待。

可以使用CyclicBarrier(int parities,Runnable )用于限制性构造器的方法。

### 8.2.2CyclicBarrier的应用场景

​	可以用于多线程计算数据，最后合并计算结果的场景。

### 8.2.3CyclicBarrier和CountDownLatch的区别

CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重置。所以CyclicBarrier能够处理更为复杂的业务场景。

## 8.3控制并发线程数的Semaphore

​	Semaphore用于控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源。

![image-20180521215717054](/Users/youyujie/Documents/读书笔记/并发编程的艺术/Semap方法.png)

## 8.4线程间交换数据的Exchanger

​	Exchanger是一个用于线程间协作的工具类。Exchanger用于线程间的数据交互。提供一个同步点，这个同步点，两个线程可以通过交互彼此的数据。

![image-20180521215644107](/Users/youyujie/Documents/读书笔记/并发编程的艺术/Exchanger测试.png)