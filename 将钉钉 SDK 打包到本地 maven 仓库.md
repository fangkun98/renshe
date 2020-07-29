## 将钉钉 SDK 打包到本地 maven 仓库

sdk.jar 存放在 resource/lib 文件夹下

将本地 jar 打包到 maven 本地仓库的命令：

**将sdk.jar保存到本地仓库**
``` powershell
mvn install:install-file -Dfile=taobao-sdk-java-auto_1479188381469-20200331.jar -DgroupId=com.taobao -DartifactId=dingding -Dversion=1.0 -Dpackaging=jar
```
**将lippi保存到本地仓库**
```powershell
mvn install:install-file -Dfile=lippi-oapi-encrpt.jar -DgroupId=com.taobao.lippi -DartifactId=dingding.lippi -Dversion=1.0 -Dpackaging=jar
```



