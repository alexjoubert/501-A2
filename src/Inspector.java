import java.lang.Class;
import java.lang.reflect.*;
import java.io.*;
import java.util.List;

import javax.annotation.PostConstruct;

import java.util.ArrayList;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		System.out.print("Declaring class: "); 
		printDeclaringClass(obj);

		System.out.print("Superclass: ");
		printSuperClass(obj);

		System.out.print("Interfaces: ");
		printInterfaceNames(obj);

		System.out.println("Methods:");
		printMethods(obj);
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

	private static Method[] getMethods(Object obj){
		List<Method> result = new ArrayList<Method>();

		for (Class c = obj.getClass(); c != null; c = c.getSuperclass()) {
			for (Method method : c.getDeclaredMethods()) {
				if (method.getAnnotation(PostConstruct.class) != null) {
					result.add(method);
				}
			}
		}
		return result.toArray(new Method[result.size()]); 
	}

	private static void printMethods(Object obj){
		Method[] methArr = getMethods(obj);

		for (int i = 0; i < methArr.length; i++) {
			Method methVal = methArr[i];

			Class[] exVal = methArr[i].getExceptionTypes();
			StringBuffer exs = new StringBuffer();
			for (int j = 0; j < exVal.length; j++) {
				if (j > 0)
					exs.append(", ");
				exs.append(exVal[j].getName());
			}

			Class[] paramVal = methVal.getParameterTypes();
			StringBuffer params = new StringBuffer();
			for (int k = 0; k < paramVal.length; k++) {
				if (k > 0)
					params.append(", ");
				params.append(paramVal[k].getName());
			}

			Class returnVal = methVal.getReturnType();

			int mods = methVal.getModifiers();
			String modVal = Modifier.toString(mods);

			System.out.println("Method: " + methVal.getName() + "()");
			System.out.println("Exceptions: " + exVal);
			System.out.println("Parameters: "  + params);
			System.out.println("Return Type: " + returnVal.getName());
			System.out.println("Modifiers: " + modVal + "\n");
		}
	}

	private static Constructor[] getConstructors(Object obj){
		List<Constructor> result = new ArrayList<Constructor>();

		for (Class c = obj.getClass(); c != null; c = c.getSuperclass()) {
			for (Constructor constructor : c.getDeclaredConstructors()) {
				result.add(constructor);
			}
		}
		return result.toArray(new Constructor[result.size()]); 
	}

	private static void printConstructors(Object obj){
		Constructor[] conArr = getConstructors(obj);

		for (int i = 0; i < conArr.length; i++) {
			Constructor conVal = conArr[i];

			Class[] paramVal = conVal.getParameterTypes();
			StringBuffer params = new StringBuffer();
			for (int j = 0; j < paramVal.length; j++) {
				if (j > 0)
					params.append(", ");
				params.append(paramVal[j].getName());
			}

			int mods = conVal.getModifiers();
			String modVal = Modifier.toString(mods);
			
			System.out.println("Constructor: " + conVal.getName() + "()");
			System.out.println("Parameters: "  + params);
			System.out.println("Modifiers: " + modVal + "\n");
		}
	}

	private static Field[] getFields(Object obj){
		List<Field> result = new ArrayList<Field>();

		for (Class c = obj.getClass(); c != null; c = c.getSuperclass()) {
			for (Field field : c.getDeclaredFields()) {
				result.add(field);
			}
		}
		return result.toArray(new Field[result.size()]); 
	}
	
	private static void printFields(Object obj){
		Field[] fieldArr = getFields(obj);

		for (int i = 0; i < fieldArr.length; i++) {
			Field fieldVal = fieldArr[i];
			
			Class typeVal = fieldVal.getType();

			int mods = fieldVal.getModifiers();
			String modVal = Modifier.toString(mods);
			
			System.out.println("Constructor: " + fieldVal.getName() + "()");
			System.out.println("Type: "  + typeVal.getName());
			System.out.println("Modifiers: " + modVal + "\n");
		}
	}
}
