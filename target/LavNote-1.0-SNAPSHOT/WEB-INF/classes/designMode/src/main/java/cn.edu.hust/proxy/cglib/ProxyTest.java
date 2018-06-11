package cn.edu.hust.proxy.cglib;

import java.util.LinkedList;
import java.util.List;

public class ProxyTest {
    public static void main(String[] args)
    {
        GamePlayer player=new GamePlayer("zhangSan");
        GamePlayerCGLiB  gamePlayerCGLiB=new GamePlayerCGLiB();
        GamePlayer player1=(GamePlayer) gamePlayerCGLiB.getInstance(player);
        player1.login("张三","123");
        player1.killBoss();
        player1.upgade();
        List<Integer> result=new LinkedList<Integer>();
        result.add(10);
        result.add(20);
        int[] t=new int[result.size()];
        for(int i=0;i<result.size();i++)
        {
            t[i]=result.get(i);
        }

    }
}
