# Create your views here.
from django.template import Context, loader
from django.http import HttpResponse
from first.models import User

def index(request):
    latest_poll_list = User.objects.all()
    t = loader.get_template('first/index.html')
    c = Context({
        'latest_poll_list': latest_poll_list,
    })
    return HttpResponse(t.render(c))
