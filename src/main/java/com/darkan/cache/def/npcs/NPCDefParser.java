package com.darkan.cache.def.npcs;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.darkan.api.util.Utils;
import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;

public class NPCDefParser extends CacheParser<NPCDef> {

	@Override
	public Index getIndex() {
		return Index.NPC_DEF;
	}
	
	@Override
	public int getArchiveBitSize() {
		return 7;
	}

	@SuppressWarnings("unused")
	@Override
	public NPCDef decode(int id, ByteBuffer stream) {
		NPCDef def = new NPCDef();
		def.id = id;
		while (stream.hasRemaining()) {
			int opcode = stream.get() & 0xff;
			if (opcode == 0)
				break;
			if (opcode == 1) {
				int i_4 = stream.get() & 0xff;
				def.modelIds = new int[i_4];
				for (int i_5 = 0; i_5 < i_4; i_5++) {
					def.modelIds[i_5] = Utils.getSmartInt(stream);
				}
			} else if (opcode == 2) {
				def.name = Utils.getString(stream);
			} else if (opcode == 12) {
				def.size = stream.get() & 0xff;
			} else if (opcode >= 30 && opcode < 35) {
				def.options[opcode - 30] = Utils.getString(stream);
			} else if (opcode == 40) {
				int i_4 = stream.get() & 0xff;
				def.originalColors = new short[i_4];
				def.modifiedColors = new short[i_4];
				for (int i_5 = 0; i_5 < i_4; i_5++) {
					def.originalColors[i_5] = (short) (stream.getShort() & 0xffff);
					def.modifiedColors[i_5] = (short) (stream.getShort() & 0xffff);
				}
			} else if (opcode == 41) {
				int i_4 = stream.get() & 0xff;
				def.originalTextures = new short[i_4];
				def.modifiedTextures = new short[i_4];
				for (int i_5 = 0; i_5 < i_4; i_5++) {
					def.originalTextures[i_5] = (short) (stream.getShort() & 0xffff);
					def.modifiedTextures[i_5] = (short) (stream.getShort() & 0xffff);
				}
			} else if (opcode == 42) {
				int i_4 = stream.get() & 0xff;
				def.recolourPalette = new byte[i_4];
				for (int i_5 = 0; i_5 < i_4; i_5++) {
					def.recolourPalette[i_5] = (byte) stream.get();
				}
			} else if (opcode == 44) {
				stream.getShort();
			} else if (opcode == 45) {
				stream.getShort();
			} else if (opcode == 60) {
				int i_4 = stream.get() & 0xff;
				def.headModels = new int[i_4];
				for (int i_5 = 0; i_5 < i_4; i_5++) {
					def.headModels[i_5] = Utils.getSmartInt(stream);
				}
			} else if (opcode == 93) {
				def.drawMapdot = false;
			} else if (opcode == 95) {
				def.combatLevel = stream.getShort() & 0xffff;
			} else if (opcode == 97) {
				def.resizeX = stream.getShort() & 0xffff;
			} else if (opcode == 98) {
				def.resizeY = stream.getShort() & 0xffff;
			} else if (opcode == 99) {
				def.aBool4904 = true;
			} else if (opcode == 100) {
				def.ambient = stream.get();
			} else if (opcode == 101) {
				def.contrast = stream.get();
			} else if (opcode == 102) {
				def.headIcons = Utils.readMasked(stream);
			} else if (opcode == 103) {
				def.rotation = stream.getShort() & 0xffff;
			} else if (opcode == 106 || opcode == 118) {
				def.varpBit = stream.getShort() & 0xffff;
				if (def.varpBit == 65535) {
					def.varpBit = -1;
				}
				def.varp = stream.getShort() & 0xffff;
				if (def.varp == 65535) {
					def.varp = -1;
				}
				int defaultId = -1;
				if (opcode == 118) {
					defaultId = stream.getShort() & 0xffff;
					if (defaultId == 65535) {
						defaultId = -1;
					}
				}
				int size = Utils.getUnsignedSmart(stream);
				def.transformTo = new int[size + 2];
				for (int i = 0; i < size+1; i++) {
					def.transformTo[i] = stream.getShort() & 0xffff;
					if (def.transformTo[i] == 65535) {
						def.transformTo[i] = -1;
					}
				}
				def.transformTo[size + 1] = defaultId;
			} else if (opcode == 107) {
				def.visible = false;
			} else if (opcode == 109) {
				def.isClickable = false;
			} else if (opcode == 111) {
				def.animateIdle = false;
			} else if (opcode == 113) {
				def.aShort4874 = (short) stream.getShort() & 0xffff;
				def.aShort4897 = (short) stream.getShort() & 0xffff;
			} else if (opcode == 114) {
				def.aByte4883 = (byte) stream.get();
				def.aByte4899 = (byte) stream.get();
			} else if (opcode == 119) {
				def.walkMask = (byte) stream.get();
			} else if (opcode == 121) {
				def.modelTranslation = new int[def.modelIds.length][];
				int i_4 = stream.get() & 0xff;
				for (int i_5 = 0; i_5 < i_4; i_5++) {
					int[] translations = new int[4];
					translations[0] = stream.get();
					translations[1] = stream.get();
					translations[2] = stream.get();
					translations[3] = stream.get();
				}
			} else if (opcode == 123) {
				def.height = stream.getShort() & 0xffff;
			} else if (opcode == 125) {
				def.respawnDirection = stream.get();
			} else if (opcode == 127) {
				def.basId = stream.getShort() & 0xffff;
			} else if (opcode == 128) {
				def.movementType = MovementType.forId(stream.get() & 0xff);
			} else if (opcode == 134) {
				def.walkingAnimation = stream.getShort() & 0xffff;
				if (def.walkingAnimation == 65535) {
					def.walkingAnimation = -1;
				}
				def.rotate180Animation = stream.getShort() & 0xffff;
				if (def.rotate180Animation == 65535) {
					def.rotate180Animation = -1;
				}
				def.rotate90RightAnimation = stream.getShort() & 0xffff;
				if (def.rotate90RightAnimation == 65535) {
					def.rotate90RightAnimation = -1;
				}
				def.rotate90LeftAnimation = stream.getShort() & 0xffff;
				if (def.rotate90LeftAnimation == 65535) {
					def.rotate90LeftAnimation = -1;
				}
				def.specialByte = stream.get() & 0xff;
			} else if (opcode == 135) {
				def.anInt4875 = stream.get() & 0xff;
				def.anInt4873 = stream.getShort() & 0xffff;
			} else if (opcode == 136) {
				def.anInt4854 = stream.get() & 0xff;
				def.anInt4861 = stream.getShort() & 0xffff;
			} else if (opcode == 137) {
				def.attackOpCursor = stream.getShort() & 0xffff;
			} else if (opcode == 138) {
				def.armyIcon = Utils.getSmartInt(stream);
			} else if (opcode == 140) {
				def.anInt4909 = stream.get() & 0xff;
			} else if (opcode == 141) {
				def.aBool4884 = true;
			} else if (opcode == 142) {
				def.mapIcon = stream.getShort() & 0xffff;
			} else if (opcode == 143) {
				def.aBool4890 = true;
			} else if (opcode >= 150 && opcode < 155) {
				def.membersOptions[opcode - 150] = Utils.getString(stream);
			} else if (opcode == 155) {
				def.aByte4868 = (byte) stream.get();
				def.aByte4869 = (byte) stream.get();
				def.aByte4905 = (byte) stream.get();
				def.aByte4871 = (byte) stream.get();
			} else if (opcode == 158) {
				def.aByte4916 = 1;
			} else if (opcode == 159) {
				def.aByte4916 = 0;
			} else if (opcode == 160) {
				int i_4 = stream.get() & 0xff;
				def.quests = new int[i_4];
				for (int i_5 = 0; i_5 < i_4; i_5++) {
					def.quests[i_5] = stream.getShort() & 0xffff;
				}
			} else if (opcode == 162) {
				def.aBool4872 = true;
			} else if (opcode == 163) {
				def.anInt4917 = stream.get() & 0xff;
			} else if (opcode == 164) {
				def.anInt4911 = stream.getShort() & 0xffff;
				def.anInt4919 = stream.getShort() & 0xffff;
			} else if (opcode == 165) {
				def.anInt4913 = stream.get() & 0xff;
			} else if (opcode == 168) {
				def.anInt4908 = stream.get() & 0xff;
			} else if (opcode == 169) {
				def.aBool4920 = false;
			} else if (opcode >= 170 && opcode < 175) {
				if (def.actionCursors == null) {
					def.actionCursors = new int[5];
					Arrays.fill(def.actionCursors, -1);
				}
				def.actionCursors[opcode - 170] = stream.getShort() & 0xFFFF;
			} else if (opcode == 178) {
				boolean unk = true;
			} else if (opcode == 179) {
				int unk0 = Utils.getUnsignedSmart(stream);
				int unk1 = Utils.getUnsignedSmart(stream);
				int unk2 = Utils.getUnsignedSmart(stream);
				int unk3 = Utils.getUnsignedSmart(stream);
				int unk4 = Utils.getUnsignedSmart(stream);
				int unk5 = Utils.getUnsignedSmart(stream);
			} else if (opcode == 180) {
				System.err.println("Missing opcode 180");
				//TODO
			} else if (opcode == 181) {
				System.err.println("Missing opcode 181");
				//TODO
			} else if (opcode == 182) {
				boolean unk = true;
			} else if (opcode == 183) {
				Utils.skip(stream, 1);
			} else if (opcode == 184) {
				//int unk = Utils.getSmartInt(stream);
				Utils.skip(stream, 1);
			} else if (opcode == 249) {
				def.params.parse(stream);
			} else {
				throw new RuntimeException("Missing NPC opcode: " + opcode);
			}
		}
		return def;
	}

	public enum MovementType {
		STATIONARY(-1),
		HALF_WALK(0),
		WALKING(1),
		RUNNING(2);
		
		public int id;
		
		private MovementType(int id) {
			this.id = id;
		}
		
		public static MovementType forId(int id) {
			for (MovementType type : MovementType.values()) {
				if (type.id == id)
					return type;
			}
			return null;
		}
	}
}
