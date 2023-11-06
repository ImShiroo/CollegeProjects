from django.urls import path
from . import views

urlpatterns = [
    path('', views.indexChat, name='indexChat'),
    path('<int:chat_id>', views.messages, name='chat'),
]