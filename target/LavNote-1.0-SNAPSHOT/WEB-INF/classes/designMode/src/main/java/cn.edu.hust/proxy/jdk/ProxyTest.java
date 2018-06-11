package cn.edu.hust.proxy.jdk;

public class ProxyTest {
    public static void main(String[] args)
    {
        GamePalyer player=new GamePalyer("张三");
        ClassLoader classLoader=player.getClass().getClassLoader();
        Class<?>[] interfaces=player.getClass().getInterfaces();
        GameTask gameTask= GameProxy.invoke(classLoader,interfaces,player);
        gameTask.login("张三","123");
        gameTask.killBoss();
        gameTask.upgade();
    }
}
