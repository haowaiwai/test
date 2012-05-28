# Create your views here.
from django.http import HttpResponse
from pad.models import User

def login(request):
	if 'username' in request.GET and request.GET['username']:
		username = request.GET['username']
	if 'pwd' in request.GET and request.GET['pwd']:
		pwd = request.GET['pwd']
	if username and pwd:
		return HttpResponse("username %s %s" % (username,pwd))
	else:
		return HttpResponse("null")