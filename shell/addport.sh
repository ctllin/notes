echo "允许访问的端口为：$1";
#变量用""包含
if [ ! -n "$1" ]; then
  echo "未输入端口,退出"
  exit 0
else
  echo 'iptables -I INPUT -p tcp --dport "$1" -j ACCEPT'
  iptables -I INPUT -p tcp --dport $1 -j ACCEPT
  service iptables save
  service iptables restart
  echo '添加允许端口结束'
fi 
