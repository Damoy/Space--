package toolbox.data;

import java.util.HashMap;
import java.util.Map;

public class GameMemory {

	public final static StringBuffer OUTPUT_STRING_BUFFER = new StringBuffer();
	public final static StringBuffer WINDOW_STRING_BUFFER = new StringBuffer();

	public static boolean hasToResetOutputStringBuffer = false;
	public static boolean hasToResetWinStringBuffer = false;

	private static Map<StringBuffer, Boolean> bufferMemory = new HashMap<StringBuffer, Boolean>() {
		private static final long serialVersionUID = -4379573685477770128L;
		{
			put(OUTPUT_STRING_BUFFER, hasToResetOutputStringBuffer);
			put(WINDOW_STRING_BUFFER, hasToResetWinStringBuffer);
		}
	};

	/**
	 * Has to be called each main game loop update
	 */
	public static void update() {
		if (hasToResetOutputStringBuffer) {
			resetOutputStringBuffer();
		}

		if (hasToResetWinStringBuffer) {
			resetWindowStringBuffer();
		}
	}

	private final static StringBuffer resetStringBuffer(StringBuffer bufferToReset, boolean copyBuffer) {
		if (bufferToReset.length() == 0)
			return null;
		StringBuffer copy = null;
		if (copyBuffer)
			copy = new StringBuffer(bufferToReset);
		// reset current
		bufferToReset.setLength(0);
		bufferMemory.put(bufferToReset, false);
		return copy;
	}

	public final static StringBuffer resetOutputStringBuffer(boolean copyBuffer) {
		return resetStringBuffer(OUTPUT_STRING_BUFFER, copyBuffer);
	}

	public final static StringBuffer resetWindowStringBuffer(boolean copyBuffer) {
		return resetStringBuffer(WINDOW_STRING_BUFFER, copyBuffer);
	}

	public final static StringBuffer resetOutputStringBuffer() {
		return resetOutputStringBuffer(false);
	}

	public final static StringBuffer resetWindowStringBuffer() {
		return resetWindowStringBuffer(false);
	}

	private final static String getBufferContentAndReset(StringBuffer buffer) {
		bufferMemory.put(buffer, true);
		return buffer.toString();
	}

	public final static String getOutputBufferContentAndReset() {
		return getBufferContentAndReset(OUTPUT_STRING_BUFFER);
	}

	public final static String getWindowBufferContentAndReset() {
		return getBufferContentAndReset(WINDOW_STRING_BUFFER);
	}

	private static boolean isStringBufferEmpty(StringBuffer buffer) {
		return buffer.length() == 0;
	}

	public static boolean isOutputStringBufferEmpty() {
		return isStringBufferEmpty(OUTPUT_STRING_BUFFER);
	}

	public static boolean isWindowStringBufferEmpty() {
		return isStringBufferEmpty(WINDOW_STRING_BUFFER);
	}
}
