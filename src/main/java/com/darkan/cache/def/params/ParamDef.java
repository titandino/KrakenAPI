package com.darkan.cache.def.params;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.darkan.api.util.Utils;
import com.darkan.cache.Cache;
import com.darkan.cache.def.vars.ScriptVarType;

public class ParamDef {
	public int id;
	public int defaultInt;
	public boolean autoDisable = true;
	public int typeId;
	public String defaultString;
	public ScriptVarType type;
	
	private static ParamDefParser PARSER = new ParamDefParser();
	
	public static ParamDefParser getParser() {
		return PARSER;
	}
	
	public static ParamDef get(int id) {
		return PARSER.get(Cache.get(), id);
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
				System.out.println(ex);
			}
			result.append(newLine);
		}
		result.append("}");

		return result.toString();
	}
}
