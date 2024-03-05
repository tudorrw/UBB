import socket

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.bind(("0.0.0.0", 9999))
client.listen(5)
cs, addr = client.accept()
b = cs.recv(1024).decode()
print(b)
cs.send()