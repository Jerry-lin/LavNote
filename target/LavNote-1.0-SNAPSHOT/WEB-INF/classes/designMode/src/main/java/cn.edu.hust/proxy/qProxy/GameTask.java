package cn.edu.hust.proxy.qProxy;

public interface GameTask {
    void login(String name,String passwd);
    void killBoss();
    void upgade();
    GameTask getProxy();
}
