package cn.edu.hust.proxy.cglib;

public class GamePlayer {
    private String name;

    public GamePlayer() {
    }

    public GamePlayer(String name) {
        this.name = name;
    }

    public void login(String name,String passwd)
    {
        System.out.println(name+" 登录成功。。。");
    }

    public void killBoss()
    {
        System.out.println(this.name+" 正在杀boss。。。");
    }

    public void upgade()
    {
        System.out.println(this.name+" 升级。。。。");
    }
}
