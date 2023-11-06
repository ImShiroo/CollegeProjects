from django.db import models
from django.utils import timezone


class Utilizador(models.Model):
    user_id = models.CharField(max_length=30, primary_key=True)
    nascimento = models.DateField(null=True)
    email = models.CharField(max_length=30)
    nome = models.CharField(blank=True, null=True, max_length=50)
    nif = models.IntegerField(blank=True, null=True)
    foto = models.ImageField(blank=True, upload_to='images/users/', default='images/users/user.svg')
    genero = models.CharField(blank=True, null=True,max_length=50)
    localidade = models.CharField(blank=True,null=True, max_length=50)
    lingua = models.CharField(blank=True, null=True,max_length=50)

    def get_user_id(self):
        return self.user_id

class Admin(models.Model):
    user_id = models.ForeignKey(Utilizador, on_delete=models.CASCADE)

class Inquilino(models.Model):
    user_id = models.ForeignKey(Utilizador, on_delete=models.CASCADE)

class Senhorio(models.Model):
    user_id = models.ForeignKey(Utilizador, on_delete=models.CASCADE)

class Habitacao(models.Model):
    hab_id = models.AutoField(primary_key=True)
    estado = models.BooleanField(default=True)
    titulo = models.CharField(max_length=100)
    fumador = models.BooleanField(default=False)
    tipo = models.CharField(max_length=40)
    morada = models.CharField(max_length=100)
    wc = models.IntegerField()
    quartos = models.IntegerField()
    area = models.IntegerField()
    sol = models.CharField(max_length=1)
    preco = models.FloatField()
    faixa_etaria = models.CharField(max_length=10)
    animais = models.BooleanField(default=False)
    genero = models.CharField(max_length=30)
    foto = models.ImageField(blank=True, upload_to='images/alojamentos/', default='images/alojamentos/default.png')
    wifi = models.BooleanField(default=False)
    limpeza = models.BooleanField(default=False)
    ar_condicionado = models.BooleanField(default=False)
    televisao = models.BooleanField(default=False)
    fogao = models.BooleanField(default=False)
    frigorifico = models.BooleanField(default=False)
    metro = models.BooleanField(default=False)
    autocarro = models.BooleanField(default=False)
    extras = models.CharField(blank=True, max_length=200, default="Nenhum")
    descricao = models.CharField(max_length=300)

class gere(models.Model):
    gere_id = models.AutoField(primary_key=True)
    hab_id = models.ForeignKey(Habitacao, on_delete=models.PROTECT)
    senhorio_id = models.ForeignKey(Senhorio, on_delete=models.PROTECT)

class Habita(models.Model):
    habitado_id = models.AutoField(primary_key=True)
    inquilino_id = models.ForeignKey(Inquilino, on_delete=models.PROTECT)
    habitacao_id = models.ForeignKey(Habitacao, on_delete=models.PROTECT)
    desde = models.DateField()
    ate = models.DateField()
    renda = models.IntegerField()
    valorAPagar = models.FloatField()
    dia_pagamento = models.IntegerField()
    n_hospedes = models.IntegerField(default=1)

class Avalia(models.Model):
    avalia_id = models.AutoField(primary_key=True)
    inquilino_id = models.ForeignKey(Utilizador, on_delete=models.PROTECT)
    habitacao_id = models.ForeignKey(Habitacao, on_delete=models.PROTECT)
    nome = models.CharField(max_length=100)
    comentario = models.CharField(max_length=500)
    senhorio = models.IntegerField()
    limpeza = models.IntegerField()
    vizinhanca = models.IntegerField()
    inquilino = models.IntegerField()

class Contacta(models.Model):
    contact_id = models.AutoField(primary_key=True)
    inquilino_id = models.ForeignKey(Inquilino, on_delete=models.PROTECT)
    senhorio_id = models.ForeignKey(Senhorio, on_delete=models.PROTECT)
    mensagem = models.CharField(max_length=500)
    data = models.DateTimeField(default=timezone.now)


class Pagamentos(models.Model):
    pagamento_id = models.AutoField(primary_key=True)
    senhorio_id = models.ForeignKey(Senhorio, on_delete=models.PROTECT)
    inquilino_id = models.ForeignKey(Inquilino, on_delete=models.PROTECT)
    habitacao_id = models.ForeignKey(Habitacao, on_delete=models.PROTECT)
    valor = models.FloatField()
    timestamp = models.DateTimeField()



class Testimonial(models.Model):
    testimonial_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Utilizador, on_delete=models.PROTECT)
    comentario = models.CharField(max_length=500)

class Pedidos(models.Model):
    pedido_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Utilizador, on_delete=models.PROTECT)
    senhorio_id = models.ForeignKey(Senhorio, on_delete=models.PROTECT)
    habitacao_id = models.ForeignKey(Habitacao, on_delete=models.PROTECT)
    data_entrada = models.DateField(null=True)
    data_saida = models.DateField(null=True)
    n_hospedes = models.IntegerField(default=1)

class HistoricoReservas(models.Model):
    hist_id = models.AutoField(primary_key=True)
    inquilino = models.ForeignKey(Utilizador, on_delete=models.PROTECT, related_name='inquilinoHist')
    senhorio = models.ForeignKey(Utilizador, on_delete=models.PROTECT, related_name='senhorioHist')
    habitacao = models.ForeignKey(Habitacao, on_delete=models.PROTECT, related_name='habitacao')
    desde = models.DateField(null=True)
    ate = models.DateField(null=True)
    renda = models.IntegerField(null=True)
    n_hospedes = models.IntegerField(default=1)


class Anunciosfav(models.Model):
    fav_id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(Utilizador, on_delete=models.PROTECT)
    habitacao_id = models.ForeignKey(Habitacao, on_delete=models.CASCADE)


class Newsletter(models.Model):
    email = models.CharField(max_length=100, primary_key=True)

    def __str__(self):
        return self.email




