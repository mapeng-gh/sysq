# 项目说明文档
---

## APK签名 

`由于开发初期使用的是eclipse默认的debug.keystore，所以之后一直沿用`

但是请尽量使用以下命令生成项目自己的签名

```
keytool -genkey -dname "CN=map,OU=huasheng,O=huasheng,L=haidian,ST=bj,C=CN" -alias sysq -keyalg RSA -keysize 1024 -validity 3650 -keypass sysq123 -keystore sysq.keystore -storepass admin123
```
## 系统更新

* 系统成功登陆后会进行版本检查；
* 系统更新前会校验是否有进行中的访谈或者未上传的访谈；
* 程序更新或者问卷更新请修改version.properties中对应的版本号；

## ChangeLog

|发布日期|app更新|问卷更新|更新内容|
|:---:|:---:|:---:|---|
|2018-05-10|v1.4.0-6|v5|1、添加多媒体备份 2、添加关于|



