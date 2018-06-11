# 第七章 Java中的13个原子操作类

4中类型的原子更新方式，分别是原子更新基本类型、原子更新数组、原子更新引用和原子更新属性。主要使用Unsafe实现的包状态类。

## 7.1原子更新基本类型

AtomicBoolean：原子更新布尔类型

AtomicInteger：原子更新整型

AtomicLong：原子更新长整型

以AtomicInteger进行讲解：

int addAndGet(int delta):以原子方式将输入的数值与实例中的值相加，并返回结果。

boolean compareAndSet(int expect,int update):如果输入的数值等于预期的值，则以原子方式将该值设置为输入的值。

Int getAndIncrement():以原子方式将当前值注意这是返回的是自增前的值。

void lazySet(int newValue):最终设置为newValue，使用lazySet设置后，可能导致其他线程之后的一段时间还会可以读到旧的值。

int getAndSet(int new Value):以原子方式设置为new Value的值，并返回旧值。

![image-20180521205051384](/Users/youyujie/Documents/读书笔记/并发编程的艺术/AtomicInteger.png)

如何实现原子操作呢？

第一步是取得AtomicInteger存储的数值，第二部对AtomicInteger的当前数值加1，第三步是调用compareAndSet方法进行原子更新操作，该方法先检查当前数值是否等于current，等于意味着没有被改过，就可以更新，否则进入循环更新执行compareAndSet操作。

Atomic包中的类都是使用Unsafe实现，可以看一下源码

![image-20180521205543889](/Users/youyujie/Documents/读书笔记/并发编程的艺术/unsafe方法.png)

都是将Boolean转换成整型，使用CompareAndSwapInt进行CAS所以原子更新char、float和double变量可以使用类似思路实现。

## 7.2原子更新数组

提供更新4个类

AtomicIntegerArray：原子更新整型数组的元素

AtomicLongArray：原子更新长整型数组里的元素

AtomicReferenceArray：原子更新引用类型数组里的元素

AtomicIntegerArray常用方法

int addAndGet(int i,int delta)：以原子方式将输入值与数组中的索引i的元素相加

boolean compareAndSet(int i,int expect,int update):如果当前址等于预期值，则以原子方式将数组位置i的元素设置成udpate值

```java
package cn.edu.hust.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class ArrayTest {
    public static void main(String[] args)
    {
        int arr[]={1,2};
        AtomicIntegerArray array=new AtomicIntegerArray(arr);
        array.set(0,3);
        System.out.println(array.get(0));
    }
}

```

需要注意的是通过数组通过构造方式传递，会将原来的数组复制一份，不会影响传入的数组。

## 7.3原子更新引用类型

AtomicReference：原子更新引用类型

AtomicReferenceFieldUodate：原子更新引用类型中的字段

AtomicMarkableReference：原子更新带有标记微的引用类型。可以更新一个布尔类型的标记微和引用类型。

![image-20180521211043175](/Users/youyujie/Documents/读书笔记/并发编程的艺术/atomicRefernece.png)

实现原理同AtomicInteger里的compareAndSet()方法

## 7.4原子更新字段

如果需要原子更新某个字段，需要使用3个类：

AtomicIntegerFieldUpdate:原子更新整型的字段的更新器

AtomicLongFieldUpdate：原子更新长整型字段的更新器

AtomicStampedReference:原子更新带有版本号的类型。该类将整型值与引用关联起来，可用于原子的更新数据和数据版本号，可以解决使用CAS进行原子更新时可能出现ABA问题。

分两步：

第一：因为原子更新字段类搜时抽象类，每次使用时候必须使用静态方法newupdate()创建一个更新器，并且需要设置更新的类核属性。第二步，更新类的字段必须使用public volatile修饰符。

![image-20180521211738506](/Users/youyujie/Documents/读书笔记/并发编程的艺术/原子更新字段测试类.png)