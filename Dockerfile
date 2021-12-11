
FROM jenkins/jenkins:lts-jdk11
MAINTAINER Ozz007 & Quintus

# Disable setup wizard
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false -Dpermissive-script-security.enabled=true

USER root
RUN apt-get update && \
    apt-get -y install apt-transport-https \
      ca-certificates \
      curl \
      gnupg2 \
      software-properties-common && \
    curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg > /tmp/dkey; apt-key add /tmp/dkey && \
    add-apt-repository \
      "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable" && \
   apt-get update && \
   apt-get -y install docker-ce

# Load plugins w jenkins-plugin-cli
COPY --chown=jenkins:jenkins jenkins/conf/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt


RUN \
# Update
apt-get update -y && \
# Install Unzip
apt-get install unzip -y && \
# need wget
apt-get install wget -y && \
# vim
apt-get install vim -y

################################
# Install Terraform
################################

# Download terraform for linux
RUN wget https://releases.hashicorp.com/terraform/1.1.0/terraform_1.1.0_linux_amd64.zip

# Unzip
RUN unzip terraform_1.1.0_linux_amd64.zip

# Move to local bin
RUN mv terraform /usr/local/bin/
# Check that it's installed
RUN terraform --version

ADD terraform/ /usr/share/terraform

RUN chown -R jenkins /usr/share/terraform

USER jenkins