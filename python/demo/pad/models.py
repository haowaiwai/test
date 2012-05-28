from django.db import models
from django.contrib import admin

# Create your models here.
class User(models.Model):
	username = models.CharField(max_length=20)
	pwd = models.CharField(max_length=20)
