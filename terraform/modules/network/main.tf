data "aws_availability_zones" "available" {
  state = "available"
}

resource "aws_vpc" "main" {
  cidr_block           = var.cidr_block
  enable_dns_support   = true # support domain name
  enable_dns_hostnames = true #

  tags = {
    Name = "${var.env}-main"
  }
}

resource "aws_internet_gateway" "main" {
  vpc_id = aws_vpc.main.id

  tags = {
    Name = "${var.env}-main"
  }
}

resource "aws_subnet" "public" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = var.public_cidr_block
  map_public_ip_on_launch = true # assign public IP
  availability_zone       = data.aws_availability_zones.available.names[0]

  tags = {
    Name = "${var.env}-PublicSubnet"
  }
}

resource "aws_subnet" "private" {
  vpc_id                  = aws_vpc.main.id
  cidr_block              = var.private_cidr_block
  map_public_ip_on_launch = false # not needed to assign public IP
  availability_zone       = data.aws_availability_zones.available.names[0]

  tags = {
    Name = "${var.env}-PrivateSubnet"
  }
}

resource "aws_route_table" "table_routes" {
  vpc_id = aws_vpc.main.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }

  tags = {
    Name = "${var.env}-PrivateRoutes"
  }
}

/*
resource "aws_route_table" "public_routes" {
  vpc_id = aws_vpc.main.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }

  tags = {
    Name = "PrivateRoutes"
  }
}
*/

resource "aws_route_table_association" "public_routes" {
  route_table_id = aws_route_table.table_routes.id
  subnet_id      = aws_subnet.public.id
}

resource "aws_route_table_association" "private_routes" {
  route_table_id = aws_route_table.table_routes.id
  subnet_id      = aws_subnet.private.id
}

