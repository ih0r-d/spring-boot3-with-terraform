resource "aws_security_group" "demo-gitlab-sg" {
  name   = "demo_sg"
  vpc_id = var.vpc_id

  dynamic "ingress" {
    for_each = ["80", "22"]
    content {
      from_port   = ingress.value
      to_port     = ingress.value
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"] # all internet
    }
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"] # all internet
  }

  tags = {
    Name = "demo_gitalb_sg"
  }
}

resource "tls_private_key" "rsa-gitlab-key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "aws_key_pair" "tf_gitlab_key" {
  key_name   = "tf_gitlab_key"
  public_key = tls_private_key.rsa-gitlab-key.public_key_openssh
}

resource "local_file" "gitlab_key" {
  content  = tls_private_key.rsa-gitlab-key.private_key_pem
  filename = "tf-keys/tf_gitlab.pem"
}

resource "aws_instance" "gitlab-instance" {
  ami                    = var.instance_ami
  instance_type          = var.instance_type
  vpc_security_group_ids = [aws_security_group.demo-gitlab-sg.id]
  user_data              = file("./modules/ec2/docker.sh")
  #  user_data              = file("${path.module}/docker.sh")
  subnet_id              = var.public_subnet_id
  key_name               = aws_key_pair.tf_gitlab_key.key_name
  tags                   = {
    Name = "${var.env}-gitlab-instance"
  }
}
