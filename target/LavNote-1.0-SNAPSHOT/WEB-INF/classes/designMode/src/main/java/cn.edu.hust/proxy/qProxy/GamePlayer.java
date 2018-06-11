package cn.edu.hust.proxy.qProxy;


public class GamePlayer implements GameTask {
    private String  name;
    private GameProxy gameProxy=null;

    public GamePlayer(String name) {
        this.name = name;
    }

    private boolean isProxy()
    {
        if(gameProxy==null)
            return false;
        else
            return true;
    }

    public void login(String name, String passwd) {
        if(!isProxy())
        {
            System.out.println("请使用代理类。。。。");
        }
        else
            System.out.println(name+" 登录成功。。。");
    }

    public void killBoss() {
        if(!isProxy())
            System.out.println("请使用代理类 。。。。");
        else
            System.out.println(name+" 正在杀boss。。");
    }

    public void upgade() {
        if(!isProxy())
        {
            System.out.println("请使用代理类...");
        }
        else
            System.out.println(name+"升级成功");
    }

    public GameTask getProxy() {
        gameProxy=new GameProxy(this);
        return gameProxy;
    }
}
