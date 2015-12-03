__author__ = 'simu-hq'


class Singleton(object):
    def __init__(self, *args, **kwargs):
        print("init")
        super(Singleton, self).__init__(*args, **kwargs)

    def __new__(cls, *args, **kwargs):
        print("new", cls, "dd")
        return super(Singleton, cls).__new__(cls, *args, **kwargs)

    def __call__(self, *args, **kwargs):
        print("call")


o = Singleton()
o()
