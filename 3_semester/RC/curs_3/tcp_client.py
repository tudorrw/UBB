import socket

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.bind(("0.0.0.0", 2515))
client.connect(("193.231.20.76", 9999))
print(client.recv(1024).decode())
client.send("ddd".encode())
client.close()