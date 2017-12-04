package toolbox.output;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import toolbox.data.GameMemory;
import toolbox.errors.Exceptions;


/**
 * Simple logs output system. 
 * @author Damoy
 */
public class Logs {

	// counter of calls to Logs
	// not accurate but don't need
	private static long logCount = 0;
	// for prints method, to know if we have to call the getLogs() method
	private static boolean logsToPrint = true;
	// the date format used to output information
	private final static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	// the only StringBuffer used (== thread-safe StringBuilder)
	private static StringBuffer strBuffer = new StringBuffer();
	
	/**
	 * Gets the logs output: counter and date.
	 * Appends the o's toString() method.
	 * Closes the log output.
	 * Prints the buffer content with a now line to the output.
	 * Increments the logs counter
	 * @param o The generic object containing the toString() method
	 */
	public static void println(Object o){
		Exceptions.checkNotNull(o);
		checkGameMemoryBuffer();
		getLogs();
		strBuffer.append(o);
		strBuffer.append("]");
		System.out.println(strBuffer.toString());
		logCount++;
		clearStrBuffer();
	}
	
	/**
	 * Following are same than above for different input types
	 */
	
	public static void println(String s){
		return;
//		Exceptions.checkNotNull(s);
//		checkGameMemoryBuffer();
//		getLogs();
//		strBuffer.append(s);
//		strBuffer.append("]");
//		System.out.println(strBuffer.toString());
//		logCount++;
//		clearStrBuffer();
	}
	
	public static void println(float f){
		getLogs();
		strBuffer.append(f);
		strBuffer.append("]");
		System.out.println(strBuffer.toString());
		logCount++;
		clearStrBuffer();
	}
	
	public static void println(double d){
		getLogs();
		strBuffer.append(d);
		strBuffer.append("]");
		System.out.println(strBuffer.toString());
		logCount++;
		clearStrBuffer();
	}
	
	public static void println(int nb){
		getLogs();
		strBuffer.append(nb);
		strBuffer.append("]");
		System.out.println(strBuffer.toString());
		logCount++;
		clearStrBuffer();
	}
	
	public static void println(boolean b){
		getLogs();
		strBuffer.append(b);
		strBuffer.append("]");
		System.out.println(strBuffer.toString());
		logCount++;
		clearStrBuffer();
	}
	
	public static void printLn(){
		getLogs();
		strBuffer.append("]");
		System.out.println(strBuffer.toString());
		logCount++;
		clearStrBuffer();
	}
	
	public static void println(Object[] array){
		Exceptions.checkNotNull(array);
		for(int i = 0; i < array.length; i++){
			Exceptions.checkNotNull(array[i]);
			println(array[i].toString());
		}
	}
	
	public static void println(List<?> list){
		Exceptions.checkNotNull(list);
		for(Object o : list){
			Exceptions.checkNotNull(o);
			println(o.toString());
		}
	}
	
	/**
	 * Checks if it has to append the logs information (logs count, date)
	 * to the StringBuffer static object.
	 * Appends the o.toString() String.
	 * If space, adds a ' ' character.
	 * Increments the logs count.
	 * @param o The generic object containing the toString() method
	 * @param space add a ' ' character after the o.toString() call ?
	 */
	public static void print(Object o, boolean space){
		checkLogsPrint();
		strBuffer.append(o);
		if(space) space();
		logCount++;
	}
	
	/**
	 * Following are same than above for different input types
	 */
	
	public static void print(Object o){
		print(o, false);
	}
	
	/**
	 * Same as above but also checks if the String @param s given
	 * is a new line character.
	 * @param s a String object
	 * @param space add a ' ' character after the o.toString() call ?
	 */
	public static void print(String s, boolean space){
		if(s.equals("\n") || s.equals('\n')){
			ln();
			return;
		}
		checkLogsPrint();
		strBuffer.append(s);
		if(space) space();
		logCount++;
	}
	
	public static void print(String s){
		print(s, false);
	}
	
	public static void print(float f, boolean space){
		checkLogsPrint();
		strBuffer.append(f);
		if(space) space();
		logCount++;
	}
	
