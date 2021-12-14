terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.27"
    }
  }
  required_version = ">= 0.14.9"
}

provider "aws" {
  profile = "default"
  region  = "us-east-2"
  shared_credentials_file = "./.credentials-ynov"
}

data "template_file" "user_data" {
  template = file("../scripts/add-ssh-web-app.yaml")
}

resource "aws_instance" "app_server" {
  count = var.instance_count
  ami           = "ami-0d97ef13c06b05a19"
  instance_type = "t2.micro"
  associate_public_ip_address = true
  user_data                   = data.template_file.user_data.rendered
  key_name = "ssh_EDIET"
  vpc_security_group_ids = [aws_security_group.allow_ssh.id]

  tags = {
    Name   = "${var.instance_name}_${count.index}"
    Groups = "app"
    Owner  = "DIET-Ebbane"
  }
}

resource "aws_key_pair" "deployer"{
  key_name = "ssh_EDIET"
  public_key =  file ("~/.ssh/id_rsa.pub")
}

resource "aws_security_group" "allow_ssh" {
  name        = "allow_ssh_EDIET"
  description = "Allow TLS inbound traffic"

  ingress {
    description      = "TLS from VPC"
    from_port        = 22
    to_port          = 22
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  ingress {
    description      = "Web from VPC"
    from_port        = 8080
    to_port          = 8080
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "allow_tls"
  }
}

variable "instance_name" {
  description = "Value of the Name tag for the EC2 instance"
  type        = string
  default     = "DIET_app"
}
variable "instance_count" {
  description = "Number of instance"
  type        = number
  default     = 1
}

output "instance_id" {
  description = "ID of the EC2 instance"
  value       = aws_instance.app_server.*.id
}

output "instance_public_ip" {
  description = "Public IP address of the EC2 instance"
  value       = aws_instance.app_server.*.public_ip
}