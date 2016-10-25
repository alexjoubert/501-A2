import java.lang.Class;
import java.lang.reflect.*;
import java.io.*;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		System.out.print("Declaring class: "); 
		printDeclaringClass(obj);
		
		System.out.print("Superclass: ");
		printSuperClass(obj);
		
		System.out.print("Interfaces: ");
		printInterfaceNames(obj);
	}
	
	private static void printDeclaringClass(Object obj) {
		Class c = obj.getClass();
		System.out.println(c.getName());
	}

	private static void printSuperClass(Object obj) {
		try{
			Class sClass = obj.getClass().getSuperclass(); 
			System.out.println(sClass.getName());
		}catch(Exception e){System.out.println(e);}
	}
	
	private static void printInterfaceNames(Object obj) {
		try{
			Class[] interfaces = obj.getClass().getInterfaces(); 
			for (int i = 0; i < interfaces.length; i++) {
		         String interfaceName = interfaces[i].getName();
		         if (i != interfaces.length - 1)
		        	 System.out.print(interfaceName + ", ");
		         else
		        	 System.out.println(interfaceName);
			}
		}catch(Exception e){System.out.println(e);}
	}
}
