from django.shortcuts import render, redirect
from .models import *
from django.db.models import Q
from rentItapp.models import *

# Create your views here.


def indexChat(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    dados = {'userLogged': userLogged}
    chats = Chat.objects.filter(Q(pessoa1= userLogged) | Q(pessoa2=userLogged))

    if request.method == "POST" and "remover" in request.POST:
        dados['chats'] = chats
        return redirect("../chat")



    if "search_conversa" in request.GET:
        x = request.GET.get('search_conversa')
        chatsFiltrados = chats.filter(Q(pessoa1__nome__icontains=x) | Q(pessoa2__nome__icontains=x))
        if chatsFiltrados:
            dados['chats'] = chatsFiltrados
    else:
        dados = {'userLogged': userLogged, 'chats': chats}
    return render(request, "chat/index.html", dados)


def messages(request, chat_id):
    dados = {}
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    chats = Chat.objects.filter(Q(pessoa1=userLogged) | Q(pessoa2=userLogged))
    chat = Chat.objects.get(chat_id=chat_id)
    pessoa1 = userLogged
    pessoa2 = chat.get_partner(userLogged)

    if request.method == "POST":
        if "mensagem" in request.POST:
            message = request.POST.get("mensagem")

            mensagem = Mensagem(chat_id=chat, remetente_id=pessoa1, destinatario_id=pessoa2,
                                conteudo=message)
            mensagem.save()

    #show messages
    mensagens = Mensagem.objects.filter(chat_id=chat)
    mensagens.order_by('data_hora')
    mensagens = list(mensagens)
    mensagens = mensagens[-20:]

    if mensagens[0].remetente_id == userLogged:
        outraPessoa = mensagens[0].destinatario_id
    else:
        outraPessoa = mensagens[0].remetente_id


    if "search_conversa" in request.GET:
        x = request.GET.get('search_conversa')
        chatsFiltrados = chats.filter(Q(pessoa1__nome__icontains=x) | Q(pessoa2__nome__icontains=x))
        if chatsFiltrados:
            dados['chats'] = chatsFiltrados



    dados = {'userLogged': userLogged, 'chats': chats, 'mensagens': mensagens, 'outraPessoa': outraPessoa}
    return render(request, "chat/index.html", dados)

