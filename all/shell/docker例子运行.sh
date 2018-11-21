ps -fe|grep docker-containerd  |grep -v grep
if [ $? -ne 0 ]
then
echo "docker is stop"
else
echo "docker is runing....."
fi
#####
#docker-containerd 表示进程特征字符串，能够查询到唯一进程的特征字符串
#0表示存在的
#$? -ne 0 不存在，$? -eq 0 存在
#####


count=`ps -ef | grep docker-containerd | grep -v "grep" | wc -l`
echo "docker相关进程数量：$count"
if [ $count -gt 0 ]; then
 echo "docker is running." 
else
 echo "start docker ......"
 service docker start
 echo "docker have satart."
fi
# Now run the build command. This creates a Docker image, which we’re going to tag using -t so it has a friendly name.
echo 'docker build -t friendlyhello .'

# Where is your built image? It’s in your machine’s local Docker image registry:
echo 'docker images'
docker images

# Run the app, mapping your machine’s port 80 to the container’s published port 80 using -p:
echo '在第一个端口80为访问端口-d代表在后台运行'
echo 'docker run -d -p 80:80 friendlyhello'
docker run -d -p 80:80 friendlyhello

echo 'docker container ls'
docker container ls
echo 'docker container ls|grep friendlyhello'
containerIdLine=$(docker container ls|grep friendlyhello)

#截取12个
containerId=${containerIdLine:0:11}
echo ${containerId}
# '不显示变量 "显示变量
echo 'docker container stop ${containerId}'
echo "docker container stop ${containerId}"
echo '关闭friendlyhello对应的容器(y/n)?'
read choose     # 获取键盘输入
if [[ $choose == 'Y' || $choose == 'y' ]];      #当输入为y或Y是覆盖
 then docker container stop ${containerId}
else
 echo '退出!'
fi


