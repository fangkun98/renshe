FROM java:8
VOLUME /tmp
ENV TZ "Asia/Shanghai"
ENV LANG C.UTF-8
ADD target/renshe-0.0.1.jar renshe.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/renshe.jar"]