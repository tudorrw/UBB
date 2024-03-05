#UDP - a connectionless protocol that allows communication between 2 devices without
# establishing a persisten commection
import socket 

server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server.bind(("127.0.0.1", 5555))

message, address = server.recvfrom(1024) #listen for messages coming in

print(message.decode('utf-8'))
server.sendto("hello client".encode('utf-8'), address)
