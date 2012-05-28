import os, sys
path = 'd:/test/center'
if path not in sys.path:
    sys.path.append(path)
os.environ['DJANGO_SETTINGS_MODULE'] = 'center.settings'

import django.core.handlers.wsgi

application = django.core.handlers.wsgi.WSGIHandler()
