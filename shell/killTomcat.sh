var=`ps -ef|grep tomcat_8080|grep java`
#echo ${var#*//} | tr -cd "[0-9]"
#echo ${var#wise} | tr -cd "[0-9]"
pid=${var:4:15}
echo tomcat_8080_pid=${pid}
echo "kill this process(y/n):$1"
read choose	# 获取键盘输入
if [[ $choose == 'Y' || $choose == 'y' ]];#当输入为y或Y是覆盖
 then kill -9 ${pid}
else
 echo 'quit!'
fi
