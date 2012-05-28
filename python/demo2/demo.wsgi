import os, sys
path = 'D:\\test'
if path not in sys.path:
    sys.path.append(path)
path = 'D:\\test\\demo'
if path not in sys.path:
    sys.path.append(path)
print sys.path
sys.stdout = sys.stderr
os.environ['DJANGO_SETTINGS_MODULE'] = 'demo.settings'
import django.core.handlers.wsgi
application = django.core.handlers.wsgi.WSGIHandler()
