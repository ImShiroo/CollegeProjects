from rest_framework import serializers
from .models import *
from chat.models import *


class NewsletterSerializer(serializers.ModelSerializer):
    class Meta:
        model = Newsletter
        fields = ("email",)


class UtilizadorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Utilizador
        fields = ("user_id", "nascimento", "email", "nome", "nif", "foto", "genero", "localidade", "lingua")


class HabitacaoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Habitacao
        fields = ("hab_id", "titulo", "fumador", "tipo", "morada", "quartos", "wc", "area",
                  "sol", "preco", "faixa_etaria", "animais", "genero", "foto",
                  "wifi", "limpeza", "ar_condicionado", "televisao", "fogao",
                  "frigorifico", "metro", "autocarro", "extras",
                  "descricao")


class InquilinoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Inquilino
        fields = ("user_id",)


class SenhorioSerializer(serializers.ModelSerializer):
    class Meta:
        model = Senhorio
        fields = ("user_id",)


class AvaliaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Avalia
        fields = ("avalia_id", "inquilino_id", "habitacao_id", "nome", "comentario", "senhorio", "limpeza",
                  "vizinhanca", "inquilino")


class ContactaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Contacta
        fields = ("contact_id", "inquilino_id", "senhorio_id", "mensagem", "data")


class GereSerializer(serializers.ModelSerializer):
    class Meta:
        model = gere
        fields = ("gere_id", "hab_id", "senhorio_id")


class HabitaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Habita
        fields = ("habitado_id", "inquilino_id", "habitacao_id", "desde", "ate", "renda", "dia_pagamento", "n_hospedes")


class TestimonialSerializer(serializers.ModelSerializer):
    class Meta:
        model: Testimonial
        fields = ("user_id", "comentario")


class PedidosSerializer(serializers.ModelSerializer):
    class Meta:
        model: Pedidos
        fields = ("pedido_id", "user_id", "senhorio_id", "habitacao_id", "data_entrada", "data_saida", "n_hospedes")


class HistoricoReservasSerializer(serializers.ModelSerializer):
    class Meta:
        model: HistoricoReservas
        fields = ("hist_id", "inquilino", "senhorio", "habitacao", "desde", "ate", "renda", "n_hospedes")


class AnunciosfavSerializer(serializers.ModelSerializer):
    class Meta:
        model: Anunciosfav
        fields = ("fav_id", "user_id", "habitacao_id")


class PagamentoSerializer(serializers.ModelSerializer):
    class Meta:
        model: Anunciosfav
        fields = ("pedido_id", "user_id", "senhorio_id", "data_entrada", "data_saida", "n_hospedes")


# Serializers da app chat

class ChatSerializer(serializers.ModelSerializer):
    class Meta:
        model: Chat
        fields = ("chat_id", "pessoa1", "pessoa2")


class MensagemSerializer(serializers.ModelSerializer):
    class Meta:
        model: Mensagem
        fields = ("msg_id", "chat_id", "remetente_id", "destinatario_id", "conteudo", "data_hora")
