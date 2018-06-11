package cn.edu.hust.proxy.qProxy;

public class GameProxy implements GameTask {
    private GameTask gameTask;
    public  GameProxy(GameTask gameTask)
    {
        this.gameTask=gameTask;
    }
    public void login(String name, String passwd) {
        this.gameTask.login(name,passwd);
    }

    public void killBoss() {
        this.gameTask.killBoss();
    }

    public void upgade() {
        this.gameTask.upgade();
    }

    public GameTask getProxy() {
        return this;
    }
}
