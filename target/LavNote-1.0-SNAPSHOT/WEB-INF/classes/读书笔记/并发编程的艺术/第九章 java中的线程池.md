# 第九章 java中的线程池

线程池的好处：

1.降低资源消耗

2.提高响应数独

3.提高线程的可管理性

## 9.1线程池实现原理

1.线程池判断核心线程池里的线程是否都在执行任务。

如果不是，那就创建一个新的工作线程来执行任务。如果核心线程池里的线程都在执行任务，则进入下个流程。

2.线程池来判断工作队列是否已经满。如果工作队列没有满，则将新提交的任务存储子啊这个工作队列里。如果工作队列满，则进入下一个流程。

3.线程池判断线程池的线程是否都处于工作状态。如果没有，则创建一个新的工作线程执行任务。如果已经满了，则交给饱和和策略来处理这个任务

![image-20180521220537911](/Users/youyujie/Documents/读书笔记/并发编程的艺术/线程池执行流程.png)

ThreadPoolExecutor执行示意图

![image-20180521220803760](/Users/youyujie/Documents/读书笔记/并发编程的艺术/ThreadPoolExcutor.png)

![image-20180521220833246](/Users/youyujie/Documents/读书笔记/并发编程的艺术/ThreadPoolExcutor2.png)

ThreadPoolExecutor执行execute分为4种情况：

1.线程小于corePoolSize，创建线程执行任务，需要获取全局锁

2.多余corePoolSize，加入BlockingQueue

3.队列已满，创建线程来处理任务，需要获取全局锁

4.超过maxpoolSize，任务拒绝，调用RejectedExecutionHandler.rejecttedExecutin()方法。

设计总的思路：调用execute()方法，尽可能避免获取全局锁。

源码分析

![image-20180521221352221](/Users/youyujie/Documents/读书笔记/并发编程的艺术/ThreadExecutorPool的执行方法源码.png)

工作线程：线程池创建线程，将线程封装工作线程Worker，执行时，循环获取工作队列里的任务来执行。

Worker的run()工作执行：

![image-20180521221636085](/Users/youyujie/Documents/读书笔记/并发编程的艺术/Worker的执行.png)

## 9.2线程池的使用

### 9.2.1线程池的使用

通过ThreadPoolExecutor来创建一个线程池。

![image-20180521221927263](/Users/youyujie/Documents/读书笔记/并发编程的艺术/创建线程池.png)

参数含义：

1.线程池基本大小，如果调用线程池的prestartAllCoreThreads()方法，线程池会提前叉棍间并开启多有基本线程。

2.任务队列：用于保存等待执行的任务的阻塞队列。

3.线程池最大数量：线程允许创建的最大线程数。如果队列满了，并且以创建的线程数小于最大线程数，则线程数会创建新的线程执行任务。

4.ThreadFactory：用于创建线程工厂，通过现场工厂给每个创建出来的线程设置更有意思的名字。

5.饱和策略：

处理策略，主要是线程池处于饱和状态。

默认是AbortPolicy

![image-20180521222542284](/Users/youyujie/Documents/读书笔记/并发编程的艺术/线程池处理策略.png)

线程活动保持时间：线程池的工作线程空闲后，保持存活的时间。

TimeUnit线程活动保持时间的单元

### 9.2.2向线程池提交任务

​	execute()和sumbit()

​	execute()用于提交不语要返回值的任务，索引无法判断任务是否被线程池执行成功。

​	submit()用于提交需要返回值的任务。返回一个future类型的对象，通过future判断任务是否执行成功，通过future的get方法获取返回值，get()方法会阻塞当前线程直到任务完成。

submit方法的使用

![image-20180521223046041](/Users/youyujie/Documents/读书笔记/并发编程的艺术/线程池submit方法.png)

### 9.2.3关闭线程池

​	可以通过shutdown()和shutdownNow()来关闭线程池。原理是遍历线程池中的工作线程，然后逐个调用线程的interrupt方法来中断新车，所以无法响应中断的任务可能永远无法终止。

​	shutdwonNow：将状态设置为Stop，饭后尝试停止正在执行或者真挺任务的线程

​	shutdown：将状态设置为shutdown状态，然后中断所有没有正在执行任务的线程。

### 9.2.4合理配置线程池

​	几个角度分析：

1.任务的性质：CPU密集型任务、IO密集型任务和混合型任务

2.任务的优先级：高、中、低

3.执行时间：长、中和短

4.依赖性：是否依赖其他系统资源

建议使用有界队列

### 9.2.5线程池的监控

监控线程池可以用一下属性：

taskCount：线程池需要执行的任务数量

completedTaskCount：线程池在运行过程中已完成的数量，小于或等于taskCount

largetPoolSize：线程池曾经创建过的最大线程数量。

getPoolSize：线程池的线程数量

getActiveCount：获取活动的线程数

