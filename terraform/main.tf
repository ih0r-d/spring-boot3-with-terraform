data "aws_region" "current" {}

module "network" {
  source = "./modules/network"

  env = var.env
}

module "ec2" {
  source = "./modules/ec2"

  env = var.env
  vpc_id = module.network.vpc_id
  public_subnet_id = module.network.public_subnet_id
}

