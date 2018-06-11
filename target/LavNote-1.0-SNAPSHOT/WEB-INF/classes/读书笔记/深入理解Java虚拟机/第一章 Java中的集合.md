# 第一章 Java中的集合

## 1.1HashMap

### 1.1.1特点

线程不安全，允许key和value为null

### 1.1.2结构

![image-20180514091503925](/Users/youyujie/Documents/读书笔记/深入理解Java虚拟机/HashMap结构图.png)

主要分为两个部分，第一是Entry数组，第二个是每一个数组元素，维护一个链表，用于解决Hash冲突。

### 1.1.3如何Hash

```java
static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```

从代码可以看出，如果key为null，那么就放置在下表为0的位置，如果不为null，那么就酱hashcode的低16位与高16位相与

### 1.1.4get()操作

首先可以查看源码：

```java
 final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```

这里是基于jdk1.8，get方法主要是首先使用key的HashCode进行定位，然后在检查数组，也就是第一个元素，如果找到了，那么就直接返回，如果找到，那么就要判断是否是TreeNode点，如果是，那么就使用就使用二叉查找，如果不是TreeNode类型，那么就遍历链表。这里，链表中的存储的是Node，需要注意的是hashCode值一定，还需要比较内容是否一致。

TreeNode的实现如下：

```java
static final class TreeNode<K,V> extends LinkedHashMap.Entry<K,V> {
        TreeNode<K,V> parent;  // red-black tree links
        TreeNode<K,V> left;
        TreeNode<K,V> right;
        TreeNode<K,V> prev;    // needed to unlink next upon deletion
        boolean red;
        TreeNode(int hash, K key, V val, Node<K,V> next) {
            super(hash, key, val, next);
        }

        /**
         * Returns root of tree containing this node.
         */
        final TreeNode<K,V> root() {
            for (TreeNode<K,V> r = this, p;;) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

```

TreeNode是一个红黑树，红黑树是一颗平衡二叉树，具体的细节，我们会在有关的博客上进行介绍。

具体的，我们可以看一下Node的内部实现

```java
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;

        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }
```

Node是HashMap的一个静态内部类，内部属性有HashCode，key，value，下一个Node点。

### 1.1.5Put()方法

过程是：

1.使用Key的HashCode&（length-1）定位，如果每一个这个元素那么就new出一个新的节点，否则进入步骤2

2.定位到具体的位置，如果是TreeNode，那么就使用putTreeVal()方法进行查找，否则使用Node进行遍历查找，最后就是新的值替换老的值

3.判断新插入后，是否需要扩容。

具体的代码如下：

```java
 final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
     	//如果是新的节点
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            //检查第一个点，是否是输入的值
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //判断是否是TreeNode
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            //遍历查询
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //新的值替换老的值
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                //回调函数，具体实现为空
                afterNodeAccess(e);
                return oldValue;
            }
        }
     	//插入节点后的预期值，判断是否需要扩容
        ++modCount;
        if (++size > threshold)
            resize();
     //回调函数，具体实现为空
        afterNodeInsertion(evict);
        return null;
    }
```

这里有一点，需要特别注意的是，如果Node的个数大于8，那么将会将链表转化为红黑树。

### 1.1.6如何扩容

具体的实现代码如下：

```java
final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            //这里扩容，称为原来的两倍
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    //计算新的位置
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        //重建红黑树
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }
```

首先判断现有的数组大小是否大于2的30次方，如果大于，那么就不会扩容了，否则就变成原来的两倍，然后将原理的元素通过hashcode确定新的位置。如果是红黑树，那么就将重建红黑树。为什么需要扩容两倍？减小了计算，原来的Node，要么在原来的索引位置，要么是原来的索引加上oldCap；

### 1.1.7有哪些优化

这里HashMap底层数组都是2的n次方，那么在确定位置时，采用hashcode&(length-1)这样相当于对length取模，但是这里&比%效率高，这是一个优化

对于将链表改成红黑树，这也是优化

## 1.2ArrayList

### 1.2.1特点

线程不安全，动态数组实现

### 1.2.2结构

![image-20180514104351762](/Users/youyujie/Documents/读书笔记/深入理解Java虚拟机/ArrayList解析.png)

### 1.2.3实现接口

实现了哪些接口

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

可以看出ArrayList实现了List RandomAccess Cloneable,等结构，可以实现随机访问，对于它的构造器

```java
1.
public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }
2.
 /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
3.
 public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }
```

有三种不同的构造器，如果没有传递参数，使用默认的大小10，如果传递的集合参数，那么使用Arrays.copyOf()来进行复制数组。

### 1.2.4如何扩容

扩容的具体函数如下

```java
 private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

可以看出，扩容的大小事原来的1.5倍，如果大于最大值Integer.MAX_VALUE，那就是Integer.MAX_VALUE。

### 1.2.5使用场景

适合查找特别多，删除少的场景

## 1.3LinkedList

### 1.3.1特点

双向链表，适合增删多的场景，非线程安全。

### 1.3.2实现接口

实现的结构如下

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

实现了List<E>, Deque<E>, Cloneable，可以看出这里实现了一个双端队列

构造方法

```java
 public LinkedList() {
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param  c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }
```

支持的方法：由于是双向队列，那么自然队列队尾的操作

```java
public E removeFirst() {
        final Node<E> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst(f);
    }

public E removeLast() {
        final Node<E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast(l);
    }
  public void addFirst(E e) {
        linkFirst(e);
    }
public void addLast(E e) {
        linkLast(e);
    }

```

## 1.4Vector

### 1.4.1特点

遗留集合，线程安全，数组实现，效率低下

### 1.4.2get()

```java
 public synchronized E get(int index) {
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);

        return elementData(index);
    }
```

使用synchronized方法实现同步，使用索引定位

### 1.4.3add()

```java
 public synchronized boolean add(E e) {
        modCount++;
        ensureCapacityHelper(elementCount + 1);
        elementData[elementCount++] = e;
        return true;
    }

```

在数组的末尾添加元素，当然需要比较添加后的数组大小

### 1.4.4如何扩容

```java
 private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                         capacityIncrement : oldCapacity);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

这是扩容的主要函数，扩容的大小由初始化决定

如果构造器传入了capacityIncrement，那么根据传入的参数，否则扩容一倍。默认数组大小为10

## 1.5Stack

### 1.5.1特点

线程不安全，基于Vector实现

### 1.5.2哪些方法

处理继承特有的方法以外，还有stack特有的方法，例如push()、pop()





