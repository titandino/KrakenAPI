package com.darkan.api.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.darkan.api.entity.NPC;
import com.darkan.api.item.GroundItem;
import com.darkan.api.pathing.EntityStrategy;
import com.darkan.api.pathing.FixedTileStrategy;
import com.darkan.api.pathing.ObjectStrategy;
import com.darkan.api.pathing.LocalPathing;
import com.darkan.api.profile.PlayerProfiles;
import com.darkan.api.world.ObjectType;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;
import com.google.common.reflect.ClassPath;

import kraken.plugin.api.Debug;

public class Utils {
	
private static final Random RANDOM = new SecureRandom();
	
	public static int gaussian(int mean, int variance) {
		return (int) (RANDOM.nextGaussian() * Math.sqrt(variance * PlayerProfiles.get().gaussVariance) + mean);
	}
	
	public static final int getDistanceI(int coordX1, int coordY1, int coordX2, int coordY2) {
		int deltaX = coordX2 - coordX1;
		int deltaY = coordY2 - coordY1;
		return ((int) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
	}

	public static final int random(int min, int max) {
		final int n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : random(n));
	}
	
	public static final int random(int maxValue) {
		if (maxValue <= 0)
			return 0;
		return RANDOM.nextInt(maxValue);
	}
	
	public static List<Class<?>> getClassesWithAnnotation(ClassLoader loader, String packageName, Class<? extends Annotation> annotation) throws ClassNotFoundException, IOException {
		ClassPath cp = ClassPath.from(loader != null ? loader : Thread.currentThread().getContextClassLoader());
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (ClassPath.ClassInfo info : packageName != null ? cp.getTopLevelClassesRecursive(packageName) : cp.getTopLevelClasses()) {
			try {
				Debug.log("Checking " + info.getName());
				if (!Class.forName(info.getName()).isAnnotationPresent(annotation))
					continue;
				classes.add(Class.forName(info.getName()));
			} catch(Throwable e) {
				
			}
		}
		return classes;
	}

