FROM tomcat:9.0-jdk11

RUN rm -rf /usr/local/tomcat/webapps/ROOT /usr/local/tomcat/webapps/examples /usr/local/tomcat/webapps/docs /usr/local/tomcat/webapps/manager /usr/local/tomcat/webapps/host-manager

RUN mkdir -p /usr/local/tomcat/webapps/lab9/WEB-INF/classes

COPY src/main/java/ /build/
RUN cd /build && javac -cp /usr/local/tomcat/lib/servlet-api.jar *.java && cp *.class /usr/local/tomcat/webapps/lab9/WEB-INF/classes/

COPY src/main/webapp/WEB-INF/web.xml /usr/local/tomcat/webapps/lab9/WEB-INF/
COPY src/main/webapp/*.html /usr/local/tomcat/webapps/lab9/

EXPOSE 8080
CMD ["catalina.sh", "run"]
