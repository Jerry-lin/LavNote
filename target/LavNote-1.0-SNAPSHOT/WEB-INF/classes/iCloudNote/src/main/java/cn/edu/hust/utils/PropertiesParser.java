package cn.edu.hust.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesParser {
    private static Properties properties;
    //加载配置文件
    static
    {
        InputStream is=PropertiesParser.class.getClassLoader().getResourceAsStream("properties/info.properties");
        properties=new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户模块相关信息输出
     *
     */



    /**
     * 邮件的相关配置
     *
     */
    public static String getSmtpServer() {
        return properties.getProperty(Constant.SMTP_SERVER);
    }

    public static String getSmtpProtocol()
    {
        return properties.getProperty(Constant.SMTP_PROTOCOL);
    }

    public static String getSmtpUser() {
        return properties.getProperty(Constant.SMTP_USER);
    }

    public static String getSmtpPwd() {
        return properties.getProperty(Constant.SMTP_PWD);
    }

    public static String getFromAddress() {
        return properties.getProperty(Constant.FROM_ADDRESS);
    }

    public static String getCopy2Send()
    {
        return properties.getProperty(Constant.COPY_TO_SEND);
    }

    public static String getAdminEmail()
    {
        return properties.getProperty(Constant.AMDIN_EMAIL);
    }

    public static String getEmailRegisterTitle() { return properties.getProperty(Constant.EMAIL_REGISTER_TITLE_INFO);};

    public static String getEmailExistsInfo()
    {
        return properties.getProperty(Constant.EMAIL_EXISTS_INFO);
    }
    public static String getEmailNOTExistsInfo()
    {
        return properties.getProperty(Constant.EMAIL_NOT_EXISTS_INFO);
    }

    public static String getResetEmailInfo() {
        return properties.getProperty(Constant.RESET_EMAIL_INFO);
    }
}
