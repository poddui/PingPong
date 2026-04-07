import socket
import torch
import numpy as np

s = socket.socket()
s.connect(("localhost" , 9999))

while True:
    state = s.recv(1024).decode().strip()
    if state:
        print("FROM JAVA:", state)

    action = "-1"
    s.send((action + "\n").encode())