from models import Utilizador, Habitacao, Habita, HistoricoReservas, gere, Senhorio
from datetime import date

def my_cron_job():
    #filtrar todos os habitas cujo atributo ate tem a data de hoje
    habitas = Habita.objects.filter(ate__date=date.today())

    for habita in habitas:
        #dados do inquilino
        inquilino = Habita.objects.get(inquilino_id= habita.inquilino_id)
        inquilinoUser = Utilizador.objects.get(user_id= inquilino.inquilino_id)

        #dados do senhorio
        gerencia = gere.objects.get(hab_id= habita.habitacao_id)
        senhorio = Senhorio.objects.get(user_id= gerencia.senhorio_id)
        senhorioUser = Utilizador.objects.get(user_id= senhorio.user_id)

        #habitacao
        habitacao = Habitacao.objects.get(hab_id= habita.habitacao_id)

        #adicionar a tabela Historico de reservas
        novoHistorico = HistoricoReservas(inquilino=inquilinoUser, senhorio= senhorioUser, habitacao= habitacao, desde=habita.desde, ate=habita.ate, renda= habita.renda, n_hospedes=habita.n_hospedes)
        novoHistorico.save()

        # apaga da tabela Habita
        Habita.objects.filter(habitado_id=habita.habitado_id).delete()