package cn.edu.hust.Singleton;
public class Student {
    //懒汉模式，只有调用getInstance方法的时候才能初始化instance，线程不安全
    private static Student instance=null;
    private Student()
    {

    }
    public static Student getInstance()
    {
        if(instance==null)
        {
            instance=new Student();
            return  instance;
        }
        return instance;
    }
}

class Teacher
{
    //饿汉模式，在类加载机制的时候就对对对象进行实例化，线程安全
    private static Teacher instance=new Teacher();
    private Teacher()
    {

    }
    public static  Teacher getInstance()
    {
        return instance;
    }
}
