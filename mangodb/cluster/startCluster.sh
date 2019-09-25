mkdir -p  /data/mongodb/slave
mkdir -p  /data/mongodb/master
mkdir -p  /data/mongodb/arbiter
./bin/mongod -f ./master.conf
./bin/mongod -f ./slave.conf
./bin/mongod -f ./arbiter.conf

