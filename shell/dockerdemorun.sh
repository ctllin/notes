ps -fe|grep docker-containerd  |grep -v grep
if [ $? -ne 0 ]
then
echo "start process....."
else
echo "runing....."
fi
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

#docker run -p 80:80 ctllin/web:test00001 (注册账户ctllin,创建仓库web  然后执行docker login)
echo "Tag the image"
echo "docker tag friendlyhello ctllin/web:test00001"
echo "Publish the image"
echo "docker push riendlyhello ctllin/web:test00001"
echo "docker run -p 80:80 ctllin/web:test00001"
#导出本地镜像
#docker save -o friendlyhello.tar friendlyhello:latest  导出镜像到本地文件
# scp -p 22 friendlyhello.tar root@192.168.42.7:/root/soft
#docker load --input friendlyhello.tar (或者docker load < frrendlyhello.tar ) 导入镜像
#删除容器 docker rm 021e6458c6f7 如果镜像无法删除可能是因为没有删除容器
#如果要移除本地的镜像，可以使用 	docker	rmi	命令。注意 	docker	rm	命令是移除容器。
#docker rmi imagename for example : docker rmi friendlyhello
#docker rmi ctllin/web:test00001    直接删除ctllin/web无法删除需要带上tag(test00001)
#打日志  docker container ls 获取names  docker logs name(来自names)



#curl -L https://github.com/docker/compose/releases/download/1.19.0-rc3/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
#chmod +x /usr/local/bin/docker-compose

#docker swarm init  --advertise-addr  192.168.42.29  服务器有多个地址需要 指定地址 (集群 访问192.168.42.29和nginx负载类似)
#docker stack deploy -c docker-compose.yml getstartedlab
#Get the service ID for the one service in our application:
#docker service ls
#List the tasks for your service:
#docker service ps getstartedlab_web
#Tasks also show up if you just list all the containers on your system, though that is not filtered by service:
#docker container ls -q
#Take the app down with docker stack rm:
#docker stack rm getstartedlab
#Take down the swarm.
#docker swarm leave --force

#Take the app down with docker stack rm:
#docker stack rm getstartedlab
#Take down the swarm.
#docker swarm leave --force

#集群
# docker swarm init  --advertise-addr  192.168.42.29   --listen-addr 192.168.42.29:2377  主
# docker swarm join --token SWMTKN-1-3h8j1tx40po8gqfepa8nql54iq34qg7bn14me97vto13pwz5tt-a7sayzxszxnw5tf7i0cxtfeiv 192.168.42.29:2377  (192.168.42.7 从执行)
#docker stack deploy --with-registry-auth -c docker-compose.yml getstartedlab  #启动集群然后使用  docker container ls 在多个服务器上执行该命令得到containers的总数等于设置的负载数量
#You can tear down the stack with docker stack rm. For example:
#docker stack rm getstartedlab 停止集群

#运行docker-compose2.yml 之前需要先执行   docker run dockersamples/visualizer:stable

