## 简介

**Admin Server** 是一个通用管理后台开发脚手架。主要包含用户管理、RBAC权限控制、生产部署脚本等基础特性。
> 采用DDD架构及CQRS开发理念。
> golang 版本同步更新中 https://github.com/huangdengfeng/go-admin-server

## 运行截图

**登录**
![login.png](doc%2Fpictures%2Flogin.png)
**首页**
![index.png](doc%2Fpictures%2Findex.png)
**用户**
![user.png](doc%2Fpictures%2Fuser.png)
**角色**
![role.png](doc%2Fpictures%2Frole.png)

## 技术选型

**Seezoon Stack** 采用当下最前沿前后端的技术栈完成。

后台**主要**框架：

| 框架名称            | 框架地址                                        | 说明           |
|-----------------|---------------------------------------------|--------------|
| Spring Boot3    | https://spring.io/projects/spring-boot      | 自动装配         |
| Spring Security | https://spring.io/projects/spring-security  | 登录及权限控制      |
| Mybatis         | https://mybatis.org/mybatis-3/zh/index.html | 持久层          |
| Springfox       | https://github.com/springfox/springfox      | openAPI 3 文档 |

## 环境准备

- JDK 1.8 + （建议17）
- Maven 3 +
- Node 12 +
- Yarn（安装完node，可用`npm install -g yarn`）
- Mysql 5.7 + (建议8)

## 🚀 快速开始

**代码下载**

可以通过 IDEA `File->New->Project From Version Control `导入，也可以通过命令下载后导入。

> 建议fork 后到自己仓库后再导入，方便后续同步更新。

```
git clone https://github.com/huangdengfeng/admin-server.git
```

### 后台

- **初始化DB脚本**

  脚本见`build/sql/all.sql`

- **配置admin-server**

  在如下配置文件配置**DB**账号密码

  `/src/main/resources/application-local.properties`

### 前台

https://github.com/huangdengfeng/admin-server-web

- **安装依赖并启动**

  ```
  cd admin-server-web
  yarn install
  yarn dev 
  
  # 访问地址
  http://localhost:3002/
  ```

## 生产环境部署

线上目录结构，

```
/data/
│── cert
├── admin-server  # 后台产出物
│   ├── bin
│   ├── conf
│   ├── logs
│   └── work
├── admin-server-web    # 前端产出物
│   ├── assets
│   └── index.html
└── upload-server  文件上传目录，默认使用磁盘文件，使用OSS则不需要.
```

### 😇 手工部署-后台

采用[maven-assembly-plugin](http://maven.apache.org/plugins/maven-assembly-plugin/)
生成构建物，可以直接生成生产部署的目录结构，方便DevOps 集成.

```
cd admin-server
mvn clean package
```

**产出物目录**

`admin-server/target/admin-server`

**只需要维护产出物`conf `目录的`application.properties `即可**，然后就可以发布了。

> 配置文件与环境分离，打包产出物`jar`会排出`resources`目录如下文件：

```
application-local.properties
```

### 😇 手工部署-前台

```
cd admin-server-web
yarn build
```

**产出物**

`admin-server-web/dist` 中文件发布到线上nginx 目录即可，该工程nginx 配置如下，仅供参考。

```
upstream admin-server {
    server 127.0.0.1:8080 max_fails=3 fail_timeout=10s;
}

server {
    listen       80;
    server_name  stack.seezoon.com;
    rewrite ^(.*)$  https://$host$1 permanent;
}

server {
    listen       443;
    server_name  stack.seezoon.com;
    ssl on;
    ssl_certificate   /data/cert/stack.seezoon.com.pem; 
    ssl_certificate_key  /data/cert/stack.seezoon.com.key;
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;

    # api
    location ^~ /api/ {
        proxy_redirect off;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Real-PORT $remote_port;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://admin-server/;
    }
    # 文件服务
    location ^~ /file/ {
        access_log off;
        alias /data/upload-server/;
    }

    # 静态资源
    location / {
        access_log off;
        root /data/admin-server-web/;
        index index.html index.htm;
    }

}
```

> 可选静态资源压缩配置，放在`nginx.conf http` 节点下。

```
# 打开gzip 效果更佳
gzip on;
gzip_min_length 1k;
gzip_buffers 4 16k;
gzip_http_version 1.0;
gzip_comp_level 6;
gzip_types text/plain application/javascript application/x-javascript text/css application/xml text/javascript application/x-httpd-php image/jpeg image/gif image/png;
gzip_vary off;
gzip_disable "MSIE [1-6]\.";
```

