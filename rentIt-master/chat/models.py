from django.db import models

# Create your models here.
class Chat(models.Model):
    chat_id = models.AutoField(primary_key=True)
    pessoa1 = models.ForeignKey('rentItapp.Utilizador', on_delete=models.PROTECT, related_name='chat_pessoa1')
    pessoa2 = models.ForeignKey('rentItapp.Utilizador', on_delete=models.PROTECT, related_name='chat_pessoa2')

    def get_partner(self, user):
        if self.pessoa1 == user:
            return self.pessoa2
        return self.pessoa1

class Mensagem(models.Model):
    msg_id = models.AutoField(primary_key=True)
    chat_id = models.ForeignKey(Chat, on_delete=models.CASCADE)
    remetente_id = models.ForeignKey('rentItapp.Utilizador', on_delete=models.PROTECT, related_name='mensagem_remetente_id')
    destinatario_id = models.ForeignKey('rentItapp.Utilizador', on_delete=models.PROTECT, related_name='mensagem_destinatario_id')
    conteudo = models.CharField(blank=True, max_length=200)
    data_hora = models.DateTimeField(auto_now_add= True)