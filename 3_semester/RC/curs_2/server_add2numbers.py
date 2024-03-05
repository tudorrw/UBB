import socket

server = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)  
server.bind(("127.0.0.1", 7777))                           

message, addr = server.recvfrom(1024)  
number1 = message

message, addr = server.recvfrom(1024)  
number2 = message

try:
    server.sendto(bin(int(number1) + int(number2)).encode(), addr)
except ValueError:
    server.sendto("invalid input".encode(), addr)

    
