package languagefeature.classloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestClassLoader extends ClassLoader {

    protected Class<?> findClass(String name) throws ClassNotFoundException {
    	// 正常情况此方法不会被调用，因为双亲委派机制的原因，类被父类AppClassLoader加载了
    	String path = name.replace('.', File.separatorChar);
    	byte[] bytes;
		try {
			bytes = toByteArray2(this.getClass().getResource("/").getPath()+path+".class");
			return super.defineClass(name, bytes, 0, bytes.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public static void main(String[] args) {
    	TestClassLoader testClassLoader = new TestClassLoader();
    	System.out.println(testClassLoader.getParent());// AppClassLoader
    	try {
    		// resolveClass 不会被调用，因此不会进行链接动作，不会输出static代码块中的日志
//			testClassLoader.loadClass("languagefeature.classloader.Test");
    		// 会进行链接动作，因此会输出static代码块中的日志
    		Class.forName("languagefeature.classloader.Test");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
    public byte[] toByteArray2(String filename)throws IOException {  
        
        File f = new File(filename);  
        if(!f.exists()){  
            throw new FileNotFoundException(filename);  
        }  
          
        FileChannel channel = null;  
        FileInputStream fs = null;  
        try{  
            fs = new FileInputStream(f);  
            channel = fs.getChannel();  
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());  
            while((channel.read(byteBuffer)) > 0){  
            }  
            return byteBuffer.array();  
        }catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        }finally{  
            try{  
                channel.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            try{  
                fs.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public static byte[] toByteArray(String filename) throws IOException{  
        
        File f = new File(filename);  
        if(!f.exists()){  
            throw new FileNotFoundException(filename);  
        }  
  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());  
        BufferedInputStream in = null;  
        try{  
            in = new BufferedInputStream(new FileInputStream(f));  
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while(-1 != (len = in.read(buffer,0,buf_size))){  
                bos.write(buffer,0,len);  
            }  
            return bos.toByteArray();  
        }catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        }finally{  
            try{  
                in.close();  
            }catch (IOException e) {  
                e.printStackTrace();  
            }  
            bos.close();  
        }  
    } 
}
