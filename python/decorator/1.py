def foo(*args,**kargs):
    print "foo function"
    for arg in args:
        print arg
    for k,v in kargs.iteritems():
        print k,":",v
        
def show_call(f):
    def shown(*args, **kwargs):
        print 'Calling', f.__name__
        return f(*args, **kwargs)
    return shown

show_call(foo("1","2","3",a="4",b="5"))
