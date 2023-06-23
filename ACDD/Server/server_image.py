import socket
import cv2
import numpy as np
from _thread import *
import base64
import threading
import time
import json
from server.util import Util
from server.database import DataBase

class Server:
    def __init__(self, HOST : str, PORT : int):
        self.__HOST = HOST
        self.__PORT = PORT
        self.STREAM_BYTE = 64
        self.__utility = Util()
        self.COUNT = 0
        self.__client_sockets = []
        self.__is_alive = False

        user_name = 'root'
        user_pwd = '1735'
        host = '192.168.50.131'
        port = 3306
        db_name = 'acdd'
        self.connectDB = DataBase(user_name, user_pwd, host, port, db_name)
        self.server_open()

    def server_open(self) -> None:
        self.__server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.get_server_socket().setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 10)
        self.get_server_socket().setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1)
        self.get_server_socket().setsockopt(socket.IPPROTO_TCP, socket.TCP_KEEPIDLE, 1)
        self.get_server_socket().setsockopt(socket.IPPROTO_TCP, socket.TCP_KEEPINTVL,  3)
        self.get_server_socket().setsockopt(socket.IPPROTO_TCP, socket.TCP_KEEPCNT, 1)

        self.get_server_socket().bind((self.get_host(), self.get_port()))
        self.get_server_socket().listen()
        print('>> Server start')
        self.server_accept()

    def server_accept(self) -> None:
        while True:
            try:
                print('>> Server Wait!!')
                self.__client_socket, self.__addr = self.get_server_socket().accept()
                self.set_alive(1)
                self.get_util().create_folder(self.get_client_ip())
                self.get_client_socket().settimeout(10000)
                thread = threading.Thread(target=self.receiveTarget)
                thread.start()
                self.COUNT += 1
            except Exception as e:
                print(e)
                self.get_client_socket().close()
                self.get_server_socket().close()
                self.server_open()

    def receiveTarget(self):
        print('>> Connected by :', self.get_client_ip(), ':', self.get_client_port())
        img_count = 0
        save_path = self.get_util().get_relative_path(self.get_client_ip())
        media_path = self.get_util().get_media_path(self.get_client_ip()) 

        while True:
            try:
                cam_data = self.get_data()
                cam_path = self.save_data(cam_data, img_count, 1, save_path, media_path)

                screen_data = self.get_data()
                screen_path = self.save_data(screen_data, img_count, 0, save_path, media_path)
                
                json_data = self.get_data()
                self.result_data = json.loads(json_data)
                
                agent_no = self.connectDB.select_identify(self.result_data['IP'], self.result_data['MACAddress'])
                self.connectDB.update_agent(self.is_alive(), agent_no)
                self.connectDB.insert_dection(self.result_data['saborn'], cam_path, screen_path)

                img_count += 1
                time.sleep(0.95)
            except socket.timeout:
                print('>> set timeout agent status OFF')
                self.agent_OFF(self.result_data)
                
            except Exception as e:
                print('>> set timeout agent status OFF')
                self.agent_OFF(self.result_data)
                break

    def agent_OFF(self, result_data):
        self.set_alive(0)
        agent_no = self.connectDB.select_identify(self.result_data['IP'], self.result_data['MACAddress'])
        self.connectDB.update_agent(self.is_alive(), agent_no)
        self.get_client_socket().close()

    def recive_data(self, count : int) -> bytes:
        buf = b''
        while count:
            newbuf = self.get_client_socket().recv(count)
            if not newbuf: return None
            buf += newbuf
            count -= len(newbuf)
        return buf

    def get_data(self):
        length = self.recive_data(self.STREAM_BYTE).decode('utf-8')
        return self.recive_data(int(length))

    def save_data(self, data, count, flag, save_path, media_path):
        decode_data = np.frombuffer(base64.b64decode(data), dtype='uint8')
        decimg = cv2.imdecode(decode_data, cv2.IMREAD_COLOR)
        if flag:
            cv2.imwrite(media_path + '\\' + f'CAM_{self.get_client_ip()}_{count}.jpg', decimg)
            return save_path + '/' + f'CAM_{self.get_client_ip()}_{count}.jpg'
        else:
            cv2.imwrite(media_path + '\\' + f'ScreenShot_{self.get_client_ip()}_{count}.jpg', decimg)
            return save_path + '/' + f'ScreenShot_{self.get_client_ip()}_{count}.jpg'
        
    def get_client_ip(self):
        return self.__addr[0]

    def get_client_port(self):
        return self.__addr[1]

    def get_client_socket(self):
        return self.__client_socket

    def get_server_socket(self):
        return self.__server_socket
    
    def get_host(self):
        return self.__HOST
    
    def get_port(self):
        return self.__PORT
    
    def get_util(self):
        return self.__utility

    def is_alive(self):
        return self.__is_alive
    
    def set_alive(self, is_alive):
        self.__is_alive = is_alive


