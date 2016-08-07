package languagefeature.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HelloProxy implements InvocationHandler {

	Hello hello;
	public static void main(String[] args) {
		HelloProxy hp = new HelloProxy(new HelloImpl());
		hp.getHello().sayHello();
		
		hp.getObject(Hello.class).sayHello();
	}

	public HelloProxy(Hello hello) {
		this.hello = hello;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// 通过字节码计数，生成一个代理类，实现了代理的接口，实现中调用了invoke方法
		System.out.println("Proxy :");
		System.out.println(proxy.getClass().getSimpleName());
		return method.invoke(hello, args);
	}
	
	public Hello getHello() {
		return (Hello)Proxy.newProxyInstance(
				Hello.class.getClassLoader(), new Class[]{Hello.class}, this);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(Class<T> c) {
		return (T)Proxy.newProxyInstance(
				c.getClassLoader(), new Class[]{c}, this);
	}

}
