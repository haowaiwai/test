from pad.models import User
from django.contrib import admin

class UserAdmin(admin.ModelAdmin):
	list_display = ('username', 'pwd')
	search_fields = ('username','content')
	
admin.site.register(User,UserAdmin)