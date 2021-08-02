package com.darkan.cache;

import java.lang.reflect.Type;

public enum Index {
	BASES(1), 
	CONFIG(2), 
	INTERFACES(3), 
	MAPSV2(5), 
	MODELS(7), 
	SPRITES(8), 
	BINARY(10), 
	SCRIPTS(12), 
	FONTMETRICS(13), 
	VORBIS(14), 
	OBJ_DEF(16), 
	ENUM_DEF(17),
	NPC_DEF(18), 
	ITEM_DEF(19), 
	SEQ_DEF(20), 
	SPOTANIM_DEF(21), 
	STRUCT_DEF(22), 
	WORLDMAP(23), 
	QUICKCHAT(24), 
	GLOBAL_QUICKCHAT(25), 
	MATERIALS(26), 
	PARTICLES(27), 
	DEFAULTS(28), 
	BILLBOARDS(29), 
	DLLS(30), 
	SHADERS(31), 
	LOADINGSPRITES(32), 
	LOADINGSCREEN(33), 
	LOADINGSPRITESRAW(34), 
	CUTSCENES(35), 
	AUDIOSTREAMS(40), 
	WORLDMAPAREAS(41), 
	WORLDMAPLABELS(42), 
	MODELSRT7(47), 
	ANIMSRT7(48), 
	DBTABLEINDEX(49), 
	TEXTURES(52), 
	TEXTURES_PNG(53), 
	TEXTURES_PNG_MIPPED(54),
	TEXTURES_ETC(55), 
	ANIMS_KEYFRAMES(56), 
	ACHIEVEMENT_DEF(57);

	private int indexId;
	private final Type defType;

	private Index(int indexId) {
		this(indexId, null);
	}
	
	private Index(int indexId, Type defType) {
		this.indexId = indexId;
		this.defType = defType;
	}

	public int getIndexId() {
		return indexId;
	}
	
	public Type getDefType() {
		return defType;
	}
}
