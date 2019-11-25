provider "aws" {
    region     = "us-east-1"
}

resource "aws_instance" "web-app" {
  ami             = "ami-3eee4150"
  instance_type   = "t2.micro"
  associate_public_ip_address = true
  subnet_id = "subnet-c5c3059d"
  tags {
    Name = "web-app"
  }
}

