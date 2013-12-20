#!/bin/bash -xe
 
#THIS SCRIPT MUST BE RUN AS ROOT
 
ADMIN_USER=admin
ADMIN_GROUP=admin
 
#add admin group
# (cat /etc/group | grep -E '\b$ADMIN_GROUP\b') || sudo groupadd $ADMIN_GROUP
 
#add admin user
# (cat /etc/passwd | grep -E "\b$ADMIN_USER\b:x") || useradd -m -s /bin/bash $ADMIN_USER -g $ADMIN_GROUP
 
#sudoless access for admin user
# (cat /etc/sudoers | grep -E "^$ADMIN_USER\b.*NOPASSWD") || echo "$ADMIN_USER ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers
 
#configure SSH
# SSH_KEY="ssh-rsa AAAAB3NzaC1yc2EAAAABIwAAAQEAuwaDITgrsmxVvSJ+HEir4iW2eBb8XqB3wIxCWJwmhiB2S6MkdX1CFcj8boHL2RIYgKN0yiTCVlOdQFjhyRpnd8NbS0RW7753SE1hzkgOg+2e1B15lf1PCSD5OPWAtpb1eYlLcs6hns+yFhYCFeQrKLBL2/si8D8OcQcTR7BjjFQEWdhjl4cYiT68cr57yFT+c/f32ZTynEWukC2YIGMjJ1nACUOYF8CSp6RUrD2kY3C1Pb1Q5V3jwi0lhay4aF5AWp28hPVtYASBe2BrYXp8uErhnmWxgXNV19P0NSHE7TBXd9nXz/njI2SDP/p2N/vHquz/Ybxs4F+kGGXknUFitQ== cmaitchison@gmail.com"
# SSH_DIR=/home/$ADMIN_USER/.ssh 
# mkdir -p -m 700 $SSH_DIR
# echo $SSH_KEY > $SSH_DIR/authorized_keys
# chmod 600 $SSH_DIR/authorized_keys
# chown -R $ADMIN_USER:$ADMIN_GROUP $SSH_DIR
 
#disable password access
# sed -E -i 's/PasswordAuthentication yes/PasswordAuthentication no/' /etc/ssh/sshd_config
# /etc/init.d/sshd restart
 
yum update -y
yum install -y curl
curl -L get.rvm.io | bash -s stable
source /usr/local/rvm/rvm.sh
rvm requirements
rvmsudo yum install -y gcc-c++ patch readline readline-devel zlib zlib-devel libyaml-devel libffi-devel openssl-devel make bzip2 autoconf automake libtool bison iconv-devel
rvm install 1.9.3
rvm rubygems current

#yum install -y gcc automake autoconf libtool make
#yum install -y ruby
#yum install -y ruby-devel ruby-docs ruby-ri ruby-rdoc
#yum install -y rubygems
#yum install curl
 
#install chef
gem install chef ruby-shadow --no-ri --no-rdoc
mkdir -p /var/chef
chown $ADMIN_USER:$ADMIN_GROUP /var/chef/
 
#init git
yum install -y git-core
 
#init capistrano deploy directories
# mkdir -p /var/www/shared/
# mkdir -p /var/www/releases/
# chown $ADMIN_USER:$ADMIN_GROUP -R /var/www
 
reboot now
