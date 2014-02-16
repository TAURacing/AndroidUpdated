import sys
import socket
import random
import time

UDP_IP = "192.168.225.3"
UDP_PORT = 5555

print "UDP target IP: ", UDP_IP
print "UDP target port: ", UDP_PORT

sock = socket.socket(socket.AF_INET, # Internet
                     socket.SOCK_DGRAM) # UDP
while(True):
    MESSAGE = "RPM:" + str(random.randint(80,255))
    print MESSAGE
    sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
    time.sleep(random.random())
    MESSAGE = "OIL:" + str(random.randint(80,95)) + "%"
    print MESSAGE
    sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
    time.sleep(random.random())
