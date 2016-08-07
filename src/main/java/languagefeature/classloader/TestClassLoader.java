package languagefeature.classloader;

public class TestClassLoader extends ClassLoader {

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        throw new ClassNotFoundException(name);
    }
}
