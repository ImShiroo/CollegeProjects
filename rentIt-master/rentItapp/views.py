from django.shortcuts import render, redirect
from .forms import *
from .models import *
from chat.models import *
from django.core.mail import send_mail
from datetime import datetime, timedelta
from django.db.models import Q
from dateutil.relativedelta import *
from random import randint
from django.http import JsonResponse
import pandas as pd


#INDEX
def newsletter_signup(request):
    testimonials = Testimonial.objects.all()
    try:
        userLogged = Utilizador.objects.get(user_id=request.user.username)

        dados = {'userLogged': userLogged, 'testimonials': testimonials}

    #caso do admin
    except:
        dados = {"testimonials": testimonials}

    if request.method == "POST":
        email = request.POST.get("email")
        if email:
            Newsletter(email=email).save()
            send_mail(
                'Newsletter Subscription',  # Subject da mensagem
                'Thank you for subscribing to our newsletter!',  # mensagem
                'ptiptr20202021@gmail.com',  # de email
                [email],  # para email
            )

    return render(request, 'index.html', dados)


def sobre(request):
    try:
        userLogged = Utilizador.objects.get(user_id=request.user.username)
        return render(request, 'sobre.html', {'userLogged': userLogged})
    except:
        return render(request, 'sobre.html')


def managing(request, hab_id):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    habitacao = Habitacao.objects.get(hab_id=hab_id)
    pedidos = Pedidos.objects.filter(habitacao_id=hab_id)
    habitaVar = Habita.objects.filter(habitacao_id=hab_id)

    if request.method == "POST" and "msg" in request.POST:
        idInquilino = request.POST.get("msg")

        inquilino = Utilizador.objects.get(user_id= idInquilino)

        try:
            chat = Chat.objects.get(
                (Q(pessoa1=userLogged) | Q(pessoa2=userLogged)) & (Q(pessoa1=inquilino) | Q(pessoa2=inquilino)))

            # Se já houver um chat
            if chat:
                idChat = chat.chat_id
                url = "../chat/?" + str(idChat)
                return redirect(url)

        # Se não houver um chat
        except:
            chatNovo = Chat(pessoa1=inquilino, pessoa2=userLogged)
            chatNovo.save()
            idChat = chatNovo.chat_id

            #envia uma mensagem vazia
            mensagem = " A conversa foi iniciada "
            mensagem = Mensagem(chat_id=chatNovo, remetente_id=userLogged, destinatario_id=inquilino,
                                conteudo=mensagem)
            mensagem.save()

            url = "../chat/?" + str(idChat)
            return redirect(url)




    if request.method == 'POST':

        #para eliminar inquilinos
        if "eliminar" in request.POST:
            idHabitado = request.POST.get("habitado")
            habita = Habita.objects.get(habitado_id=idHabitado)

            idCasa = habita.habitacao_id.hab_id
            habita.delete()

            url = "../managing/" + str(idCasa)

            return redirect(url)

        # Para tratar dos pedidos de arrendar
        elif "estado" in request.POST:

            #O pedido foi aceite
            if request.POST.get("estado") == "Aceite":

                idPedido = request.POST.get("pedidoId")
                pedido = Pedidos.objects.get(pedido_id=idPedido)
                habitacao = pedido.habitacao_id
                casaId = habitacao.hab_id


                # Adiciona à tabela Inquilinos caso ainda nao esteja
                if not Inquilino.objects.filter(user_id=pedido.user_id):
                    novoInquilino = Inquilino(user_id=pedido.user_id)
                    novoInquilino.save()
                else:
                    novoInquilino = Inquilino.objects.get(user_id=pedido.user_id)

                # Adiciona à tabela Habita

                #calcular o preço total da estadia
                dias = pedido.data_saida - pedido.data_entrada
                renda_total = float(round((dias.days * habitacao.preco), 2))

                novoHabita = Habita(inquilino_id= novoInquilino, habitacao_id= pedido.habitacao_id, desde=pedido.data_entrada, ate=pedido.data_saida, renda=habitacao.preco, valorAPagar=renda_total,  dia_pagamento= request.POST.get("pagamento"), n_hospedes= pedido.n_hospedes)
                novoHabita.save()

                #Apaga o pedido da tabela Pedidos
                Pedidos.objects.filter(pedido_id=idPedido).delete()

                url = "../managing/" + str(casaId)
                return redirect(url)

            elif request.POST.get("estado") == "Recusado":

                idPedido = request.POST.get("pedidoId")
                pedido = Pedidos.objects.get(pedido_id=idPedido)
                habitacao = pedido.habitacao_id
                casaId = habitacao.hab_id
                Pedidos.objects.filter(pedido_id=idPedido).delete()
                url = "../managing/" + str(casaId)
                return redirect(url)

    dados = {'userLogged': userLogged, 'habitacao': habitacao, 'pedidos': pedidos, 'habita': habitaVar}

    return render(request, 'managing.html', dados)


