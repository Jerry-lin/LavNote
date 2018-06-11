# 第四章 Java并发编程基础

## 4.1线程简介

### 4.1.1什么时线程

现在操作系统调度的最小单元时线程，叫做轻量级进程，在一个进程中可以创建多个线程，这些线程都拥有各自的计数器、堆栈和局部变量等属性，并且能够访问共享的内存变量。

### 4.1.2为什么要使用多线程

1.更多的处理器核心

2.更快的响应时间

3.更好的编程模型

### 4.1.3线程优先级

在Java线程中，通过一个整型成员变量priority来控制优先级，优先级范围从1-10，在线程构建的时候可以通过setPriority(int)方法来修改优先级，默认优先级时5，优先级高的线程分配时间片的数量要多于优先级低的程序。

### 4.1.4线程的状态

Java线程6种状态

![image-20180508112213740](/Users/youyujie/Documents/读书笔记/并发编程的艺术/线程状态.png)

java线程状态变迁如下

![image-20180508112356131](/Users/youyujie/Documents/读书笔记/并发编程的艺术/java状态变迁.png)

### 4.1.5Daemon线程

Daemon线程是一种支持型线程，因为它主要被用作程序中后台调度以及支持型工作。

Daemon属性需要在启动之前设置，不能在启动之后设置。

在构建Daemon线程诗，不能依靠finally快中的内容确保执行关闭和清理资源逻辑。

## 4.2启动和终止线程

### 4.2.1构建线程

一个新的构造线程对象是由其parent线程进行空间分配，而child线程继承了parent是否是Daemon、优先级和加载资源的contextClassLoader以及可继承的ThreadLocal同时还会分配一个唯一的ID标识这个child线程。

### 4.2.2启动线程

使用start()方法调用。

### 4.2.3理解中断

其他线程调用该线程的interrupted()方法对其进行中断操作。

许多声明抛出InterruptedException的方法例如sleep方法，这些方法抛出异常之前，Java虚拟机会先将该线程的中断标识位清楚，然后抛出异常，此时调用isInterrupted方法将会放回false。

### 4.2.4过期的suspend()、resume()、stop()

这些方法不会保证资源正常释放，不建议使用

### 4.2.5安全终止线程

使用volatile变量安全优雅的终止线程。

## 4.3线程间通信

### 4.3.1volatile和synchronized关键字

关键字volatile可以用来修饰字段，就是告知程序任何对该变量的访问聚需要从共享内存中获取，而对它的改变必须同步刷新回共享内存，它能保证所有线程对变量的可见性。

关键字synchronized可以修饰方法或者以同步块的形式来进行使用，它主要确保多个线程在同一时刻，只能拿又一个线程处于方法或者同步块中，它保证了线程对变量的可见性和排他性。

### 4.3.2等待／通知机制

等待/通知的相关方法是仁义Java对象都具备的，因为这些方法被定义在所有对象的超类object上，如下表

![image-20180508143749778](/Users/youyujie/Documents/读书笔记/并发编程的艺术/等待:通知的相关方法.png)

等待通知机制，是指一个线程A调用对象O的wait()方法进入等待状态，而另一个线程B调用对象O的notify()或者notifyAll()方法，线程A收到通知后从对象O的wait()方法返回，进而执行后续操作。

1.使用wait()、notify()和notifyAll(）时需要对调用对象加锁

2.调用wait()方法后，线程状态由Running变为Waiting状态进入对象等待队列

3.notify()或者notifyAll()方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或者notify All（）的线程释放锁后，等待线程才有机会从wait()返回。

### 4.3.3等待/通知的经典范式

生产者消费者模型

### 4.3.4管道输入/输出

管道输入/输出流主要包括如下4中具体实现：PipedOutputStream、PipeInputStream、PipedReader和PipedWriter，前面两种面向字节，而后两种面相字符。

### 4.3.5Thread.join()的使用

如果一个线程A执行thread.join()语义，其含义是：当线程A等待thread线程终止以后才从thread.join()返回。

join()方法，每一个线程等待前驱线程终止后，才从join()方法返回。

### 4.3.6ThreadLocal的使用

ThreadLocal，即线程变量，是一个以ThreadLocal对象为键、任意对象为值的存储结构，着个结构被福袋在线程上，也就说一个线程中可以根据一个ThreadLocal对象查询到绑定在这个线程上的一个值。

实例代码：

```java
public class ThreadLocalTest {
    private static ThreadLocal<Long> times=new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };
    public static void begin()
    {
        times.set(System.currentTimeMillis());
    }
    public static Long end()
    {
        return System.currentTimeMillis()-times.get();
    }
    public static void main(String[] args) throws InterruptedException {
        ThreadLocalTest.begin();
        Thread.sleep(1000);
        System.out.println(ThreadLocalTest.end());
    }
}

```

输出结果：

```java
1004
```

## 4.4线程应用实例

### 4.4.1等待超时模式

