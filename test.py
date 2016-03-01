# 对方法进行配置
def make_addr(addend):
    def adder(augend):
        return augend + addend

    return adder


p = make_addr(23)
q = make_addr(44)

print(p(100))
print(q(100))


def hellocounter(name):
    count = 0

    def counter():
        nonlocal count
        count += 1
        print("hello," + name + "," + str(count) + "access !")
    return counter


hello = hellocounter("A Hqdo")

hello()
hello()
hello()
