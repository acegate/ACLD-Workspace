from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from django.conf.urls.static import static
from . import views

app_name = 'main'


urlpatterns = [
    path('', views.home, name='home'),
    path('detail/', views.detail, name='detail'),
    path('agent/', views.agent, name='agent'),
    path('<int:dect_no>/process/', views.process, name='process'),
    path('addEmp/', views.addEmp, name='addEmp'),
    path('addDepart/', views.addDepart, name='addDepart'),
    path('get_updated_data/', views.get_updated_data, name='get_updated_data'),
    path('process_txt/', views.process_txt, name='process_txt'),
    
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)




