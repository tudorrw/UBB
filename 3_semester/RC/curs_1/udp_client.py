import socket

client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
message = "hey"
client.sendto(message.encode('utf-8'), ("127.0.0.1", 5555))

print(client.recvfrom(1024)[0].decode('utf-8'))
