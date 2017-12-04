package toolbox.errors;

/**
 * Contains some launches of exceptions.
 * @author Damoy
 */
public class Exceptions {
	
	public static void checkNotNull(Object o){
		if(o == null) throw new IllegalArgumentException("Object argument is null !");
	}
	
	public static void throwIllegalArgument(String str){
		throw new IllegalArgumentException(str);
	}
	
}
