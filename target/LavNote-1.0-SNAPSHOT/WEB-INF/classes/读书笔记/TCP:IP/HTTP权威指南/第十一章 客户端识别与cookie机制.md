# 第十一章 客户端识别于cookie机制

## 11.1个性化接触

站点的个性化

1.个性化的问候

2.有的放矢的推荐

3.管理信息的存档

4.记录安全回话

## 11.2HTTP首部

HTTP请求首部

![image-20180509090052596](/Users/youyujie/Documents/读书笔记/TCP:IP/HTTP权威指南图片/HTTP请求首部.png)

## 11.3客户端IP地址

客户端IP地址识别用户的缺点：

1.客户端IP地址描述的是所有的机器。不是用户，有可能多个用户共享一台计算机

2.很多服务提供商都会在用户登录时动态分配IP地址

3。提高安全性，通过NAT来浏览网页

## 11.4用户登录

HTTP中包含了一种内建机制，可以用www-Aythenticate首部和Authorization首部向Web站点传输用户的相关信息。

## 11.5胖URL

有些Web站点会为每一个用户生成特定版本的URL来追踪用户的身份，通常是对真正的URL进行扩展，包含一些状态信息，改动后的URL成为胖URL。

技术问题：

1.丑陋的URL

2.无法共享URL

3.破坏缓存

4.额外的服务器负载

5.逃逸口

6.回话间非持久

## 11.6cookie

cookie时当前识别用户，实现持久会话的最好方式。

### 11.6.1cookie的类型

cookie分为两类：会话cookie和持久cookie

会话cookie时一种临时cookie，用户就会删除

持久cooki存储在看硬盘，计算机重启时仍然存在

唯一区别是过期时间

### 11.6.2cookie是如何工作的

cookie中包含一个由名字=值这样的信息构成的任意列表，并通过Set-Cookie或者Set-Cookie2HTTP响应首部将其贴到用户身上去。

### 11.6.3cookie罐：客户端的状态

cookie的基本思想是让浏览器积累一组服务器特有的信息，每次访问服务器都将这些信息提供给它。

1.网景公司的cookie，存储在cookie.txt中

2.ie存储在高速缓存目录下独立文本文件中

### 11.6.4不同的站点使用不同的cookie

浏览器只向服务器发送服务器产生的那些cookie。

**1.cookie的域属性**

通过Set-Cookie响应首部添加一额Domain属性来控制哪些站点可以看到哪个cookie

**2.cookie路径属性**

cookie规范深知允许用户cookie与部分Web站点关联起来。可以通过Path属性来实现这一功能功能，在这个属性列出的URL路径前缀所有的cookie都是有效的。

### **11.6.5cookie成分**

cookie有两个版本，第一个是cookies版本0，第二个是cookie版本1

cookie的规范：

![image-20180509094647744](/Users/youyujie/Documents/读书笔记/TCP:IP/HTTP权威指南图片/Cookie规范.png)

### 11.6.6cookies版本0

定义了Set-Cookie响应首部、cookie请求首部以及用于控制cookie的字段。

![image-20180509095153783](/Users/youyujie/Documents/读书笔记/TCP:IP/HTTP权威指南图片/版本0的cookie.png)

1.版本0的Set-cookie首部

有一个强制性的cookie名和cookie值，后面跟着可短的cookie属性，由分号隔开

2.版本0的cookie首部

将所有与域、路径和安全多虑期相匹配的国旗cookie都发送给这个站点。所有的cookie将会被 组合到一个cookie首部：

![image-20180509095548774](/Users/youyujie/Documents/读书笔记/TCP:IP/HTTP权威指南图片/版本为0的cookie首部.png)

### 11.6.7cookies版本1

引入set-Cookies2首部和Cookies首部，能与版本0系统进行互操作。

1.版本1的Set-cookie2首部

![image-20180509095937019](/Users/youyujie/Documents/读书笔记/TCP:IP/HTTP权威指南图片/版本为1的Set-Cookie2的首部.png)

2.版本1的cookie首部

带回与传送的每个cookie相关的附加信息，用来描述每个cookie途径的过滤器。

3.版本1的cookie2首部和版本协商

Cookie2请求首部负责能够理解不同cookie规范版本的客户端和服务器之间的进行互操作性的协商。

### 11.6.8cookie与会话跟踪

可以用cookie在用户与某个web站点进行多项事务处理时对用户进行跟踪。

### 11.6.9cookie与缓存

1.如果缓存文档，将其标示出来

2.缓存Set-Cookie首部要小心

3.小心处理带有cookie首部请求

### 11.6.10cookie、安全性和隐私