def alojamentos(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    todosAlojamentos = Habitacao.objects.all()
    try:
        senhorio = Senhorio.objects.get(user_id = userLogged)
        casas = gere.objects.filter(senhorio_id = senhorio)
        for casa in casas:
            habitacao = Habitacao.objects.get(hab_id = casa.hab_id.hab_id)
            todosAlojamentos = todosAlojamentos.exclude(hab_id = habitacao.hab_id)
    except:
        todosAlojamentos = Habitacao.objects.all()

    keys = [
        'search',
        'fogao',
        'frigorifico',
        'animais',
        'fumador',
        'wifi',
        'tipo',
        'preco',
        'quartos',
        'wc'
    ]

    filters = {}
    for key in keys:
        x = request.GET.get(key)
        if x:
            if key == "preco":
                filters['{}__lte'.format(key)] = x
            elif key == "wifi" or key == "fumador" or key == "animais" or key == "frigorifico" or key == "fogao":
                filters['{}'.format(key)] = True
            elif key == "quartos" or key == "wc":
                filters['{}__gte'.format(key)] = x
            elif key == "search":
                filters["titulo__icontains"] = x
            else:
                filters['{}'.format(key)] = x

    query = todosAlojamentos.filter(**filters)

    dados = {'userLogged': userLogged, 'todosAlojamentos': todosAlojamentos, 'filters': query}

    favoritos = Anunciosfav.objects.filter(user_id=userLogged)
    casasFav = []
    if favoritos:
        for favorito in favoritos:
            casasFav.append(Habitacao.objects.get(hab_id=(favorito.habitacao_id).hab_id).hab_id)

    dados['casasFav'] = casasFav

    if request.method == 'POST':

        #adicionar aos favoritos
        if "adicionar" in request.POST:
            casaId = request.POST.get("adicionar")
            casa = Habitacao.objects.get(hab_id = casaId)

            novoFavorito = Anunciosfav(user_id=userLogged, habitacao_id=casa)

            #procura se a casa já está favoritada pela pessoa
            favoritos = Anunciosfav.objects.filter(user_id=userLogged, habitacao_id=casa)
            if not favoritos.exists():
                novoFavorito.save()
            return redirect("../alojamentos")

        # remover aos favoritos
        elif "remover" in request.POST:
            casaId = request.POST.get("remover")
            casa = Habitacao.objects.get(hab_id=casaId)
            Anunciosfav.objects.filter(habitacao_id=casa).delete()
            return redirect("../alojamentos")

    return render(request, 'alojamentos.html', dados)


def perfil(request):
    userParaEditar = Utilizador.objects.get(user_id=request.user.username)
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    dados = {'userLogged': userLogged, 'userParaEditar': userParaEditar}
    return render(request, 'perfil.html', dados)


def my_alojamentos(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    try:
        habitacoesQueGere = gere.objects.filter(senhorio_id= Senhorio.objects.get(user_id=request.user.username))
        if habitacoesQueGere:
            habitacoes = []
            for g in habitacoesQueGere:
                novaCasa = g.hab_id
                habitacoes.append(novaCasa)

        dados = {'userLogged': userLogged, 'habitacoes': habitacoes}
    except:
        dados = {'userLogged': userLogged}

    if request.method == 'POST':
        # para eliminar habitacoes
        if "eliminar" in request.POST:
            idCasa = request.POST.get("eliminar")
            habitacao = Habitacao.objects.get(hab_id=idCasa)
            habitas = Habita.objects.filter(habitacao_id=habitacao)

            #apagar todos os habitas
            for h in habitas:
                h.delete()

            #apagar o gere
            gere.objects.get(hab_id=idCasa).delete()

            #apagar a habitacao
            Habitacao.objects.get(hab_id=idCasa).delete()

            #apagar dos pedidos
            Pedidos.objects.filter(habitacao_id=idCasa).delete()

        return redirect("../my_alojamentos")

    return render(request, 'my_alojamentos.html', dados)


def register(request):
    utilizadores = Utilizador.objects.all()
    if request.method == 'POST':
        form = UserRegisterForm(request.POST)
        if form.is_valid():
            form.save()
            username = request.POST.get("username")
            email = request.POST.get("email")
            Utilizador(user_id=username, email=email).save()
            return redirect("../login")
        else:
            form = UserRegisterForm()
    else:
        form = UserRegisterForm()
    return render(request, 'register.html', {'form': form , "utilizadores":utilizadores})


def login(request):
    testimonials = Testimonial.objects.all()
    return render(request, 'login.html', testimonials)


def add_habitacao(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    dados = {'userLogged': userLogged}
    if request.method == "POST":
        form = addHabitacaoForm(request.POST, request.FILES)
        if form.is_valid():
            novaHabitacao = form.save()
            #Se ainda nao estiver na tabela de senhorios adiciona
            try:
                Senhorio.objects.get(user_id=Utilizador.objects.get(user_id=request.user.username))
                novoGere = gere(hab_id=Habitacao.objects.get(hab_id=novaHabitacao.pk),
                                senhorio_id=Senhorio.objects.get(user_id=request.user.username)).save()
            except:
                novoSenhorio = Senhorio(user_id= Utilizador.objects.get(user_id=request.user.username)).save()
                novoGere = gere(hab_id= Habitacao.objects.get(hab_id=novaHabitacao.pk), senhorio_id= Senhorio.objects.get(user_id=request.user.username)).save()
                dados = {'userLogged': userLogged, 'form': form}
            return redirect("../my_alojamentos")
    else:
        form = addHabitacaoForm()
        dados = {'userLogged':userLogged, 'form': form}
    return render(request, 'create_alojamento.html', dados)

def edit_habitacao(request, hab_id):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    habitacao = Habitacao.objects.get(hab_id=hab_id)
    if request.method == "POST":
        form = addHabitacaoForm(request.POST, request.FILES)
        if request.POST.get("estado") != "" and request.POST.get("estado") != habitacao.estado:
            if request.POST.get("estado") == "on":
                habitacao.estado = True
            else:
                habitacao.estado = False

            habitacao.save(update_fields=['estado'])
        if request.POST.get("titulo") != "":
            habitacao.titulo = request.POST.get("titulo")
            habitacao.save(update_fields=['titulo'])
        if request.POST.get("descricao") != "":
            habitacao.descricao = request.POST.get("descricao")
            habitacao.save(update_fields=['descricao'])
        if request.POST.get("foto") != "":
            novaFoto = request.FILES['foto']
            habitacao.foto = novaFoto
            habitacao.save(update_fields=['foto'])
        if request.POST.get("fumador") != "" and request.POST.get("fumador") != habitacao.fumador:
            if request.POST.get("fumador") == "on":
                habitacao.fumador = True
            else:
                habitacao.fumador = False
            habitacao.save(update_fields=['fumador'])
        if request.POST.get("tipo") != "" and request.POST.get("tipo")!=habitacao.tipo:
            habitacao.tipo = request.POST.get("tipo")
            habitacao.save(update_fields=['tipo'])
        if request.POST.get("morada") != "":
            habitacao.morada = request.POST.get("morada")
            habitacao.save(update_fields=['morada'])
        if request.POST.get("wc") != "" and request.POST.get("wc") != habitacao.wc:
            habitacao.wc = request.POST.get("wc")
            habitacao.save(update_fields=['wc'])
        if request.POST.get("quartos") != "" and request.POST.get("quartos") != habitacao.quartos:
            habitacao.quartos = request.POST.get("quartos")
            habitacao.save(update_fields=['quartos'])
        if request.POST.get("area") != "" and request.POST.get("area") != habitacao.area:
            habitacao.area = request.POST.get("area")
            habitacao.save(update_fields=['area'])
        if request.POST.get("sol") != "" and request.POST.get("sol") != habitacao.sol:
            habitacao.sol = request.POST.get("sol")
            habitacao.save(update_fields=['sol'])
        if request.POST.get("preco") != "" and request.POST.get("preco") != habitacao.preco:
            habitacao.preco = request.POST.get("preco")
            habitacao.save(update_fields=['preco'])
        if request.POST.get("faixa_etaria") != "" and request.POST.get("faixa_etaria") != habitacao.faixa_etaria :
            habitacao.faixa_etaria = request.POST.get("faixa_etaria")
            habitacao.save(update_fields=['faixa_etaria'])
        if request.POST.get("animais") != "" and request.POST.get("animais") != habitacao.animais:
            if request.POST.get("animais") == "on":
                habitacao.animais = True
            else:
                habitacao.animais = False

            habitacao.save(update_fields=['animais'])
        if request.POST.get("genero") != "" and request.POST.get("genero") != habitacao.genero:
            habitacao.genero = request.POST.get("genero")
            habitacao.save(update_fields=['genero'])
        if request.POST.get("wifi") != "" and request.POST.get("wifi")!=habitacao.wifi:
            if request.POST.get("wifi") == "on":
                habitacao.wifi = True
            else:
                habitacao.wifi = False

            habitacao.save(update_fields=['wifi'])
        if request.POST.get("limpeza") != "" and request.POST.get("limpeza") != habitacao.limpeza:
            if request.POST.get("limpeza") == "on":
                habitacao.limpeza = True
            else:
                habitacao.limpeza = False

            habitacao.save(update_fields=['limpeza'])
        if request.POST.get("ar_condicionado") != "" and request.POST.get("ar_condicionado")!= habitacao.ar_condicionado:
            if request.POST.get("ar_condicionado") == "on":
                habitacao.ar_condicionado = True
            else:
                habitacao.ar_condicionado = False

            habitacao.save(update_fields=['ar_condicionado'])
        if request.POST.get("televisao") != "" and request.POST.get("televisao")!=habitacao.televisao:
            if request.POST.get("televisao") == "on":
                habitacao.televisao = True
            else:
                habitacao.televisao = False
            habitacao.save(update_fields=['televisao'])
        if request.POST.get("fogao") != "" and request.POST.get("fogao")!= habitacao.fogao :
            if request.POST.get("fogao") == "on":
                habitacao.fogao = True
            else:
                habitacao.fogao = False
            habitacao.save(update_fields=['fogao'])
        if request.POST.get("frigorifico") != "" and request.POST.get("frigorifico")!=habitacao.frigorifico:
            if request.POST.get("frigorifico") == "on":
                habitacao.frigorifico = True
            else:
                habitacao.frigorifico = False
            habitacao.save(update_fields=['frigorifico'])
        if request.POST.get("metro") != "" and request.POST.get("metro")!=habitacao.metro:
            if request.POST.get("metro") == "on":
                habitacao.metro = True
            else:
                habitacao.metro = False

            habitacao.save(update_fields=['metro'])
        if request.POST.get("autocarro") != "" and request.POST.get("autocarro") != habitacao.autocarro:
            if request.POST.get("autocarro") == "on":
                habitacao.autocarro = True
            else:
                habitacao.autocarro = False

            habitacao.save(update_fields=['autocarro'])
        if request.POST.get("extras") != "" and request.POST.get("extras") != habitacao.extras:
            habitacao.extras = request.POST.get("extras")
            habitacao.save(update_fields=['extras'])

        dados = {'form': form, 'userLogged': userLogged, 'habitacao': habitacao}
        return redirect("../my_alojamentos")

    else:
        form = addHabitacaoForm()
        dados = {'form': form, 'userLogged': userLogged, 'habitacao': habitacao}
        return render(request, 'edit_alojamento.html', dados)


def verAlojamento(request, hab_id):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    habitacao = Habitacao.objects.get(hab_id=hab_id)
    gerencia = gere.objects.get(hab_id=habitacao)
    senhorio = Senhorio.objects.get(user_id=gerencia.senhorio_id.user_id).user_id
    avalia_tabela = Avalia.objects.filter(habitacao_id= habitacao).count()
    avalia_tudo = Avalia.objects.filter(habitacao_id= habitacao)

    if request.method == "GET" and request.GET.get("data") == "checkOcupacao":
        habitacao = Habitacao.objects.get(hab_id=hab_id)
        datasOcupadas = []
        habita = Habita.objects.filter(habitacao_id=habitacao)

        for h in habita:
            inicio = h.desde
            fim = h.ate

            datas = list(map(lambda x: x.strftime('%Y%m%d'), pd.date_range(inicio, fim-timedelta(days=1),freq='d')))
            datasOcupadas.append(datas)

        return JsonResponse({'data': datasOcupadas}, status=200)

    if request.method == "POST" and request.POST.get("msg"):
        habitacao = Habitacao.objects.get(hab_id=hab_id)
        inquilino = Utilizador.objects.get(user_id=request.user.username)
        mensagem = request.POST.get("msg")

        try:
            chat = Chat.objects.get((Q(pessoa1= userLogged) | Q(pessoa2=userLogged)) & (Q(pessoa1= senhorio) | Q(pessoa2=senhorio)))
            #Se já houver um chat
            if chat:
                mensagem = Mensagem(chat_id=chat, remetente_id=inquilino, destinatario_id=senhorio,
                                    conteudo=mensagem)
                mensagem.save()
        #Se não houver um chat
        except:

            chatNovo = Chat(pessoa1=inquilino, pessoa2=senhorio)
            chatNovo.save()
            mensagem = Mensagem(chat_id=chatNovo, remetente_id=inquilino, destinatario_id=senhorio, conteudo=mensagem)
            mensagem.save()


        return redirect("../verAlojamento/"+str(hab_id)+"?")


    if request.method == "POST":
        habitacao = Habitacao.objects.get(hab_id=hab_id)
        inquilino = Utilizador.objects.get(user_id=request.user.username)
        senhorio  = gere.objects.get(hab_id = hab_id).senhorio_id
        try:
            #verificar se já está na tabela habita
            habita = Habita.objects.filter(inquilino_id= Inquilino.objects.get(user_id=inquilino.user_id), habitacao_id= habitacao)
            #verificar se já mandou um pedido
            pedido = Pedidos.objects.filter(user_id = userLogged, habitacao_id = habitacao)
            if not habita and not pedido:
                novoPedido = Pedidos(user_id=inquilino, senhorio_id = senhorio, habitacao_id=habitacao,
                             data_entrada= request.POST.get("entrada"), data_saida=request.POST.get("saida"),
                             n_hospedes=request.POST.get("hospedes") )
                novoPedido.save()

        except:
            # verificar se já mandou um pedido
            pedido = Pedidos.objects.filter(user_id=userLogged, habitacao_id=habitacao)
            if not pedido:
                novoPedido = Pedidos(user_id=inquilino, senhorio_id=senhorio, habitacao_id=habitacao,
                                 data_entrada=request.POST.get("entrada"), data_saida=request.POST.get("saida"),
                                 n_hospedes=request.POST.get("hospedes"))
                novoPedido.save()

        return redirect("../verAlojamento/"+str(hab_id)+"?")

    else:
        form = addPedidoForm()
        minSaida = datetime.today() + relativedelta(months=+1)
        dados = {'userLogged': userLogged, 'habitacao': habitacao, 'min': datetime.today().strftime('%Y-%m-%d'),
                 'minSaida': minSaida.strftime('%Y-%m-%d'), 'form': form, "c":avalia_tabela,
                 "dados_comentarios":avalia_tudo}

        return render(request, 'verAlojamento.html', dados)


def edit_user(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    if request.method == "POST":
        userParaEditar = Utilizador.objects.get(user_id=request.user.username)
        form = userEditForm(request.POST, request.FILES)
        if request.POST.get("nome") != "":
            userParaEditar.nome = request.POST.get("nome")
        if request.POST.get("foto") != "":
            novaFoto = request.FILES['foto']
            userParaEditar.foto = novaFoto
        if request.POST.get("email") != "":
            userParaEditar.email = request.POST.get("email")
        if request.POST.get("nascimento") != "":
            userParaEditar.nascimento = request.POST.get("nascimento")
        if request.POST.get("nif") != "":
            userParaEditar.nif = request.POST.get("nif")
        if request.POST.get("genero") != "":
            userParaEditar.genero = request.POST.get("genero")
        if request.POST.get("localidade") != "":
            userParaEditar.localidade = request.POST.get("localidade")
        if request.POST.get("lingua") != "":
            userParaEditar.lingua = request.POST.get("lingua")

        userParaEditar.save()
        return redirect("../perfil")
    else:
        userParaEditar = Utilizador.objects.get(user_id=request.user.username)
        form = userEditForm()
        dados = {'userCorrente': userParaEditar, 'form': form, 'userLogged': userLogged}

        return render(request, 'editPerfil.html', dados)


def anunciosfavoritos(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    anunciosFavoritos = Anunciosfav.objects.filter(user_id=request.user.username)

    if request.method == 'POST':
        if "remover_anuncio" in request.POST:
            anuncio_removido = request.POST.get("remover_anuncio")
            casa = Habitacao.objects.get(hab_id=anuncio_removido)
            Anunciosfav.objects.filter(habitacao_id=casa).delete()
            return redirect("../favAnuncios")

    dados = {'userLogged': userLogged, 'anunciosFavoritos': anunciosFavoritos}
    return render(request, 'fav_anuncios.html', dados)


def histarrendamentos(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)

    try:
        historico = HistoricoReservas.objects.filter(senhorio= userLogged)
        dados = {'userLogged': userLogged, 'historico': historico}

    except:
        dados = {'userLogged': userLogged}

    return render(request, 'hist_arrendamentos.html', dados)


def histreservas(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    try:
        historico = HistoricoReservas.objects.filter(inquilino=userLogged)
        dados = {'userLogged': userLogged, 'historico': historico}

    except:
        dados = {'userLogged': userLogged}
    return render(request, 'hist_reservas.html', dados)

def alugueres(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    try:
        inquilino = Inquilino.objects.get(user_id=userLogged)
        alugueres = Habita.objects.filter(inquilino_id=inquilino)
        pagamentos = Pagamentos.objects.filter(inquilino_id=inquilino)

        dados = {'userLogged': userLogged, 'alugueres': alugueres, 'pagamentos': pagamentos}

        if request.method == "POST" and request.POST.get("valorPagar"):

            aluguer = Habita.objects.get(habitado_id =request.POST.get("aluguer"))
            casa = Habitacao.objects.get(hab_id=aluguer.habitacao_id.hab_id)

            gerencia = gere.objects.get(hab_id=aluguer.habitacao_id)

            senhorio = gerencia.senhorio_id

            nr_recibo = randint(1000000, 9999999)
            data = datetime.today()

            quantiaPaga = float(request.POST.get("valorPagar"))
            valor = float(aluguer.valorAPagar) - quantiaPaga

            pagamento = Pagamentos(senhorio_id=senhorio, inquilino_id=inquilino, habitacao_id= casa, valor=quantiaPaga, timestamp=data)
            pagamento.save()

            # Update do valorAPagar do aluguer
            aluguer.valorAPagar = valor
            aluguer.save(update_fields=['valorAPagar'])


            print("pagamento bem sucedido")
            dados = {"nr_recibo": nr_recibo, "senhorio": senhorio, "inquilino": inquilino, "pagamento": pagamento,
                     "titulo": aluguer.habitacao_id.titulo, 'userLogged': userLogged}

            return render(request, 'recibo_fatura.html', dados)

        if request.method == "POST" and request.POST.get("msg"):
            inquilino = Utilizador.objects.get(user_id=request.user.username)
            habitacao = Habitacao.objects.get(hab_id=request.POST.get("habitacao"))

            gerencia = gere.objects.get(hab_id=habitacao)

            senhorio = Senhorio.objects.get(user_id=gerencia.senhorio_id.user_id).user_id

            mensagem = request.POST.get("msg")

            try:
                chat = Chat.objects.get(
                    (Q(pessoa1=userLogged) | Q(pessoa2=userLogged)) & (Q(pessoa1=senhorio) | Q(pessoa2=senhorio)))

                # Se já houver um chat
                if chat:
                    mensagem = Mensagem(chat_id=chat, remetente_id=inquilino, destinatario_id=senhorio,
                                        conteudo=mensagem)
                    mensagem.save()

            # Se não houver um chat
            except:

                chatNovo = Chat(pessoa1=inquilino, pessoa2=senhorio)
                chatNovo.save()
                mensagem = Mensagem(chat_id=chatNovo, remetente_id=inquilino, destinatario_id=senhorio,
                                    conteudo=mensagem)
                mensagem.save()
            return render(request, 'alugueres.html', dados)


        if request.method == "POST" and request.POST.get("renov"):
            aluguer = Habita.objects.get(habitado_id=request.POST.get("aluguer"))
            gerencia = gere.objects.get(hab_id=aluguer.habitacao_id.hab_id)
            senhorio = Senhorio.objects.get(user_id=gerencia.senhorio_id.user_id)
            casa = Habitacao.objects.get(hab_id=aluguer.habitacao_id.hab_id)
            hospedes = request.POST.get("hospedes")
            entrada = request.POST.get("entrada")
            saida = request.POST.get("saida")

            #Verifica se já mandou um pedido
            pedido = Pedidos.objects.filter(user_id=userLogged, habitacao_id=casa)
            if not pedido:
                novoPedido = Pedidos(user_id = userLogged, senhorio_id = senhorio, habitacao_id= casa, data_entrada =entrada, data_saida= saida, n_hospedes=hospedes )
                novoPedido.save()

        return render(request, 'alugueres.html', dados)
    except:
        dados = {'userLogged': userLogged}
        return render(request, 'alugueres.html', dados)


def recibofatura(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    dados = {'userLogged': userLogged}
    return render(request, 'recibo_fatura.html', dados)

def reservationquest(request):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    pedidos = Pedidos.objects.filter(user_id=request.user.username);

    dados = {'userLogged': userLogged, 'pedidos': pedidos}

    if request.method == "POST":
        if "cancelar" in request.POST:
            pedidoId = request.POST.get("cancelar")

            # remover dos pedidos
            Pedidos.objects.filter(pedido_id=pedidoId).delete()

    return render(request, 'Reservationquests.html', dados)


def contacto(request):
    try:
        userLogged = Utilizador.objects.get(user_id=request.user.username)
        dados = {'userLogged': userLogged}

        if request.method == "POST":
            name = [request.POST.get("name")]
            email = [request.POST.get("email")]
            msg = [request.POST.get("message")]
            subject = name + email

            send_mail(
                ' '.join(subject),
                 msg[0],
                'ptiptr20202021@gmail.com',
                ['ptiptr20202021@gmail.com']
            )

            return redirect("../")

        return render(request, 'Contacto.html', dados)

    except:
        if request.method == "POST":
            name = [request.POST.get("name")]
            email = [request.POST.get("email")]
            msg = [request.POST.get("message")]
            subject = name + email

            send_mail(
                ' '.join(subject),
                msg[0],
                'ptiptr20202021@gmail.com',
                ['ptiptr20202021@gmail.com']
            )

            return redirect("../")

        return render(request, 'Contacto.html')


def FAQ(request):
    try:
        userLogged = Utilizador.objects.get(user_id=request.user.username)
        dados = {'userLogged': userLogged}
        return render(request, 'FAQ.html', dados)
    except:
        return render(request, 'FAQ.html')

def depoimentos(request):
    testimonials = Testimonial.objects.all()
    try:
        userLogged = Utilizador.objects.get(user_id=request.user.username)
        dados = {'userLogged': userLogged, 'testimonials': testimonials}

    except:
        dados = {'testimonials': testimonials}

    if request.method == "POST":
        comentario = request.POST.get("comentario")
        user_id = userLogged

        #guarda na tabela Testimonial
        Testimonial(user_id = user_id, comentario = comentario).save()

        return redirect("../")

    return render(request, 'depoimentos.html', dados)

def fazerReview(request, hab_id):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    habitacao = Habitacao.objects.get(hab_id=hab_id)
    dados = {'userLogged': userLogged, 'alojamento' : habitacao}

    if request.method == "POST":
        casa = habitacao
        inquilino_id = userLogged
        nome = request.POST.get("nome")
        comentario = request.POST.get("comentario")
        senhorio = request.POST.get("senhorio")
        limpeza =request.POST.get("limpeza")
        vizinhanca = request.POST.get("vizinhanca")
        inquilino = request.POST.get("inquilino")

        # Adiciona à tabela Avalia
        novaReview = Avalia(inquilino_id= inquilino_id, habitacao_id= casa, nome=nome, comentario=comentario, senhorio= senhorio, limpeza= limpeza, vizinhanca=vizinhanca, inquilino=inquilino )
        novaReview.save()

        return redirect("../verAlojamento/"+str(hab_id)+"?")

    return render(request, "fazerReview.html", dados)

def reviews_senhorio(request, hab_id):
    userLogged = Utilizador.objects.get(user_id=request.user.username)
    habitacao = Habitacao.objects.get(hab_id=hab_id)
    avalia_tabela = Avalia.objects.filter(habitacao_id=hab_id).count()
    avalia_tudo = Avalia.objects.filter(habitacao_id=hab_id)

    dados = {'userLogged': userLogged, 'comentarios_count': avalia_tabela, "dados_com":avalia_tudo, 'habitacao': habitacao}

    return render(request, "reviews_senhorio.html", dados)