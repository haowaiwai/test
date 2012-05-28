import os, sys
path = 'd:/test'
if path not in sys.path:
    sys.path.append(path)
sys.stdout = sys.stderr
os.environ['DJANGO_SETTINGS_MODULE'] = 'first.settings'
import django.core.handlers.wsgi
application = django.core.handlers.wsgi.WSGIHandler()
