# Generated by Django 3.1.4 on 2021-05-21 02:52

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('rentItapp', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='Chat',
            fields=[
                ('chat_id', models.AutoField(primary_key=True, serialize=False)),
                ('pessoa1', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='chat_pessoa1', to='rentItapp.utilizador')),
                ('pessoa2', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='chat_pessoa2', to='rentItapp.utilizador')),
            ],
        ),
        migrations.CreateModel(
            name='Mensagem',
            fields=[
                ('msg_id', models.AutoField(primary_key=True, serialize=False)),
                ('conteudo', models.CharField(blank=True, max_length=200)),
                ('data_hora', models.DateTimeField(auto_now_add=True)),
                ('chat_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='chat.chat')),
                ('destinatario_id', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='mensagem_destinatario_id', to='rentItapp.utilizador')),
                ('remetente_id', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, related_name='mensagem_remetente_id', to='rentItapp.utilizador')),
            ],
        ),
    ]
