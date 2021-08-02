package com.darkan.cache.def.objects;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.darkan.api.util.Utils;
import com.darkan.api.world.ObjectType;
import com.darkan.cache.Index;
import com.darkan.cache.def.CacheParser;

public class ObjectDefParser extends CacheParser<ObjectDef> {

	@Override
	public Index getIndex() {
		return Index.OBJ_DEF;
	}

	@Override
	public int getArchiveBitSize() {
		return 8;
	}
	
	@SuppressWarnings("unused")
	@Override
	public ObjectDef decode(int id, ByteBuffer stream) {
		ObjectDef def = new ObjectDef();
		def.id = id;
		while (stream.hasRemaining()) {
			int opcode = stream.get() & 0xff;
			if (opcode == 0)
				break;
			if (opcode == 1) {
				int i_4_ = stream.get() & 0xff;
				def.types = new ObjectType[i_4_];
				def.modelIds = new int[i_4_][];
				for (int i_5_ = 0; i_5_ < i_4_; i_5_++) {
					def.types[i_5_] = ObjectType.forId(stream.get());
					int i_6_ = stream.get() & 0xff;
					def.modelIds[i_5_] = new int[i_6_];
					for (int i_7_ = 0; i_7_ < i_6_; i_7_++)
						def.modelIds[i_5_][i_7_] = Utils.getSmartInt(stream);
				}
			} else if (opcode == 2)
				def.name = Utils.getString(stream);
			else if (opcode == 14)
				def.sizeX = stream.get() & 0xff;
			else if (15 == opcode)
				def.sizeY = stream.get() & 0xff;
			else if (17 == opcode) {
				def.clipType = 0;
				def.blocks = false;
			} else if (18 == opcode)
				def.blocks = false;
			else if (opcode == 19)
				def.interactable = stream.get() & 0xff;
			else if (21 == opcode)
				def.groundContoured = (byte) 1;
			else if (22 == opcode)
				def.delayShading = true;
			else if (opcode == 23)
				def.occludes = 1;
			else if (opcode == 24) {
				int i_8_ = Utils.getSmartInt(stream);
				if (i_8_ != -1)
					def.animations = new int[] { i_8_ };
			} else if (opcode == 27)
				def.clipType = 1;
			else if (opcode == 28)
				def.decorDisplacement = (stream.get() & 0xff << 2);
			else if (opcode == 29)
				def.ambient = stream.get();
			else if (39 == opcode)
				def.contrast = stream.get();
			else if (opcode >= 30 && opcode < 35)
				def.options[opcode - 30] = Utils.getString(stream);
			else if (40 == opcode) {
				int i_9_ = stream.get() & 0xff;
				def.originalColors = new short[i_9_];
				def.modifiedColors = new short[i_9_];
				for (int i_10_ = 0; i_10_ < i_9_; i_10_++) {
					def.originalColors[i_10_] = (short) (stream.getShort() & 0xffff);
					def.modifiedColors[i_10_] = (short) (stream.getShort() & 0xffff);
				}
			} else if (opcode == 41) {
				int i_11_ = stream.get() & 0xff;
				def.originalTextures = new short[i_11_];
				def.modifiedTextures = new short[i_11_];
				for (int i_12_ = 0; i_12_ < i_11_; i_12_++) {
					def.originalTextures[i_12_] = (short) (stream.getShort() & 0xffff);
					def.modifiedTextures[i_12_] = (short) (stream.getShort() & 0xffff);
				}
			} else if (opcode == 42) {
				int i_13_ = stream.get() & 0xff;
				def.aByteArray5641 = new byte[i_13_];
				for (int i_14_ = 0; i_14_ < i_13_; i_14_++)
					def.aByteArray5641[i_14_] = (byte) stream.get();
			} else if (opcode == 44) {
				stream.getShort();
			} else if (opcode == 45) {
				stream.getShort();
			} else if (opcode == 62)
				def.inverted = true;
			else if (opcode == 64)
				def.castsShadow = false;
			else if (65 == opcode)
				def.scaleX = stream.getShort() & 0xffff;
			else if (opcode == 66)
				def.scaleY = stream.getShort() & 0xffff;
			else if (67 == opcode)
				def.scaleZ = stream.getShort() & 0xffff;
			else if (opcode == 69)
				def.accessBlockFlag = stream.get() & 0xff;
			else if (70 == opcode)
				def.offsetX = (stream.getShort() << 2);
			else if (opcode == 71)
				def.offsetY = (stream.getShort() << 2);
			else if (opcode == 72)
				def.offsetZ = (stream.getShort() << 2);
			else if (73 == opcode)
				def.obstructsGround = true;
			else if (opcode == 74)
				def.ignoreAltClip = true;
			else if (opcode == 75)
				def.supportsItems = stream.get() & 0xff;
			else if (77 == opcode || 92 == opcode) {
				def.varpBit = stream.getShort() & 0xffff;
				if (65535 == def.varpBit)
					def.varpBit = -1;
				def.varp = stream.getShort() & 0xffff;
				if (def.varp == 65535)
					def.varp = -1;
				int objectId = -1;
				if (opcode == 92)
					objectId = Utils.getSmartInt(stream);
				int transforms = Utils.getUnsignedSmart(stream);
				def.transformTo = new int[transforms + 2];
				for (int i = 0; i <= transforms; i++)
					def.transformTo[i] = Utils.getSmartInt(stream);
				def.transformTo[1 + transforms] = objectId;
			} else if (78 == opcode) {
				def.ambientSoundId = stream.getShort() & 0xffff;
				def.ambientSoundHearDistance = stream.get() & 0xff;
			} else if (79 == opcode) {
				def.anInt5667 = stream.getShort() & 0xffff;
				def.anInt5698 = stream.getShort() & 0xffff;
				def.ambientSoundHearDistance = stream.get() & 0xff;
				int i_18_ = stream.get() & 0xff;
				def.audioTracks = new int[i_18_];
				for (int i_19_ = 0; i_19_ < i_18_; i_19_++)
					def.audioTracks[i_19_] = stream.getShort() & 0xffff;
			} else if (81 == opcode) {
				def.groundContoured = (byte) 2;
				def.anInt5654 = stream.get() & 0xff * 256;
			} else if (opcode == 82)
				def.hidden = true;
			else if (88 == opcode)
				def.aBool5703 = false;
			else if (opcode == 89)
				def.aBool5702 = false;
			else if (91 == opcode)
				def.members = true;
			else if (93 == opcode) {
				def.groundContoured = (byte) 3;
				def.anInt5654 = stream.getShort() & 0xffff;
			} else if (opcode == 94)
				def.groundContoured = (byte) 4;
			else if (95 == opcode) {
				def.groundContoured = (byte) 5;
				def.anInt5654 = stream.getShort();
			} else if (97 == opcode)
				def.adjustMapSceneRotation = true;
			else if (98 == opcode)
				def.hasAnimation = true;
			else if (99 == opcode) {
				def.anInt5705 = stream.get() & 0xff;
				def.anInt5665 = stream.getShort() & 0xffff;
			} else if (opcode == 100) {
				def.anInt5670 = stream.get() & 0xff;
				def.anInt5666 = stream.getShort() & 0xffff;
			} else if (101 == opcode)
				def.mapSpriteRotation = stream.get() & 0xff;
			else if (opcode == 102)
				def.mapSpriteId = stream.getShort() & 0xffff;
			else if (opcode == 103)
				def.occludes = 0;
			else if (104 == opcode)
				def.ambientSoundVolume = stream.get() & 0xff;
			else if (opcode == 105)
				def.flipMapSprite = true;
			else if (106 == opcode) {
				int i_20_ = stream.get() & 0xff;
				int i_21_ = 0;
				def.animations = new int[i_20_];
				def.animProbs = new int[i_20_];
				for (int i_22_ = 0; i_22_ < i_20_; i_22_++) {
					def.animations[i_22_] = Utils.getSmartInt(stream);
					i_21_ += def.animProbs[i_22_] = stream.get() & 0xff;
				}
				for (int i_23_ = 0; i_23_ < i_20_; i_23_++)
					def.animProbs[i_23_] = def.animProbs[i_23_] * 65535 / i_21_;
			} else if (opcode == 107)
				def.mapIcon = stream.getShort() & 0xffff;
			else if (opcode >= 150 && opcode < 155) {
				def.options[opcode - 150] = Utils.getString(stream);
//				if (!((ObjectDefinitionsLoader) loader).showOptions)
//					aStringArray5647[opcode - 150] = null;
			} else if (160 == opcode) {
				int i_24_ = stream.get() & 0xff;
				def.anIntArray5707 = new int[i_24_];
				for (int i_25_ = 0; i_25_ < i_24_; i_25_++)
					def.anIntArray5707[i_25_] = stream.getShort() & 0xffff;
			} else if (162 == opcode) {
				def.groundContoured = (byte) 3;
				def.anInt5654 = stream.getInt();
			} else if (163 == opcode) {
				def.aByte5644 = (byte) stream.get();
				def.aByte5642 = (byte) stream.get();
				def.aByte5646 = (byte) stream.get();
				def.aByte5634 = (byte) stream.get();
			} else if (164 == opcode)
				def.anInt5682 = stream.getShort();
			else if (165 == opcode)
				def.anInt5683 = stream.getShort();
			else if (166 == opcode)
				def.anInt5710 = stream.getShort();
			else if (167 == opcode)
				def.anInt5704 = stream.getShort() & 0xffff;
			else if (168 == opcode)
				def.aBool5696 = true;
			else if (169 == opcode)
				def.aBool5700 = true;
			else if (opcode == 170)
				def.anInt5684 = Utils.getUnsignedSmart(stream);
			else if (opcode == 171)
				def.anInt5658 = Utils.getUnsignedSmart(stream);
			else if (opcode == 173) {
				def.anInt5708 = stream.getShort() & 0xffff;
				def.anInt5709 = stream.getShort() & 0xffff;
			} else if (177 == opcode)
				def.aBool5699 = true;
			else if (178 == opcode)
				def.anInt5694 = stream.get() & 0xff;
			else if (186 == opcode) {
				int unk = stream.get() & 0xff;
			} else if (188 == opcode) {
				boolean unk = true;
			} else if (189 == opcode) {
				def.aBool5711 = true;
			} else if (opcode >= 190 && opcode < 195) {
				if (def.actionCursors == null) {
					def.actionCursors = new int[5];
					Arrays.fill(def.actionCursors, -1);
				}
				def.actionCursors[opcode - 190] = stream.getShort() & 0xFFFF;
			} else if (opcode == 196) {
				int unk = stream.get() & 0xff;
			} else if (opcode == 197) {
				int unk = stream.get() & 0xff;
			} else if (opcode == 198) {
				boolean unk = true;
			} else if (opcode == 199) {
				boolean unk = true;
			} else if (opcode == 201) {
				int unk0 = Utils.getUnsignedSmart(stream);
				int unk1 = Utils.getUnsignedSmart(stream);
				int unk2 = Utils.getUnsignedSmart(stream);
				int unk3 = Utils.getUnsignedSmart(stream);
				int unk4 = Utils.getUnsignedSmart(stream);
				int unk5 = Utils.getUnsignedSmart(stream);
			} else if (opcode == 202) {
				int unk = Utils.getUnsignedSmart(stream);
			} else if (249 == opcode) {
				def.params.parse(stream);
			} else {
				throw new RuntimeException("Missing Object opcode: " + opcode);
			}
		}
		return def;
	}

}
