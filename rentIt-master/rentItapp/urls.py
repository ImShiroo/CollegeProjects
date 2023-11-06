import rest_framework.authtoken.views
from django.urls import path, include
from django.contrib.auth import views as auth_views
from rest_framework import routers

from . import views, api_views

router = routers.DefaultRouter()
router.register('Newsletter', api_views.NewsletterViewSet)
router.register('Habitacao', api_views.HabitacaoViewSet)
router.register('Utilizador', api_views.UtilizadorViewSet)
router.register('Senhorio', api_views.SenhorioViewSet)
router.register('Inquilino', api_views.InquilinoViewSet)
router.register('Avalia', api_views.AvaliaViewSet)
router.register('Gere', api_views.GereViewSet)
router.register('Habita', api_views.HabitaViewSet)
router.register('Testimonial', api_views.TestimonialViewSet)
router.register('Pedidos', api_views.PedidosViewSet)
router.register('HistoricoReservas', api_views.HistoricoReservasViewSet)
router.register('Anunciosfav', api_views.AnunciosfavViewSet)
router.register('Chat', api_views.ChatViewSet)
router.register('Mensagem', api_views.MensagemViewSet)

urlpatterns = [
    path('', views.newsletter_signup, name='index'),
    path('register/', views.register, name="register"),
    path('login/', auth_views.LoginView.as_view(template_name='login.html'), name="login"),
    path('logout/', auth_views.LogoutView.as_view(template_name='logout.html'), name="logout"),
    path('index/', views.newsletter_signup, name="index"),
    path('edit_habitacao/<int:hab_id>', views.edit_habitacao, name="editHabitacao"),
    path('addHabitacao/', views.add_habitacao, name="addHabitacao"),
    path('managing/<int:hab_id>', views.managing, name="managing"),
    path('sobre/', views.sobre, name="sobre"),
    path('alojamentos/', views.alojamentos, name="alojamentos"),
    path('perfil/', views.perfil, name="perfil"),
    path('editPerfil/', views.edit_user, name="editPerfil"),
    path('my_alojamentos/', views.my_alojamentos, name="os_meus_alojamentos"),
    path('verAlojamento/<int:hab_id>', views.verAlojamento, name="verAlojamento"),
    path('favAnuncios/', views.anunciosfavoritos, name="favAnuncios"),
    path('histArrendamentos/', views.histarrendamentos, name="histArrendamentos"),
    path('histReservas/', views.histreservas, name="histReservas"),
    path('alugueres/', views.alugueres, name="alugueres"),
    path('recibofatura/', views.recibofatura, name="reciboFatura"),
    path('pedidosReserva/', views.reservationquest, name="pedidosReserva"),
    path('contacto/', views.contacto, name="contacto"),
    path('FAQ/', views.FAQ, name="FAQ"),
    path('depoimentos/', views.depoimentos, name="depoimentos"),
    path('fazerReview/<int:hab_id>', views.fazerReview, name="fazerReview"),
    path('reviewsSenhorio/<int:hab_id>', views.reviews_senhorio, name="reviewsSenhorio"),
    path("api/", include(router.urls)),
    path('api-token-auth/', rest_framework.authtoken.views.obtain_auth_token, name = 'api-token-auth')
]
