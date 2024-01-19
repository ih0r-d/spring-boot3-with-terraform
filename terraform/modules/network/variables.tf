variable "env" {}

variable "cidr_block" {
  default     = "10.0.0.0/16"
  description = "Cidr block for network module"
}

variable "private_cidr_block" {
  default     = "10.0.1.0/24"
  description = "Cidr block for private subnet"
}

variable "public_cidr_block" {
  default     = "10.0.2.0/24"
  description = "Cidr block for public subnet"
}