FROM openjdk:17

MAINTAINER Tom "swhors@naver.com"

ENV API_HOME $HOME/

RUN mkdir -p $API_HOME
ADD ./target/AzureVMManualScaleOutPlanner-0.0.1.jar $API_HOME

WORKDIR $API_HOME

EXPOSE 8080

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
