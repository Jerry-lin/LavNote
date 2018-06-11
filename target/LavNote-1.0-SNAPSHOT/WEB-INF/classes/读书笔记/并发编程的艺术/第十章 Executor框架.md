# 第十章 Executor框架

线程的工作单位是Runnable和Callable，执行机制由Executor框架提供。

## 10.1Executor框架简介

### 10.1.1Executor框架的两级调度

在上层，Java多线程程序通常把应用分解成为若干任务，然后使用用户级的调度器(Executor框架)将这些任务映射成为固定数量的线程；在底层，操作系统内核将这些线程映射到硬件处理器上。

![image-20180522090322635](/Users/youyujie/Documents/读书笔记/并发编程的艺术/两级映射.png)

![image-20180522090349745](/Users/youyujie/Documents/读书笔记/并发编程的艺术/两级调度模型.png)

应用程序通过Executor框架控制上层的调度；而下层的调度由操作系统内核控制，下层的调度不受应用程序的控制。

### 10.1.2Executor框架的结构和成员

**1.Executor框架的结构**

​	主要分为3大部分：

​	1.任务：被执行任务需要实现的接口：Runnable接口和Callable接口

​	2.任务的执行：任务执行机制的核心接口Executor，以及继承自Executor的Executor的ExecutorService接口。Executor框架有两个关键类实现了ExecutorService接口(ThreadPoolExecutor和ScheduThreadPoolExecutor)。

​	3.异步计算结果：包含接口Future和实现Future接口的FutureTask类。

![image-20180522091152643](/Users/youyujie/Documents/读书笔记/并发编程的艺术/Executor框架的使用示意图.png)

主线程首先要创建实现Runnable或者Callable接口的任务对象。

1.Executors可以把一个Runnable对象封装成为一个Callable对象

2.然后将Runnable对象或者Callable对象直接交给ExecutorService执行

3.ExecutorService将返回一个实现Future接口的对象。由于FutureTask实现Runnable，程序员也可以创建FutureTask，然后直接交给ExecutorService执行。

4.主线程可以执行FutureTask.get()方法等待任务执行完成。

**2.Executor框架的成员**

​	Executor框架的主要成员：ThreadPoolExecutor、ScheduledThreadPoolExecutor、Future接口、Runnable接口、Callable接口和Executors

(1)ThreadPoolExecutor

​	3种类型：SingleThreadExecutor、FixedThreadPool、CachedThreadPool。

​	FixedThreadPool：创建使用固定线程数，使用当前线程数量的应用场景，比较适应负载比较重的服务器

​	SingleThreadExecutor: 创建单个线程的SingleThreadExector，适用于保证顺序第执行各个任务

​	CachedThreadPool:创建一个根据需要创建新线程，大小无界的线程池，适用于执行很多的短期异步任务的小程序。

(2)ScheduledThreadPoolExecutor

​	通常使用工厂类Executors创建。2种类型的ScheduThreadPoolExecutor：

​	ScheduledThreadPoolExecutor：包含若干线程，适用于需要多个后台线程执行周期任务，同时为了满足资源管理的需求而限制后台线程的数量的应用场景。

​	SingleThreadScheduledExecutor：包含一个线程，适用于需要单个后台线程执行周期任务，同时需要保证顺序的执行各个任务的应用场景。

3.Future接口

​	Future接口和实现Future接口的FutureTask类用来表示异步计算的结果。

4.Runnable接口和Callable接口

​	这个接口的实现类可以被ThreadPoolExecutor或SchedledThreadPoolExecutor执行。他们之间的区别是Runnable不返回结果，Callable可以返回结果。

## 10.2ThreadPoolExecutor详解

Executor核心类是ThreadPoolExecutor，线程池的额主要实现类：

1.coolPool：核心线程池大小

2.maximumpool：最大线程池的大小

3.BlockingQueue:用来暂时保存任务的工作队列

4.RejectedExecutionHandler:当ThreadPoolExcutor已经关闭或ThreadPoolExecutor已经饱和时，executor方法调用的Handler

可以创建3类型：

FixedThreadPool

SingleThreadExecutor

CachedThreadPool

### 10.2.1FixedThreadPool

​	可重用固定线程数的线程池。

![image-20180522095809867](/Users/youyujie/Documents/读书笔记/并发编程的艺术/固定线程池源码实现.png)

​	核心线程数和最大线程数都是制定参数

​	当线程数大于corePoolSize时，KeepAliveTime为多余的空闲线程等待任务的最长时间，超过这个时间后多余的线程被终止。这里的设置为0L

​	![image-20180522100112003](/Users/youyujie/Documents/读书笔记/并发编程的艺术/固定线程池执行.png)

​	流程如下：

1.当前运行的线程数少于corePoolSize，除创建线程执行任务

2.运行线程数等于corePoolSize，将任务加入LinkedBlockingQueue

3.反复冲队列获取任务，使用无界对类，最大仁良伟整型最大值

### 10.2.2SIngleThreadExecutor详解

​	使用单个worker线程的Executor

