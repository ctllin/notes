#!/bin/sh  
myPath="/root/apache-tomcat-8.5.20/warsback/"  
war1="/root/apache-tomcat-8.5.20/airbanksso.war"  
war2="/root/apache-tomcat-8.5.20/airshop.war"  
war3="/root/apache-tomcat-8.5.20/apiairbank.war"  
#这里的-x 参数判断$myPath是否存在并且是否具有可执行权限  
if [ ! -x "$myPath" ]; then  
    echo $myPath'不存在创建该文件夹'
    mkdir $myPath 
fi  
#这里的-f参数判断$war1是否存在 （文件） 
if [ ! -f "$war1" ]; then  
    echo "$war1不存在退出!" 
    exit 0
fi  
if [ ! -f "$war2" ]; then  
    echo "$war2不存在退出!" 
    exit 0
fi 
if [ ! -f "$war3" ]; then  
    echo "$war3不存在退出!" 
    exit 0
fi  
warname=_back_`date '+%Y%m%d%H%M%S'`.war
cp webapps/airbanksso.war warsback/airbanksso$warname && cp webapps/airshop.war warsback/airshop$warname && cp webapps/apiairbank.war warsback/apiairbank$warname
if [ "$?" = "0" ]; then	#判断上面步骤是否执行成功
   echo 'war包备份成功名称：*'$warname
   #按时间排序查看第二个到第四个
   ls -tl warsback/|head -4|tail -3
else
   echo "war包备份失败" 1>&2
   exit 1
fi
echo '执行war包覆盖(y/n)?'
read choose	# 获取键盘输入
if [[ $choose == 'Y' || $choose == 'y' ]];	#当输入为y或Y是覆盖
 then mv airbanksso.war  webapps/ && mv airshop.war  webapps/   &&      mv apiairbank.war  webapps/
else
 echo '退出!'
fi
