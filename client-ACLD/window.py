from tkinter import *
import threading
import cv2
import numpy as np
import time, sys
from restAPI import loginAPI

stop_event = threading.Event()

def login():
    capture = cv2.VideoCapture(0)
    if not capture.isOpened():
        print("카메라를 열 수 없습니다.")
        stop_event.set()
        return

    while not stop_event.is_set():
        ret, frame = capture.read()
        if not ret:
            print("프레임을 가져올 수 없습니다.")
            break

        resize_frame = cv2.resize(frame, (640, 640), interpolation=cv2.INTER_LINEAR)
        cv2.imshow("PC_cam", resize_frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):  # 'q' 키를 누르면 종료
            break

    capture.release()
    cv2.destroyAllWindows()
    stop_event.set()

def start_login_thread():

    id_input = entry1.get()
    password_input = entry2.get()

    tk.destroy()

    api_thread = threading.Thread(target=loginAPI, args=(id_input, password_input, stop_event))
    api_thread.start()

    camera_thread = threading.Thread(target=login)
    camera_thread.start()

    camera_thread.join()
    api_thread.join()

def cancel():
    tk.destroy()

tk = Tk()
tk.title('클라이언트')

windows_width = tk.winfo_screenwidth()
windows_height = tk.winfo_screenheight()

app_width = 200
app_height = 150

center_width = (windows_width / 2) - (app_width / 2)
center_height = (windows_height / 2) - (app_height / 2)
tk.geometry(f'{app_width}x{app_height}+{int(center_width)}+{int(center_height)}')

id_label = Label(tk, text='아이디')
id_label.grid(row=0, column=0)


password_label = Label(tk, text='비밀번호')
password_label.grid(row=1, column=0)


entry1 = Entry(tk)
entry1.grid(row=0, column=1)

entry2 = Entry(tk, show="*")
entry2.grid(row=1, column=1)



login_button = Button(tk, text='로그인', bg='white', fg='black', command=start_login_thread)
login_button.grid(row=2, column=0)

cancel_button = Button(tk, text='취소', bg='white', fg='black', command=cancel)
cancel_button.grid(row=2, column=1)

tk.mainloop()