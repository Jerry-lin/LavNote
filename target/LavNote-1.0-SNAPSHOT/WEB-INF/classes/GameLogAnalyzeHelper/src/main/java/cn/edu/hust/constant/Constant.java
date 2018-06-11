package cn.edu.hust.constant;

public class Constant {
    // redis的常用配置
    public static final String REDIS_IP="10.211.55.16";
    public static final int REDIS_PORT=6379;
    public static final int TIMEOUT=300;
    public static final String PASSWD="root";

    //日志类型
    public static final String LOG_ADMIN_LOGIN="0";
    public static final String LOG_FIRST_LOGIN="1";
    public static final String LOG_LOGIN_IN="2";
    public static final String LOG_LOGIN_OUT="3";
    public static final String LOG_UPGRADE="4";
    public static final String LOG_PLAN="5";
    public static final String LOG_GAMEGOLD="6";
    public static final String LOG_GAMEGOLD2RMB="7";
    public static final String LOG_PK="8";
    public static final String LOG_GROWTASK="9";
    public static final String LOG_TACKAWARD="10";
    public static final String LOG_SUPPOTOR="11";
    public static final String LOG_BUYTHINGS="12";

    //logServer编号定义
    public static final String LOGSERVER_TACKTHIING="0";
    public static final String LOGSERVER_RESOTRETHIING="1";
    public static final String LOGSERVER_PICKUP="4";
    public static final String LOGSERVER_MAKEUP="5";
    public static final String LOGSERVER_DISCARD="7";
    public static final String LOGSERVER_DEAL="8";
    public static final String LOGSERVER_GIVE="9";
    public static final String LOGSERVER_TASK="10";
    public static final String LOGSERVER_USE="11";
    public static final String LOGSERVER_DEADDROP="15";
    public static final String LOGSERVER_KILLED="19";
    public static final String LOGSERVER_UPGRADEARMSUCC="20";
    public static final String LOGSERVER_UPGRADEARMFAIL="21";
    public static final String LOGSERVER_TASKUPGRADEARM="24";
    public static final String LOGSERVER_BEGINUPGRADEARM="25";
}
