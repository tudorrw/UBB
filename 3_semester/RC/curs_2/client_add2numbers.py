import socket

client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

number1 = input("give first number: ")
client.sendto(number1.encode(),("127.0.0.1", 7777))

number2 = input("give second number: ")
client.sendto(number2.encode(),("127.0.0.1", 7777))

message, adr = client.recvfrom(1024)
try:
    print(int(message.decode(), 2))
except ValueError:
    print(message.decode())
