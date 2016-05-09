#! /bin/bash  
rm /Users/xiazhiyang/Documents/apache-tomcat-8.0.26/webapps/wireless.war
rm -r /Users/xiazhiyang/Documents/apache-tomcat-8.0.26/webapps/wireless
cp /Users/xiazhiyang/Documents/java\ project/exp-platform/target/exp-platform-1.0.war /Users/xiazhiyang/Documents/apache-tomcat-8.0.26/webapps/wireless.war

tomcat restart