	public static int toFilesystemHash(String string) {
		int size = string.length();
		char c = 0;
		for (int index = 0; index < size; index++)
			c = (char) ((c << 5) - c + charToCp1252(string.charAt(index)));
		return c;
	}

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	private static char[] CP1252_VALS = new char[] { '\u20ac', '\u0000', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021', '\u02c6', '\u2030', '\u0160', '\u2039', '\u0152', '\u0000', '\u017d', '\u0000', '\u0000', '\u2018', '\u2019', '\u201c', '\u201d', '\u2022', '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\u0000', '\u017e', '\u0178' };

	public static char cp1252ToChar(byte i) {
		int cp1252 = i & 0xff;
		if (cp1252 == 0)
			throw new IllegalArgumentException("Non cp1252 character provided");
		if (cp1252 >= 128 && cp1252 <= 159) {
			int translated = CP1252_VALS[cp1252 - 128];
			if (translated == 0) {
				translated = 63;
			}
			cp1252 = (char) translated;
		}
		return (char) cp1252;
	}

	public static byte charToCp1252(char c) {
		if (c > 0 && c < '\u0080' || c >= '\u00a0' && c <= '\u00ff')
			return (byte) c;

		return switch (c) {
			case '\u20ac' -> -128;
			case '\u201a' -> -126;
			case '\u0192' -> -125;
			case '\u201e' -> -124;
			case '\u2026' -> -123;
			case '\u2020' -> -122;
			case '\u2021' -> -121;
			case '\u02c6' -> -120;
			case '\u2030' -> -119;
			case '\u0160' -> -118;
			case '\u2039' -> -117;
			case '\u0152' -> -116;
			case '\u017d' -> -114;
			case '\u2018' -> -111;
			case '\u2019' -> -110;
			case '\u201c' -> -109;
			case '\u201d' -> -108;
			case '\u2022' -> -107;
			case '\u2013' -> -106;
			case '\u2014' -> -105;
			case '\u02dc' -> -104;
			case '\u2122' -> -103;
			case '\u0161' -> -102;
			case '\u203a' -> -101;
			case '\u0153' -> -100;
			case '\u017e' -> -98;
			case '\u0178' -> -97;
			default -> 63;
		};
	}

	public static void putSmartInt(ByteBuffer buffer, int value) {
		if (value >= Short.MAX_VALUE) {
			buffer.putInt(value - Integer.MAX_VALUE - 1);
		} else {
			if (value >= 0)
				buffer.putShort((short) value);
			else
				buffer.putShort(Short.MAX_VALUE);
		}
	}

	public static int getUnsignedSmart(ByteBuffer buffer) {
		int i = buffer.get() & 0xff;
		if (i >= 0x80) {
			i -= 0x80;
			return i << 8 | (buffer.get() & 0xff);
		}
		return i;
	}

	public static int getSmartInt(ByteBuffer buffer) {
		if (buffer.get(buffer.position()) < 0)
			return buffer.getInt() & 0x7fffffff;
		return buffer.getShort() & 0xffff;
	}

	public static String getString(ByteBuffer buffer) {
		int origPos = buffer.position();
		int length = 0;
		while (buffer.get() != 0)
			length++;
		if (length == 0)
			return "";
		byte[] byteArray = new byte[length];
		buffer.position(origPos);
		buffer.get(byteArray);
		buffer.position(buffer.position() + 1);
		return new String(byteArray);
	}

	public static void skip(ByteBuffer buffer, int bytes) {
		buffer.position(buffer.position() + bytes);
	}

	public static int getSmallSmartInt(ByteBuffer buffer) {
		if ((buffer.get(buffer.position()) & 0xff) < 128)
			return buffer.get() & 0xff;
		return (buffer.getShort() & 0xffff) - 0x8000;
	}

	public static int archiveId(int id, int size) {
		return id >> size;
	}

	public static int fileId(int id, int size) {
		return id & (1 << size) - 1;
	}

	public static int getTriByte(ByteBuffer buffer) {
		int p1 = buffer.get() & 0xff;
		int p2 = buffer.get() & 0xff;
		int p3 = buffer.get() & 0xff;
		return (p1 << 16) + (p2 << 8) + (p3);
	}

	public static Object getFieldValue(Object object, Field field) throws Throwable {
		field.setAccessible(true);
		Class<?> type = field.getType();
		if (type == int[][].class) {
			return Arrays.deepToString((int[][]) field.get(object));
		} else if (type == HashMap[].class) {
			return Arrays.toString((HashMap[]) field.get(object));
		} else if (type == ObjectType[].class) {
			return Arrays.toString((ObjectType[]) field.get(object));
		} else if (type == byte[].class) {
			return Arrays.toString((byte[]) field.get(object));
		} else if (type == int[].class) {
			return Arrays.toString((int[]) field.get(object));
		} else if (type == byte[].class) {
			return Arrays.toString((byte[]) field.get(object));
		} else if (type == short[].class) {
			return Arrays.toString((short[]) field.get(object));
		} else if (type == double[].class) {
			return Arrays.toString((double[]) field.get(object));
		} else if (type == float[].class) {
			return Arrays.toString((float[]) field.get(object));
		} else if (type == boolean[].class) {
			return Arrays.toString((boolean[]) field.get(object));
		} else if (type == Object[].class) {
			return Arrays.toString((Object[]) field.get(object));
		} else if (type == String[].class) {
			return Arrays.toString((String[]) field.get(object));
		}
		return field.get(object);
	}

	public static int getDecrSmart(ByteBuffer stream) {
		int first = stream.get() & 0xff;
		if (first < 128) {
			first -= 1;
		} else {
			first = (first << 8 | (stream.get() & 0xff));
			first -= 0x8000;
			first -= 1;
		}
		return first;
	}

	public static int readSmartSizeVar(ByteBuffer stream) {
		int value = 0;
		int curr = getUnsignedSmart(stream);
		while (curr == 32767) {
			curr = getUnsignedSmart(stream);
			value += 32767;
		}
		value += curr;
		return value;
	}

	public static Map<Integer, Integer> readMasked(ByteBuffer stream) {
		Map<Integer, Integer> result = new HashMap<>();
		int mask = stream.get() & 0xff;
		while (mask > 0) {
			if ((mask & 0x1) == 1) {
				result.put(getSmartInt(stream), getDecrSmart(stream));
			} else {
				result.put(0, 0);
			}
			mask /= 2;
		}
		return result;
	}
	
	public static int getRouteDistanceTo(WorldTile start, GroundItem item) {
		return LocalPathing.getLocalStepsTo(start, 1, new FixedTileStrategy(item.getPosition()), false);
	}
	
	public static int getRouteDistanceTo(WorldTile start, WorldObject object) {
		return LocalPathing.getLocalStepsTo(start, 1, new ObjectStrategy(object), false);
	}
	
	public static int getRouteDistanceTo(WorldTile start, NPC npc) {
		return LocalPathing.getLocalStepsTo(start, 1, new EntityStrategy(npc), false);
	}
	
	public static int getRouteDistanceTo(WorldTile start, WorldTile tile) {
		return LocalPathing.getLocalStepsTo(start, 1, new FixedTileStrategy(tile), false);
	}
	
	public static int getDistanceTo(WorldTile start, WorldTile destination) {
		return start.getDistance(destination);
	}
	
	public static boolean withinDistance(WorldTile from, WorldTile target) {
		return getDistanceTo(from, target) < PlayerProfiles.get().interactDistanceRange;
	}

	public static int larger(int v1, int v2) {
		if (v1 > v2)
			return v1;
		return v2;
	}
}
