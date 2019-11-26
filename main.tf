provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "web-app" {
  ami             = "ami-00068cd7555f543d5"
  instance_type   = "t2.micro"
  associate_public_ip_address = true
  subnet_id = "subnet-c5c3059d"
}

