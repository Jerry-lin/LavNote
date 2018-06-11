# 第四章 Schema与类型优化

## 4.1选择优化的数据类型

几个简单原则

**更小的通常更好**

**简单就好**

**尽量避免NULL**

### 4.1.1整数类型

​	TINYINT、SMALLINT、MEDIUMINT、INT、BIGTINT 分别是8，16，24，32，64位

​	MySQL可以指定整型宽度

### 4.1.2实数类型

​	FLOAT和DOUBLE类型，CPU支持FLOAT，所以使用这个运算跟快，

### 4.1.3字符串类型

​	varchar和char，

​	varchar可变长比定长省空间，1或者2个字节存储字符串长度

​	char是定长的，适合存储短字符串



​	