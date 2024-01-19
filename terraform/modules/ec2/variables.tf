variable "env" {}

variable "vpc_id" {}

variable "public_subnet_id" {}

variable "instance_type" {
  default = "t2.micro"
  description = "Instance type for app server"
}

variable "instance_ami" {
  default = "ami-0c7217cdde317cfec"
  description = "Ununtu 22.04 AMI"
}