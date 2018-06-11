# **第六章** Java并发容器和框架

## 6.1ConcurrentHashMap的实现原理和使用

ConcurrentHashMap式线程安全且高效的HashMap。

### 6.1.1为什么要选择ConcurrentHashMap

**1.线程不安全的HashMap**

**2.效率低下的HashTable**

HashTable使用synchronized来保证线程的安全，在线程激烈的情况下，Hashtable的效率低

**3.ConcurrentHashMap的锁分离技术可有效提升并发访问率**

首先将数据分成一段一段地存储，然后给每一段数据配一把锁，当每一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。

### 6.1.2ConcurrentHashMap的结构

ConcurrentHashMap是由Segment数据结构和HashEntry数组组成，Segment是一种可重入锁，在ConcurrentHashMap里扮演锁的角色；HashEntry则用于存储键值对数据。一个ConcurrentHashMap里包含一个Segment数组。Segment的结构和HashMap类似，是一种数组和链表机构，一个Segment里包含一个HashEntry数组，每个HashEntry是一个链表的结构元素，每个Segment守护一个HashEntry数组里的元素，当对HashEntry数组的数据进行修改的，必须首先对它随影的Segment锁。

![image-20180508200505293](/Users/youyujie/Documents/读书笔记/并发编程的艺术/ConcurrentHashMap的结构.png)

### 6.1.3ConcurrentHashMap的初始化

initCapacity、loadFactor和concurrentLevel等几个参数初始化segment数组、段偏移量segmentShift、段掩码segmentMask和每个segment里的HashEntry数组来实现。

**1.初始化segments数组**

segment数组长度是2的N次方，默认值是16

**2.初始化segmentShift和segmentMask**

segmentShift用于定位参与散列算法的位数，segmentShift等于32减去sshift，所以等于28，segmentMask等于sszie减1，也就是15位。

**3.初始化每个semengt**

默认清咖滚下HashEntry的长度是1

### 6.1.4定位Segment

首先使用hashcode，然后使用一个算法对元素的hashcode再次hash。

这样做的好处是减少散列冲突，使元素能够均匀的分布在不同的Segment上，从而提高容器的存取效率。

定位Segment的算法：

segmentShift最高的4位于segmentMask相于

### 6.1.5ConcurrentHashMap的操作

**1.get操作**

操作简单高效，先经过一次散列，然后再次散列定位到Segment，不需要加锁，除非读到的值是空才会加锁。

**2.put操作**

必须加锁，需要进行两步操作

1.是否扩容

​	判断是否达到阈值，如果达到，那么将会扩容到原来的两倍，然后将原数组元素再散列后插入到新数组中。

2.将元素放在HashEntry中

**3.size操作**

先尝试2次通过不锁住Segment的方式来统计各个Segment大小，如果统计的过程中，容器的count发生变化，则再采用加锁的方式来统计所有的Segment大小。

## 6.2ConcurrentLinkedQueue

基于链接节点的无解线程安全队列，采用先进先出的规则对节点进行排序，当我们添加一个元素的时候，他会添加队列的尾部；当我们获取一个元素时，他会返回队列的元素。他采用了wait-free算法(CAS算法)来实现。

### 6.2.1ConcurrentLinkedQueue的结构

![image-20180508203355785](/Users/youyujie/Documents/读书笔记/并发编程的艺术/ConcurretnLinkedQueue的结构.png)

### 6.2.2入队列

**1.入队列的过程**

入队列就是将入队节点添加到队列的尾部。

入队列做了两件事情：

1.将入队节点设置成当前队列尾节点的写一个节点

2更新tail节点，如果tail节点的next节点不为空，则将插入节点设置为tail节点，如果tail节点的next节点为空，则将入队列节点设置tail的next即诶单，所以tail节点不总是尾节点。

**2.定位尾节点**

**3.设置入队节点为尾节点**

### 6.2.3出队列

出队列时从队列返回一个节点元素，比轻轻空盖即诶单对元素的引用。

并不是每一次都更新head节点，当head里面有元素的时候直接弹出节点的元素，否者更新head。

这样做是减少CAS更新head节点的消耗，提高出队效率。

## 6.3Java中的阻塞队列

### 6.3.1什么时阻塞队列

阻塞是支持两个附加操作的队列。

1.支持阻塞的支持方法：当队列满的时候，队列回族塞插入元素的线程，直到队列不满

2.支持阻塞的移除方法：意思是在队列为空时，互殴去元素的线程会等待队列变为非空。

![image-20180508205951235](/Users/youyujie/Documents/读书笔记/并发编程的艺术/阻塞队列的方法.png)

### 6.3.2Java里的阻塞队列

ArrayBlockingQueue：数据结构组成的有界阻塞队列

LinkedBlokingQueue:一个链表结构组成的有界组塞队列

PriorityBlockingQueue:一个支持优先级排序的无界阻塞队列

DealyQueue:一个使用优先级队列实现的无界阻塞队列

SynchronousQueue：一个不存储元素的组塞队列

LinkedTransferQueue:一个链表结构组成的无界阻塞队列

