package com.darkan.cache.def.items;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.darkan.api.util.Utils;
import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;

public class ItemDefParser extends CacheParser<ItemDef> {
	
	@Override
	public Index getIndex() {
		return Index.ITEM_DEF;
	}

	@Override
	public int getArchiveBitSize() {
		return 8;
	}

	@Override
	public ItemDef decode(int id, ByteBuffer buffer) {
		ItemDef def = new ItemDef();
		def.id = id;
		while (buffer.hasRemaining()) {
			int opcode = buffer.get() & 0xFF;
			if (opcode == 0) {
				break;
			} else if (opcode == 1) {
				def.modelId = Utils.getSmartInt(buffer);
			} else if (opcode == 2) {
				def.name = Utils.getString(buffer);
			} else if (opcode == 3) {
				def.buffEffect = Utils.getString(buffer);// examine
			} else if (opcode == 4) {
				def.modelZoom = buffer.getShort() & 0xFFFF;
			} else if (opcode == 5) {
				def.modelRotationX = buffer.getShort() & 0xFFFF;
			} else if (opcode == 6) {
				def.modelRotationY = buffer.getShort() & 0xFFFF;
			} else if (opcode == 7) {
				def.modelOffsetX = buffer.getShort() & 0xFFFF;
				if (def.modelOffsetX > 32767) {
					def.modelOffsetX -= 65536;
				}
			} else if (opcode == 8) {
				def.modelOffsetY = buffer.getShort() & 0xFFFF;
				if (def.modelOffsetY > 32767) {
					def.modelOffsetY -= 65536;
				}
			} else if (opcode == 11) {
				def.stackable = 1;
			} else if (opcode == 12) {
				def.price = buffer.getInt();
			} else if (opcode == 13) {
				def.equipmentSlot = buffer.get();
			} else if (opcode == 14) {
				def.equipmentType = buffer.get();
			} else if (opcode == 15) {
				// TODO bool
			} else if (opcode == 16) {
				def.members = true;
			} else if (opcode == 18) {
				def.unused = buffer.getShort() & 0xFFFF;
			} else if (opcode == 23) {
				def.maleModel1 = Utils.getSmartInt(buffer);
			} else if (opcode == 24) {
				def.maleModel2 = Utils.getSmartInt(buffer);
			} else if (opcode == 25) {
				def.femaleModel1 = Utils.getSmartInt(buffer);
			} else if (opcode == 26) {
				def.femaleModel2 = Utils.getSmartInt(buffer);
			} else if (opcode == 27) {
				def.equipmentType2 = buffer.get();
			} else if (opcode >= 30 && opcode < 35) {
				def.groundActions[opcode - 30] = Utils.getString(buffer);
			} else if (opcode >= 35 && opcode < 40) {
				def.inventoryActions[opcode - 35] = Utils.getString(buffer);
			} else if (opcode == 40) {
				int count = buffer.get() & 0xFF;
				def.originalColors = new short[count];
				def.replacementColors = new short[count];
				for (int idx = 0; idx < count; idx++) {
					def.originalColors[idx] = (short) (buffer.getShort() & 0xFFFF);
					def.replacementColors[idx] = (short) (buffer.getShort() & 0xFFFF);
				}
			} else if (opcode == 41) {
				int count = buffer.get() & 0xFF;
				def.originalTextures = new short[count];
				def.replacementTextures = new short[count];
				for (int idx = 0; idx < count; idx++) {
					def.originalTextures[idx] = (short) (buffer.getShort() & 0xFFFF);
					def.replacementTextures[idx] = (short) (buffer.getShort() & 0xFFFF);
				}
			} else if (opcode == 42) {
				int count = buffer.get() & 0xFF;
				def.recolorPalette = new byte[count];
				for (int index = 0; index < count; index++) {
					def.recolorPalette[index] = buffer.get();
				}
			} else if (opcode == 43) {
				buffer.getInt();
			} else if (opcode == 44) {
				buffer.getShort();
			} else if (opcode == 45) {
				buffer.getShort();
			} else if (opcode == 65) {
				def.stockMarket = true;
			} else if (opcode == 69) {
				def.geBuyLimit = buffer.getInt();
			} else if (opcode == 78) {
				def.maleModel3 = Utils.getSmartInt(buffer);
			} else if (opcode == 79) {
				def.femaleModel3 = Utils.getSmartInt(buffer);
			} else if (opcode == 90) {
				def.maleHeadModel1 = Utils.getSmartInt(buffer);
			} else if (opcode == 91) {
				def.femaleHeadModel1 = Utils.getSmartInt(buffer);
			} else if (opcode == 92) {
				def.maleHeadModel2 = Utils.getSmartInt(buffer);
			} else if (opcode == 93) {
				def.femaleHeadModel2 = Utils.getSmartInt(buffer);
			} else if (opcode == 94) {
				def.category = buffer.getShort();
			} else if (opcode == 95) {
				def.modelAngleZ = buffer.getShort() & 0xFFFF;
			} else if (opcode == 96) {
				def.searchable = (byte) (buffer.get() & 0xFF);
			} else if (opcode == 97) {
				def.notedItemId = buffer.getShort() & 0xFFFF;
			} else if (opcode == 98) {
				def.notedTemplate = buffer.getShort() & 0xFFFF;
			} else if (opcode >= 100 && opcode < 110) {
				if (def.stackIds == null) {
					def.stackAmounts = new int[10];
					def.stackIds = new int[10];
				}
				def.stackIds[opcode - 100] = buffer.getShort() & 0xFFFF;
				def.stackAmounts[opcode - 100] = buffer.getShort() & 0xFFFF;
			} else if (opcode == 110) {
				def.resizeX = buffer.getShort() & 0xFFFF;
			} else if (opcode == 111) {
				def.resizeY = buffer.getShort() & 0xFFFF;
			} else if (opcode == 112) {
				def.resizeZ = buffer.getShort() & 0xFFFF;
			} else if (opcode == 113) {
				def.ambient = buffer.get();
			} else if (opcode == 114) {
				def.contrast = buffer.get() * 5;
			} else if (opcode == 115) {
				def.teamId = (byte) (buffer.get() & 0xFF);
			} else if (opcode == 121) {
				def.lentItemId = buffer.getShort() & 0xFFFF;
			} else if (opcode == 122) {
				def.lendTemplate = buffer.getShort() & 0xFFFF;
			} else if (opcode == 125) {
				def.maleModelOffsetX = buffer.get() << 2;
				def.maleModelOffsetY = buffer.get() << 2;
				def.maleModelOffsetZ = buffer.get() << 2;
			} else if (opcode == 126) {
				def.femaleModelOffsetX = buffer.get() << 2;
				def.femaleModelOffsetY = buffer.get() << 2;
				def.femaleModelOffsetZ = buffer.get() << 2;
			} else if (opcode == 127 || opcode == 128 || opcode == 129 || opcode == 130) {
				buffer.get();
				buffer.getShort();
			} else if (opcode == 132) {
				int count = buffer.get() & 0xFF;
				def.questIds = new int[count];
				for (int index = 0; index < count; index++) {
					def.questIds[index] = buffer.getShort() & 0xFFFF;
				}
			} else if (opcode == 134) {
				def.pickSizeShift = buffer.get() & 0xFF;
			} else if (opcode == 139) {
				def.bindId = buffer.getShort() & 0xFFFF;
			} else if (opcode == 140) {
				def.boundTemplate = buffer.getShort() & 0xFFFF;
			} else if (opcode >= 142 && opcode < 147) {
				if (def.groundCursors == null) {
					def.groundCursors = new int[6];
					Arrays.fill(def.groundCursors, -1);
				}
				def.groundCursors[opcode - 142] = buffer.getShort() & 0xFFFF;
			} else if (opcode == 147) {
				buffer.getShort();
			} else if (opcode >= 150 && opcode < 155) {
				if (def.inventoryCursors == null) {
					def.inventoryCursors = new int[5];
					Arrays.fill(def.inventoryCursors, -1);
				}
				def.inventoryCursors[opcode - 150] = buffer.getShort() & 0xFFFF;
			} else if (opcode == 157) {
				def.randomizeGroundPos = true;
			} else if (opcode == 161) {
				def.shardItemId = buffer.getShort() & 0xFFFF;
			} else if (opcode == 162) {
				def.shardTemplateId = buffer.getShort() & 0xFFFF;
			} else if (opcode == 163) {
				def.shardCombineAmount = buffer.getShort();
			} else if (opcode == 164) {
				def.shardName = Utils.getString(buffer);
			} else if (opcode == 165) {
				def.neverStackable = true;
			} else if (opcode == 167) {
				// TODO bool
			} else if (opcode == 168) {
				// TODO bool
			} else if (opcode == 242) {
				Utils.getSmartInt(buffer);
				Utils.getSmartInt(buffer);
			} else if (opcode >= 243 && opcode < 249) {
				Utils.getSmartInt(buffer);
			} else if (opcode == 249) {
				def.params.parse(buffer);
			} else {
				throw new IllegalArgumentException("Invalid EnumDefinition opcode " + opcode);
			}
		}
		if (def.notedTemplate != -1)
			def.toNote();
		if (def.lendTemplate != -1)
			def.toLend();
		if (def.boundTemplate != -1)
			def.toBind();
		def.loadEquippedOps();
		return def;
	}
}
