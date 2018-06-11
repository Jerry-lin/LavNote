package cn.edu.hust.proxy.jdk;

public class GamePalyer implements GameTask{
    private String name;

    public GamePalyer(String name) {
        this.name = name;
    }

    public void login(String name, String passwd) {
        System.out.println(this.name+" 登录成功...");
    }

    public void killBoss() {
        System.out.println(this.name+" 杀boss。。");
    }

    public void upgade() {
        System.out.println(this.name+" 升级。。");
    }
}
