import requests


def loginAPI(id_input, password_input, stop_event):
    try:
        request = {"email" : id_input, "password" : password_input}
        response = requests.post('http://localhost:8000/login', json=request)

        if response.status_code == 200:
            print(response.headers['Authorization'])
        else:
            stop_event.set()
    except Exception as e:
        stop_event.set()
        print("API 호출 중 예외 발생:", e)