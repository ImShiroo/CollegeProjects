from rest_framework import viewsets
from rest_framework.permissions import IsAdminUser
from .serializers import *
from chat.models import *

class NewsletterViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Newsletter.objects.all()
    serializer_class = NewsletterSerializer


class UtilizadorViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Utilizador.objects.all()
    serializer_class = UtilizadorSerializer
    http_method_names = ["get", "head", "patch", "put"]


class HabitacaoViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Habitacao.objects.all()
    serializer_class = HabitacaoSerializer


class InquilinoViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Inquilino.objects.all()
    serializer_class = InquilinoSerializer
    http_method_names = ["get", "head", "delete"]


class SenhorioViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Senhorio.objects.all()
    serializer_class = SenhorioSerializer
    http_method_names = ["get", "head", "delete"]


class AvaliaViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Avalia.objects.all()
    serializer_class = AvaliaSerializer


class GereViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = gere.objects.all()
    serializer_class = GereSerializer


class HabitaViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Habita.objects.all()
    serializer_class = HabitaSerializer


class TestimonialViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Testimonial.objects.all()
    serializer_class = TestimonialSerializer


class PedidosViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Pedidos.objects.all()
    serializer_class = PedidosSerializer


class HistoricoReservasViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = HistoricoReservas.objects.all()
    serializer_class = HistoricoReservasSerializer


class AnunciosfavViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Anunciosfav.objects.all()
    serializer_class = AnunciosfavSerializer


class PagamentoViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Pagamentos.objects.all()
    serializer_class = PagamentoSerializer
    http_method_names = ["get", "head", "delete"]


###viewsets da app Chat

class ChatViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Chat.objects.all()
    serializer_class = ChatSerializer


class MensagemViewSet(viewsets.ModelViewSet):
    permission_classes = [IsAdminUser]
    queryset = Mensagem.objects.all()
    serializer_class = MensagemSerializer
