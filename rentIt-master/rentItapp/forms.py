from django import forms
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.models import User
from .models import Habitacao, Utilizador, Pedidos, Avalia, Testimonial


class UserRegisterForm(UserCreationForm):
    email = forms.EmailField()

    class Meta:
        model = User
        fields = ['username', 'email', 'password1', 'password2']


class addHabitacaoForm(forms.ModelForm):
    class Meta:
        model = Habitacao
        fields = ('titulo', 'estado', 'descricao', 'fumador', 'tipo', 'morada', 'wc', 'quartos', 'area', 'sol', 'preco',
                  'faixa_etaria', 'animais', 'genero', 'foto', 'wifi', 'limpeza', 'ar_condicionado', 'televisao',
                  'fogao', 'frigorifico', 'metro', 'autocarro', 'extras')

class userEditForm(forms.ModelForm):
    class Meta:
        model = Utilizador
        fields = ('nascimento', 'foto', 'email', 'nome', 'nif', 'genero', 'localidade', 'lingua')

class addPedidoForm(forms.ModelForm):
    class Meta:
        model = Pedidos
        fields = ('user_id', 'senhorio_id', 'habitacao_id', 'data_entrada', 'data_saida', 'n_hospedes')

class contactForm(forms.Form):
    class Meta:
        fields = ('name', 'email', 'message')

class reviewForm(forms.ModelForm):
    class Meta:
        model = Avalia
        fields = ("nome", "comentario", "senhorio", "limpeza", "vizinhanca", "inquilino")

class depoimentoForm(forms.Form):
    class Meta:
        model = Testimonial
        fields = ("comentario")