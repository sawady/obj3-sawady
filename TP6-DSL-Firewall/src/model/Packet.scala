package model

case class Packet(val port: Int, val sourceIP: IP, val destIP: IP, val body: String)