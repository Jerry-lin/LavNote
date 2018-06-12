###数据库表格
CREATE DATABASE LavNote;
MYSQL用户数据表
CREATE TABLE User(
id VARCHAR(128) PRIMARY KEY ,
firstname VARCHAR(20),
lastname VARCHAR(20),
password VARCHAR(128)
);


HBase的表
Create "NoteBook",""