package com.darkan.cache.def.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.darkan.api.entity.VarManager;
import com.darkan.api.util.Utils;
import com.darkan.api.world.ObjectType;
import com.darkan.cache.Cache;
import com.darkan.cache.def.params.Params;

public class ObjectDef {
	
	private static ObjectDefParser PARSER = new ObjectDefParser();
	
	public static ObjectDef get(int id) {
		ObjectDef def = PARSER.get(Cache.get(), id);
		if (def == null) {
			ObjectDef empty = new ObjectDef();
			empty.id = id;
			return empty;
		}
		return PARSER.get(Cache.get(), id);
	}
	
	public static ObjectDefParser getParser() {
		return PARSER;
	}
	
	public static ObjectDef get(int id, VarManager vars) {
		ObjectDef def = get(id);
		if (def != null)
			def = ObjectDef.get(def.getIdForPlayer(vars));
		if (def == null) {
			ObjectDef empty = new ObjectDef();
			empty.id = id;
			return def;
		}
		return def;
	}

	public int id;
	public ObjectType[] types;
	public int[][] modelIds;
	public String name = "";
	public int sizeX = 1;
	public int sizeY = 1;
	public int clipType = 2;
	public boolean blocks;
	public int interactable;
	public byte groundContoured;
	public boolean delayShading;
	public int occludes;
	public int[] animations;
	public int decorDisplacement;
	public byte ambient;
	public byte contrast;
	public String[] options = new String[5];
	public short[] originalColors;
	public short[] modifiedColors;
	public short[] originalTextures;
	public short[] modifiedTextures;
	public byte[] aByteArray5641;
	public boolean inverted;
	public boolean castsShadow;
	public int scaleX;
	public int scaleY;
	public int scaleZ;
	public int accessBlockFlag;
	public int offsetX;
	public int offsetY;
	public int offsetZ;
	public boolean obstructsGround;
	public int supportsItems;
	public boolean ignoreAltClip;
	public int varpBit = -1;
	public int varp = -1;
	public int[] transformTo;
	public int ambientSoundId;
	public int ambientSoundHearDistance;
	public int anInt5667;
	public int anInt5698;
	public int[] audioTracks;
	public int anInt5654;
	public boolean hidden;
	public boolean aBool5703 = true;
	public boolean aBool5702 = true;
	public boolean members;
	public boolean adjustMapSceneRotation;
	public boolean hasAnimation = false;
	public int anInt5705;
	public int anInt5665;
	public int anInt5670;
	public int anInt5666;
	public int mapSpriteRotation;
	public int mapSpriteId;
	public int ambientSoundVolume;
	public boolean flipMapSprite;
	public int[] animProbs;
	public int mapIcon;
	public int[] anIntArray5707;
	public byte aByte5644;
	public byte aByte5642;
	public byte aByte5646;
	public byte aByte5634;
	public short anInt5682;
	public short anInt5683;
	public short anInt5710;
	public int anInt5704;
	public boolean aBool5696;
	public boolean aBool5700;
	public int anInt5684;
	public int anInt5658;
	public int anInt5708;
	public int anInt5709;
	public boolean aBool5699;
	public int anInt5694;
	public boolean aBool5711;
	public Params params = new Params();
	public int[] actionCursors;
	

	public int getOpIdForName(String opName) {
		for (int i = 0;i < 5;i++) {
			if (containsOp(i, opName))
				return i;
		}
		return -1;
	}
	
	public String getOp(int optionId) {
		if (options == null)
			return "null";
		if (optionId >= options.length)
			return "null";
		if (options[optionId] == null)
			return "null";
		return options[optionId];
	}
	
	public boolean containsOp(int i, String option) {
		if (options == null || options[i] == null || options.length <= i)
			return false;
		return getOp(i).equalsIgnoreCase(option);
	}

	public boolean containsOp(String option) {
		if (options == null)
			return false;
		for (String o : options) {
			if (o == null || !o.equalsIgnoreCase(option))
				continue;
			return true;
		}
		return false;
	}
	
	public int getIdForPlayer(VarManager vars) {
		if (transformTo == null || transformTo.length == 0)
			return id;
		if (vars == null) {
			int varIdx = transformTo[transformTo.length - 1];
			return varIdx;
		}
		int index = -1;
		if (varpBit != -1) {
			index = vars.getVarBit(varpBit);
		} else if (varp != -1) {
			index = vars.getVar(varp);
		}
		if (index >= 0 && index < transformTo.length - 1 && transformTo[index] != -1) {
			return transformTo[index];
		} else {
			int varIdx = transformTo[transformTo.length - 1];
			return varIdx;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append(this.getClass().getName());
		result.append(" {");
		result.append(newLine);

		// determine fields declared in this class only (no fields of
		// superclass)
		Field[] fields = this.getClass().getDeclaredFields();

		// print field names paired with their values
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()))
				continue;
			result.append("  ");
			try {
				result.append(field.getType().getCanonicalName() + " " + field.getName() + ": ");
				result.append(Utils.getFieldValue(this, field));
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