​	核心线程数和最大线程数设置为1，使用无界队列作为组塞队列

![image-20180522100613742](/Users/youyujie/Documents/读书笔记/并发编程的艺术/单个线程池的执行.png)



### 10.2.3CachedThreadPool详解

​	根据需求创建新线程的线程池。

​	核心线程池数量为0，最大为整型最大值，KeepAliveTime为60秒，使用SynchronousQueue作为线程池的工作队列

![image-20180522100917339](/Users/youyujie/Documents/读书笔记/并发编程的艺术/可变线程池的执行.png)

​	

​	![image-20180522101009737](/Users/youyujie/Documents/读书笔记/并发编程的艺术/可变线程池任务传递.png)

## 10.3ScheduledThreadPoolExecutor详解

​	继承自ThreadPoolExecutor，主要用于给定的延迟之后运行，或者定期执行任务。

### 10.3.1ScheduledThreadPoolExecutor的运行机制

​	使用DelayQueue无界队列，核心线程数和最大线程数没有什么意义

​	执行分为两部分：

​	1.当调用SchedThreadPoolExecutor的scheduledAtFixedRate()或者scheduleWithFixedDelay()方法是，会想SchedThreadPoolExecutor()的DelayQueue添加一个实现了RunnableScheduledFuture接口的ScheduledFutureTask。

​	2.线程池中的线程从DelayQueue中获取ScheduledFutureTask然后执行任务。

![image-20180522101657002](/Users/youyujie/Documents/读书笔记/并发编程的艺术/可调度任务调度.png)

### 10.3.2ScheduledThreadPoolExecutor的实现

3个成员变量

​	long型成员time，表示这个任务将要被执行的具体时间

​	long型成员sequenceNumber，表示这个任务被添加ScheduThreadPoolExecutor中的序号

​	long型成员变量preiod，表示任务执行的间隔周期

DelayQueue封装优先队列，根据time进行升序排序，time小的排在前面，如果time相同，就比较sequenceNumber，小的排在前面。

![image-20180522102214269](/Users/youyujie/Documents/读书笔记/并发编程的艺术/可调度执行部分.png)

​	执行流程：

1.线程获取到期任务，任务指的是time大于等于当前时间

2.线程执行这个ScheduFutrueTask

3.线程修改ScheduledFutreTask的time变量为下次要修改的执行时间

4.修改后放入队列

DelayQueue.take()方法的源代码

![image-20180522102608931](/Users/youyujie/Documents/读书笔记/并发编程的艺术/DelayQueue的takefangfa.png)

![image-20180522102643124](/Users/youyujie/Documents/读书笔记/并发编程的艺术/ScheduleThreadPool获取任务执行.png)

分为三个步骤

1.获取lock

2.获取周期任务

如果队列为空，当前线程Condition中等待，否则执行y下一步

如果队列头元素的time时间比当前时间大，到Condition中等待到time时间，否则下一步

获取队列都元素，如果队列不为空，唤醒Condition中的所有线程

3释放锁

下面是队列的add执行示意图

![image-20180522103053898](/Users/youyujie/Documents/读书笔记/并发编程的艺术/队列的add操作.png)

分为3个步骤

1.获取Lock

2.添加任务

向队列添加任务，唤醒Condition中等待的所有线程

3.释放锁

## 10.4FutureTask详解

Future接口和实现Future接口的FutureTask类，代表异步计算的结果。

### 10.4.1FutureTask简介

FutureTask.run() 方法执行的时机；FutureTask处于下面3种状态

1.未启动。没有执行run方法之前，这个FutureTask处于未启动状态

2.已启动。run方法被执行的过程，处于已启动状态

3.已完成，执行完成

![image-20180522103549332](/Users/youyujie/Documents/读书笔记/并发编程的艺术/FutureTask状态迁移.png)

FutureTask的get方法和cancel方法执行示意图

![image-20180522103716847](/Users/youyujie/Documents/读书笔记/并发编程的艺术/FutureTask的get和cancel示意图.png)

### 10.4.2FutureTask的使用

​	当一个线程等待另一个线程吧某个任务执行完成后他才能继续执行，此时可以使用FutureTask。

​	当多个线程试图同时执行同一个任务时，只允许一个线程执行任务，其他线程需要等待这个任务执行完成在能继续执行。

![image-20180522104133655](/Users/youyujie/Documents/读书笔记/并发编程的艺术/FutureTask的使用.png)

### 10.4.3FutureTask的实现

​	FutureTask的实现基于AQS。

下面是FutureTask的示意图

![image-20180522104404206](/Users/youyujie/Documents/读书笔记/并发编程的艺术/FutureTask的实现基于AQS.png)

Sync是FutureTask的内部私有类，继承自AQS。创建FutureTask时会创建内部私有的成员对象Sync，FutureTask所有的公有方法都直接委托给内部私有的Sync。

线程级联唤醒

![image-20180522104732634](/Users/youyujie/Documents/读书笔记/并发编程的艺术/FutureTask级联唤醒.png)