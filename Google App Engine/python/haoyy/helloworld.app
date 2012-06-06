import webapp2
from google.appengine.api import memcache

class MainPage(webapp2.RequestHandler):
    def get(self):
        self.response.headers['Content-Type'] = 'text/html'
    	greetings = self.get_greetings()
    	stats = memcache.get_stats()
    	self.response.out.write("<html><body>")
    	self.response.out.write("<b>Cache Hits:%s</b><br>" % stats['hits'])
    	self.response.out.write("<b>Cache Misses:%s</b><br><br>" % stats['misses'])
    	self.response.out.write(greetings)
        self.response.out.write('Hello, webapp World!')
        self.response.out.write("</body></html>")

    def get_greetings(self):
        greetings = memcache.get("greetings")
        if greetings is not None:
            return greetings
        else:
            if not memcache.add("greetings", "1", 10):
                logging.error("Memcache set failed.")
            return greetings

app = webapp2.WSGIApplication([('/', MainPage)],
                              debug=True)
