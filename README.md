# WOIM

#### Introduction
**WOIM**是一个基于Netty、SpingBoot开发的**分布式**、**嵌入式**、**组件化**、**高度自定义**、**高可扩展**的Java即时通讯框架，可以轻松嵌入你**自己的**用户系统，以提供即时通讯服务,同时也可作为消息推送系统，成为连接两端的桥梁。

#### Features

1. **分布式**

   支持分布式集群部署，单机无法满足用户量需求时可水平扩展。

2. **嵌入式**

   只需实现自己的连接鉴权逻辑，便可非侵入式地嵌入已有的用户系统。

3. **组件化**

   从连接管理到消息处理通道再到业务逻辑都是可拆卸、组装的。

4. **高度自定义**

   非侵入式，支持二次开发和功能集成，对于内置默认的各个组件以及业务逻辑可进行替换及自定义开发，你可以通过WOIM搭建最适合你自己的即时通讯服务。

5. **高可扩展**

   内置了序列化和反序列化逻辑，并集成了protobuf进行高效传输，真正的消息类型自定义，配置了相应的处理逻辑后，你便可以向客户端推送任何自定义的消息类型，包括但不限于私聊、群聊消息、系统推送。

6. **完善的IM功能**

   内置了私聊、群聊、离线消息、会话管理等一系列功能（出于对隐私的尊重，没有内置漫游消息的功能，**但支持二次开发实现**），支持多种消息类型的扩展，支持多端登录。

#### Quick Start

- **初始化数据库表格**

  - 方法一：配置文件中配置

    默认为NONE模式，不进行数据库的初始化，可选模式有NEW（删除旧表，创建新表），SAFE（不存在则创建）模式

    ```yaml
    woi:
      woim:
        db-initialization-mode: SAFE
    ```

  - 方法二：执行sql脚本

     执行提供的sql脚本

- **依赖引入**

  ***本项目暂未上传至中央仓库，可clone后安装到本地maven仓库***

  > 服务端

  **maven**

  ```xml
      <dependency>
          <groupId>zone.czh</groupId>
          <artifactId>woi-woim</artifactId>
          <version>1.0.0</version>
      </dependency>
  ```

  > 客户端

  **maven**

  ```xml
      <dependency>
          <groupId>zone.czh</groupId>
          <artifactId>woi-woim-client</artifactId>
          <version>1.0.0</version>
      </dependency>
  ```

  **gradle**

  ```gra
      implementation 'zone.czh:woi-woim-client:1.0.0'
  ```

  

- **使用及开发**

  详见example模块

  > 服务端

  - **快速开发**

  1. 注解开启WOIM

     ```java
     @SpringBootApplication
     @EnableWOIM //todo enable woim
     public class WoiWOIMExampleApplication {
         public static void main(String[] args) {
             SpringApplication.run(WoiWOIMExampleApplication.class, args);
         }
     }
     ```

  2. 实现WOIMConfigurator

     ```java
     //or implements WOIMConfigurator
     @Component
     public class ExampleConfigurator extends SimpleWOIMConfigurator {
         @Override
         public WOIMVerifier getChannelVerifier() {
             //todo 配置自己的连接授权方法，适配自己的用户系统
             return null;
         }
     }
     ```

  - **集群**

  1. 编写配置文件

      ```yaml
      woi:
        woim:
          server-addr: 127.0.0.1:8080,127.0.0.1:8081,127.0.0.1:8082
      ```
      
      

  

  > 客户端

  - **快速开发**

    ```java
    //init client
    WOIMClient client = new WOIMClient.Builder().setInetHost("127.0.0.1")
                    .setPort(6666)
                    .setEventListener(new CustomEventListener())
                    .setConfigurator(new CustomConfigurator())
                    .build();
    //auth
    client.authSafely("token",null,null);
    //send message
    client.push(new PrivateMsg());
    ```

#### Document

暂无详细文档，可参考example模块

#### Screenshot

> 安卓端

（自己写的一个基于本框架开发的安卓端app，条件合适时可以考虑开源）



<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/android_client/1.jpg" alt="image" height=500px />
<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/android_client/2.jpg" alt="image" height=500px />
<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/android_client/3.jpg" alt="image" height=500px />
<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/android_client/4.jpg" alt="image" height=500px />
<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/android_client/5.jpg" alt="image" height=500px />
<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/android_client/6.jpg" alt="image" height=500px />
<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/android_client/7.jpg" alt="image" height=500px />



> 桌面端

暂时没做图形界面的桌面端

<img src="https://gitee.com/woi/res/raw/main/woim/screenshot/desktop_client/1.jpg" alt="image" height=300px />


#### Contacts

Email：woimail@163.com