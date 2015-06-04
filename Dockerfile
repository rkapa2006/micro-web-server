#Clones the repo from https://github.com/rkapa2006/micro-web-server.git
#Builds micro-web-server Application using mvn clean install
#Then runs micro-web-server Application using Jenkins

#start from docker machine base image
FROM rkapa2006/dockermachine:0.1

MAINTAINER Ramesh B Kapa (rkapa2006@yahoo.com)

#Clone git project
RUN git clone https://github.com/rkapa2006/micro-web-server.git

#change to micro-web-server Directory
WORKDIR micro-web-server

RUN pwd

#Build EmiCalculator Maven Project
RUN mvn clean install

#Build Maven Assembly
RUN mvn assembly:single

RUN cp target/micro-web-server-1.0.tar.gz /root/

WORKDIR /root

RUN pwd

RUN rm -rf micro-web-server

RUN tar xzf  micro-web-server-1.0.tar.gz

RUN rm -rf micro-web-server-1.0.tar.gz


WORKDIR micro-web-server

#Expose the port 8080 on which micro web server is listening for requests
EXPOSE 8080

#Run micro-web-server
CMD ./bin/start.sh 