LinkedBlockingDeque：链表结构组成的双向阻塞队列

**1.ArrayBlockingQueue**

FIFO，不保证公平访问

**2.LinkedBlockingQueue**

FIFO

**3.PriorityBlockingQueue**

支持优先级，不保证同优先级元素的顺序

**4.DelayQueue**

通过使用PrioprityQueue来实现，队列中的元素必须实现Delayed接口，只有时间到了，才从队列中出来

缓存系统的设计

定时任务调度

**5.SynchronousQueue**

一个put操作必须等待一个take操作

支持公平访问

默认不公平

适合传递场景

**6.LinkedTransferQueue**

1.transfer方法

将生产者传入的元素立刻传递给消费者，如果没有消费者接受，放在tail，然后自旋等待

2.tryTransfer方法

试探生产者传入的元素是否能直接传递给消费者，如果没有消费者接受，返回false

7.**LinkedBlockingDeque**

### 6.3.3阻塞队列的实现原理

使用通知模式实现

当生产者往满的队列里添加元素，会阻塞住生产者，当消费者消费一个队列中的元素后，会通知生产者当前队列可用。

## 6.4Fork/Join框架

### 6.4.1什么时Fork/Join框架

是将一个大人物分割成若干小任务，最终汇总每个小任务结果后得到大任务结果的框架。

Fork就是将一个大任务切分成为若干字任务并行的执行，Join就是合并这些字任务的执行结果，最后得到这个大任务结果。

流程图如下：

![image-20180521193918110](/Users/youyujie/Documents/读书笔记/并发编程的艺术/Fork:Join流程图.png)

![image-20180521193946318](/Users/youyujie/Documents/读书笔记/并发编程的艺术/Fork:Join流程图2.png)

### 6.4.2工作窃取算法

工作窃取算法值得时某个线程从其他队列里窃取任务来执行。

使用工作窃取算法，多个线程会访问统一队列，所以为了减少窃取任何线程和被窃取任务线程之间的竞争，通常会使用双端队列，被窃取任务线程永远从双端队列的头部哪去任务执行，而窃取任务的线程永远都会从尾部拿去任务。

![image-20180521200358829](/Users/youyujie/Documents/读书笔记/并发编程的艺术/工作窃取算法流程图.png)

优点：充分利用线程进行并行计算，减少线程间的竞争。

缺点：存在竞争情况，例如队列直邮一个任务

### 6.4.3Fork/Join框架的设计

设计思想：

1.任务分割：fork类将大任务分割成为子任务，有可能很大还需要不停的分割

2.执行任务合并结果：子任务放入双端队列，然后几个启动线程分别从双端队列里获取任务执行。

Fork/Join使用来个类完成这两件事情。

1.ForkJoinTask:创建一个ForkJoin()任务，执行fork()和join()操作的机制

RecursiveAction:用于没有返回结果的任务

RecusiveTask：用于有返回结果的任务

### 6.4.4使用Fork/Join框架

```java
package cn.edu.hust.ForkJoin;

import java.util.concurrent.*;

public class CountInt extends RecursiveTask<Integer> {
    private static final int threshold=2;
    private int start;
    private int end;

    public CountInt(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum=0;
        boolean flag=(end-start)<=threshold;
        if(flag)
        {
            for(int i=start;i<=end;i++)
            {
                sum+=i;
            }
        }
        else
        {
            int mid=(start+end)/2;
            CountInt left=new CountInt(start,mid-1);
            CountInt right=new CountInt(mid,end);
            left.fork();
            right.fork();
            int sum1=left.join();
            int sum2=right.join();
            sum=sum1+sum2;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool=new ForkJoinPool();
        CountInt t=new CountInt(0,100);
        Future<Integer> result=pool.submit(t);
        System.out.println(result.get());
    }
}

```

### 6.4.5Fork/Join框架的异常处理

有时候会抛出异常，主线程无法捕捉异常，使用ForkJoinTask提供的isCompletedAbnormally()来检查异常或者任务别取消，task.getExceptioon()如果任务没有完成，返回null

### 6.4.6Fork/Join框架的实现原理

ForkJoinPool由ForkJoinTask数组和ForkJoinWorkerThread数组组成，ForkJoinTask数组负责将存放程序提交给ForkJoinPool的任务，而ForkJoinWorkerThread数组负责执行这些任务。

(1)ForkJoinTask的fork方法实现原理

​	fork方法时，程序会调用ForkJoinWorkerThread的pushTask方法一步的执行这个任务，然后立即放回结果。然后调用ForkJoinPool的singalWork（）方法唤醒或者创建一个线程来执行任务。

(2)ForkJoinTask的join方法来实现原理

​	Join方法主要的作用是阻塞当前线程并等待获取结果。调用doJoin()方法判断当前任务状态，有四种状态：已完成、被取消、信号和出现异常

完成，返回执行结果

被取消 抛出异常

抛出异常 直接抛出异常

doJoin()方法，没有执行完成，那么等待执行，执行完成设置状态位normal，抛出异常，状态设置为异常。