	public static void print(float f){
		print(f, false);
	}
	
	public static void print(double d, boolean space){
		checkLogsPrint();
		strBuffer.append(d);
		if(space) space();
		logCount++;
	}
	
	public static void print(double d){
		print(d, false);
	}
	
	public static void print(int nb, boolean space){
		checkLogsPrint();
		strBuffer.append(nb);
		if(space) space();
		logCount++;
	}
	
	public static void print(int nb){
		print(nb, false);
	}
	
	public static void print(boolean b, boolean space){
		checkLogsPrint();
		strBuffer.append(b);
		if(space) space();
		logCount++;
	}
	
	public static void print(boolean b){
		print(b, false);
	}
	
	/**
	 * Outputs all elements of a generic list
	 * into the same log.
	 * @param list the List<?> list
	 */
	public static void printList(List<?> list){
		checkLogsPrint();
		strBuffer.append("List: [");
		
		for(int i = 0; i < list.size(); i++){
			strBuffer.append(list.get(i));
			
			if(i < list.size() - 1){
				strBuffer.append(", ");
			}
			
			if(i == list.size() - 1){
				strBuffer.append(']');
			}
		}
		
		ln(true);
	}
	
	/**
	 * Adds a space character to the Logs output
	 */
	public static void space(){
		strBuffer.append(' ');
	}
	
	public static void ln(){
		ln(false);
	}
	
	/**
	 * First, if @param append adds a ']' character
	 * to end the log output.
	 * Prints the buffer content and clear it.
	 * @param append should we end the log with ']' ?
	 */
	public static void flush(boolean endLog){
		if(endLog){
			strBuffer.append(']');
		}
		endPrintAndClear();
	}
	
	/**
	 * Basically a flush with a '\n' character added.
	 * Adds a '\n' character == adds a 'goto' new line.
	 * Prints the buffer content and clear it.
	 * @param appendEndChar should we end the log with ']' ?
	 */
	public static void ln(boolean appendEndChar){
		if(appendEndChar){
			strBuffer.append(']');
		}
		strBuffer.append('\n');
		endPrintAndClear();
	}
	
	/**
	 * Prints the buffer content.
	 * Reset logsToPrint boolean.
	 * Clear the buffer. 
	 */
	private static void endPrintAndClear(){
		System.out.print(strBuffer.toString());
		logsToPrint = true;
		clearStrBuffer();
	}
	
	/**
	 * If needed, adds the logs 'header' to
	 * the buffer content.
	 */
	private static void checkLogsPrint(){
		if(logsToPrint){
			getLogs();
			logsToPrint = false;
		}
	}
	
	private static void checkGameMemoryBuffer(){
		if(GameMemory.isOutputStringBufferEmpty()) return;
		GameMemory.resetOutputStringBuffer();
	}
	
	/**
	 * Clears the buffer.
	 * Appends the header content to it.
	 */
	private static void getLogs(){
		clearStrBuffer();
		strBuffer.append("[Logs:");
		strBuffer.append(logCount);
		strBuffer.append(" | ");
		strBuffer.append(getDate());
		strBuffer.append(" | ");
	}
	
	/**
	 * Gets the current data formatted by @param dateFormat.
	 */
	private static String getDate(){
		return dateFormat.format(new Date());
	}
	
	/**
	 * Resets a StringBuffer static object. 
	 */
	private static void clearBuffer(StringBuffer buffer){
		if(buffer.length() == 0) return;
		buffer.setLength(0);
	}
	
	/**
	 * Resets the strBuffer StringBuffer static object. 
	 */
	private static void clearStrBuffer(){
		clearBuffer(strBuffer);
	}
	
	public static long getLogsCount(){
		return logCount;
	}
	/**
	 * Resets the Logs:
	 * 	- resets the logs counter
	 * 	- resets the buffer
	 *  - resets the logsToPrint boolean 
	 */
	public static void reset(){
		logCount = 0;
		clearStrBuffer();
		logsToPrint = true;
	}
}
