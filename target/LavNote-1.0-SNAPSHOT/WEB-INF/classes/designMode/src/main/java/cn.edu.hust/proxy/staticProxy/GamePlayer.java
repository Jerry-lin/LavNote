package cn.edu.hust.proxy.staticProxy;

public class GamePlayer implements GameTask {
    private String name;
    private String passwd;

    public GamePlayer() {
    }


    public GamePlayer(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public void login(String name, String passwd) {
        System.out.println(name+" 登录成功..");
    }

    public void killBoss() {
        System.out.println(name+" 正在杀boss。。。");
    }

    public void upgade() {
        System.out.println(name +" 升级成功。。。。");
    }
}
