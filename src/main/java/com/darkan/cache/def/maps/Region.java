package com.darkan.cache.def.maps;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.darkan.api.util.Logger;
import com.darkan.api.util.Utils;
import com.darkan.api.world.ObjectType;
import com.darkan.api.world.WorldObject;
import com.darkan.api.world.WorldTile;
import com.darkan.cache.Archive;
import com.darkan.cache.ArchiveFile;
import com.darkan.cache.Cache;
import com.darkan.cache.Index;
import com.darkan.cache.def.objects.ObjectDef;

public class Region {

	public static final int OBJECTS = 0, UNDERWATER = 1, NPCS = 2, TILES = 3, WATER_TILES = 4;
	private static Map<Integer, Region> REGIONS = new HashMap<>();

	private int regionId;
	private ClipMap clipMap;
	private ClipMap clipMapProj;

	public WorldObject[][][][] objects;
	public List<WorldObject> objectList;
	public int[][][] overlayIds;
	public int[][][] underlayIds;
	public byte[][][] overlayPathShapes;
	public byte[][][] overlayRotations;
	public byte[][][] tileFlags;
	private boolean loaded = false;

	public Region(int regionId, boolean load) {
		this.regionId = regionId;
		if (load)
			load();
	}

	public Region(int regionId) {
		this(regionId, true);
	}

	public void load() {
		int regionX = regionId >> 8;
		int regionY = regionId & 0xff;
		// System.out.println("Loading region " + regionId + "(" + regionX + ", " +
		// regionY + ")");
		Archive archive = Cache.get().getArchive(Index.MAPSV2, regionX | regionY << 7);
		if (archive == null)
			return;
		ArchiveFile objects = archive.files.get(OBJECTS);
		ArchiveFile tiles = archive.files.get(TILES);
		if (objects != null)
			decodeObjectData(objects);
		if (tiles != null)
			decodeTileData(tiles);
		loaded = true;
	}

	private void decodeTileData(ArchiveFile file) {
		byte[] data = file.data;
		if (data == null)
			return;
		ByteBuffer stream = ByteBuffer.wrap(data);
		// System.out.println("Decoding tile data... Data len: " + stream.remaining());
		overlayIds = new int[4][64][64];
		underlayIds = new int[4][64][64];
		overlayPathShapes = new byte[4][64][64];
		overlayRotations = new byte[4][64][64];
		tileFlags = new byte[4][64][64];

		for (int plane = 0; plane < 4; plane++) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++) {
					int flags = stream.get() & 0xff;

					if ((flags & 0x10) != 0)
						System.err.println("Flag 0x10 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);
					if ((flags & 0x20) != 0)
						System.err.println("Flag 0x20 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);
					if ((flags & 0x40) != 0)
						System.err.println("Flag 0x40 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);
					if ((flags & 0x80) != 0)
						System.err.println("Flag 0x80 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);

					if ((flags & 0x1) != 0) {
						int shapeHash = stream.get() & 0xff;
						overlayIds[plane][x][y] = Utils.getUnsignedSmart(stream);
						overlayPathShapes[plane][x][y] = (byte) (shapeHash >> 2);
						overlayRotations[plane][x][y] = (byte) (shapeHash & 0x3);
					}
					if ((flags & 0x2) != 0) {
						tileFlags[plane][x][y] = (byte) (stream.get() & 0xff);
					}
					if ((flags & 0x4) != 0) {
						underlayIds[plane][x][y] = Utils.getUnsignedSmart(stream);
					}
					if ((flags & 0x8) != 0) {
						// tile heights (unsigned)
						stream.get();
					}
				}
			}
		}
		for (int plane = 0; plane < 4; plane++) {
			for (int x = 0; x < 64; x++) {
				for (int y = 0; y < 64; y++) {
					if (RenderFlag.flagged(tileFlags[plane][x][y], RenderFlag.CLIPPED)) {
						int finalPlane = plane;
						if (RenderFlag.flagged(tileFlags[1][x][y], RenderFlag.LOWER_OBJECTS_TO_OVERRIDE_CLIPPING))
							finalPlane--;
						if (finalPlane >= 0) {
							getClipMap().addBlockedTile(finalPlane, x, y);
						}
					}
				}
			}
		}
//		byte[] remaining = new byte[stream.remaining()];
//		stream.get(remaining);
//		System.out.println("Done parsing tile data... bytes not read: " + remaining.length + " " + Arrays.toString(remaining));
	}

	@SuppressWarnings("unused")
	private void decodeObjectData(ArchiveFile file) {
		try {
			int regionX = regionId >> 8;
			int regionY = regionId & 0xff;
			byte[] data = file.data;
			if (data == null)
				return;
			ByteBuffer stream = ByteBuffer.wrap(data);
			int objectId = -1;
			int incr;
			while ((incr = Utils.readSmartSizeVar(stream)) != 0) {
				objectId += incr;
				int location = 0;
				int incr2;
				while ((incr2 = Utils.getUnsignedSmart(stream)) != 0) {
					location += incr2 - 1;
					int localX = (location >> 6 & 0x3f);
					int localY = (location & 0x3f);
					int plane = location >> 12;
					int objectData = stream.get() & 0xff;
					boolean flag = (objectData & 0x80) != 0;
					int type = objectData >> 2 & 0x1f;
					int rotation = objectData & 0x3;
					int metaDataFlag = 0;
					if (flag) {
						metaDataFlag = stream.get() & 0xff;
//						if (metaDataFlag != 0)
//							System.err.println("Metadata flag: " + metaDataFlag);
						if ((metaDataFlag & 0x1) != 0) {
							float f1 = 0.0F, f2 = 0.0F, f3 = 0.0F, f4 = 1.0F;
							f1 = (float) stream.getShort() / 32768.0F;
							f2 = (float) stream.getShort() / 32768.0F;
							f3 = (float) stream.getShort() / 32768.0F;
							f4 = (float) stream.getShort() / 32768.0F;
//							System.err.println("4 float flag: " + f1 + ", " + f2 + ", " + f3 + ", " + f4);
						}
						float f1 = 0.0f, f2 = 0.0f, f3 = 0.0f;
						boolean print = false;
						if ((metaDataFlag & 0x2) != 0) {
							f1 = stream.getShort();
							print = true;
						}
						if ((metaDataFlag & 0x4) != 0) {
							f2 = stream.getShort();
							print = true;
						}
						if ((metaDataFlag & 0x8) != 0) {
							f3 = stream.getShort();
							print = true;
						}
//						if (print)
//							System.err.println("3 float flag: " + f1 + ", " + f2 + ", " + f3);

						f1 = f2 = f3 = 1.0f;
						print = false;
						if ((metaDataFlag & 0x10) != 0) {
							f1 = f2 = f3 = stream.getShort();
							print = true;
						} else {
							if ((metaDataFlag & 0x20) != 0) {
								f1 = stream.getShort();
								print = true;
							}
							if ((metaDataFlag & 0x40) != 0) {
								f2 = stream.getShort();
								print = true;
							}
							if ((metaDataFlag & 0x80) != 0) {
								f3 = stream.getShort();
								print = true;
							}
						}
//						if (print)
//							System.err.println("3 float flag 2: " + f1 + ", " + f2 + ", " + f3);
					}
					if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
						System.err.println("Error tile out of range: " + localX + ", " + localY);
						continue;
					}
					int objectPlane = plane;
					if (tileFlags != null && (tileFlags[1][localX][localY] & 0x2) != 0)
						objectPlane--;
					if (objectPlane < 0 || objectPlane >= 4 || plane < 0 || plane >= 4)
						continue;
//					if (flag)
//						System.out.println(new WorldObject(objectId, ObjectType.forId(type), rotation, localX + regionX * 64, localY + regionY * 64, objectPlane) + ", " + metaDataFlag + ", " + tileFlags[plane][localX][localY]);
					if (ObjectType.forId(type) == null) {
						System.out.println(new WorldObject(objectId, ObjectType.forId(type), rotation, localX + regionX * 64, localY + regionY * 64, objectPlane));
						System.err.println("Invalid object type: " + type + ", " + objectData + " obj: " + ObjectDef.get(objectId));
						continue;
					}
					WorldObject obj = new WorldObject(objectId, ObjectType.forId(type), rotation, localX + regionX * 64, localY + regionY * 64, objectPlane);
					spawnObject(obj, objectPlane, localX, localY);
				}
			}
		} catch (Exception e) {
			Logger.logError("Failed to parse region: " + regionId);
			Logger.handle(e);
			tileFlags = null;
			objects = null;
		}
	}

	public void spawnObject(WorldObject obj, int plane, int localX, int localY) {
		if (objects == null)
			objects = new WorldObject[4][64][64][4];
		if (objectList == null)
			objectList = new ArrayList<>();
		objectList.add(obj);
		objects[plane][localX][localY][obj.getSlot()] = obj;
		clip(obj, localX, localY);
	}

	public void clip(WorldObject object, int x, int y) {
		if (object.getId() == -1)
			return;
		if (clipMap == null)
			clipMap = new ClipMap(regionId, false);
		if (clipMapProj == null)
			clipMapProj = new ClipMap(regionId, true);
		int plane = object.getPlane();
		ObjectType type = object.getType();
		int rotation = object.getRotation();
		if (x < 0 || y < 0 || x >= clipMap.getMasks()[plane].length || y >= clipMap.getMasks()[plane][x].length)
			return;
		ObjectDef defs = ObjectDef.get(object.getId());

		if (defs.clipType == 0)
			return;

		switch (type) {
		case WALL_STRAIGHT:
		case WALL_DIAGONAL_CORNER:
		case WALL_WHOLE_CORNER:
		case WALL_STRAIGHT_CORNER:
			clipMap.addWall(plane, x, y, type, rotation, defs.blocks, !defs.ignoreAltClip);
			if (defs.blocks)
				clipMapProj.addWall(plane, x, y, type, rotation, defs.blocks, !defs.ignoreAltClip);
			break;
		case WALL_INTERACT:
		case SCENERY_INTERACT:
		case GROUND_INTERACT:
		case STRAIGHT_SLOPE_ROOF:
		case DIAGONAL_SLOPE_ROOF:
		case DIAGONAL_SLOPE_CONNECT_ROOF:
		case STRAIGHT_SLOPE_CORNER_CONNECT_ROOF:
		case STRAIGHT_SLOPE_CORNER_ROOF:
		case STRAIGHT_FLAT_ROOF:
		case STRAIGHT_BOTTOM_EDGE_ROOF:
		case DIAGONAL_BOTTOM_EDGE_CONNECT_ROOF:
		case STRAIGHT_BOTTOM_EDGE_CONNECT_ROOF:
		case STRAIGHT_BOTTOM_EDGE_CONNECT_CORNER_ROOF:
			int sizeX;
			int sizeY;
			if (rotation != 1 && rotation != 3) {
				sizeX = defs.sizeX;
				sizeY = defs.sizeY;
			} else {
				sizeX = defs.sizeY;
				sizeY = defs.sizeX;
			}
			clipMap.addObject(plane, x, y, sizeX, sizeY, defs.blocks, !defs.ignoreAltClip);
			if (defs.clipType != 0)
				clipMapProj.addObject(plane, x, y, sizeX, sizeY, defs.blocks, !defs.ignoreAltClip);
			break;
		case GROUND_DECORATION:
			if (defs.clipType == 1)
				clipMap.addBlockWalkAndProj(plane, x, y);
			break;
		default:
			break;
		}
	}

	public void unclip(int plane, int x, int y) {
		if (clipMap == null)
			clipMap = new ClipMap(regionId, false);
		if (clipMapProj == null)
			clipMapProj = new ClipMap(regionId, true);
		clipMap.setFlag(plane, x, y, 0);
	}

	public void unclip(WorldObject object, int x, int y) {
		if (object.getId() == -1) // dont clip or noclip with id -1
			return;
		if (clipMap == null)
			clipMap = new ClipMap(regionId, false);
		if (clipMapProj == null)
			clipMapProj = new ClipMap(regionId, true);
		int plane = object.getPlane();
		ObjectType type = object.getType();
		int rotation = object.getRotation();
		if (x < 0 || y < 0 || x >= clipMap.getMasks()[plane].length || y >= clipMap.getMasks()[plane][x].length)
			return;
		ObjectDef defs = ObjectDef.get(object.getId());

		if (defs.clipType == 0)
			return;

		switch (type) {
		case WALL_STRAIGHT:
		case WALL_DIAGONAL_CORNER:
		case WALL_WHOLE_CORNER:
		case WALL_STRAIGHT_CORNER:
			clipMap.removeWall(plane, x, y, type, rotation, defs.blocks, !defs.ignoreAltClip);
			if (defs.blocks)
				clipMapProj.removeWall(plane, x, y, type, rotation, defs.blocks, !defs.ignoreAltClip);
			break;
		case WALL_INTERACT:
		case SCENERY_INTERACT:
		case GROUND_INTERACT:
		case STRAIGHT_SLOPE_ROOF:
		case DIAGONAL_SLOPE_ROOF:
		case DIAGONAL_SLOPE_CONNECT_ROOF:
		case STRAIGHT_SLOPE_CORNER_CONNECT_ROOF:
		case STRAIGHT_SLOPE_CORNER_ROOF:
		case STRAIGHT_FLAT_ROOF:
		case STRAIGHT_BOTTOM_EDGE_ROOF:
		case DIAGONAL_BOTTOM_EDGE_CONNECT_ROOF:
		case STRAIGHT_BOTTOM_EDGE_CONNECT_ROOF:
		case STRAIGHT_BOTTOM_EDGE_CONNECT_CORNER_ROOF:
			int sizeX;
			int sizeY;
			if (rotation == 1 || rotation == 3) {
				sizeX = defs.sizeY;
				sizeY = defs.sizeX;
			} else {
				sizeX = defs.sizeX;
				sizeY = defs.sizeY;
			}
			clipMap.removeObject(plane, x, y, sizeX, sizeY, defs.blocks, !defs.ignoreAltClip);
			if (defs.blocks)
				clipMapProj.removeObject(plane, x, y, sizeX, sizeY, defs.blocks, !defs.ignoreAltClip);
			break;
		case GROUND_DECORATION:
			if (defs.clipType == 1)
				clipMap.removeBlockWalkAndProj(plane, x, y);
			break;
		default:
			break;
		}
	}

	public static Region get(int regionId) {
		return get(regionId, true);
	}

	public static Region get(int regionId, boolean load) {
		Region region = REGIONS.get(regionId);
		if (region == null) {
			region = new Region(regionId, load);
			REGIONS.put(regionId, region);
		}
		if (load && !region.loaded)
			region.load();
		return region;
	}

	public ClipMap getClipMap() {
		if (clipMap == null)
			clipMap = new ClipMap(regionId, false);
		return clipMap;
	}

	public ClipMap getClipMapProj() {
		if (clipMapProj == null)
			clipMapProj = new ClipMap(regionId, true);
		return clipMapProj;
	}

	public List<WorldObject> getObjectList() {
		return objectList;
	}

	public static boolean validateObjCoords(WorldObject object) {
		Region region = Region.get(object.getRegionId());
		List<WorldObject> realObjects = region.getObjectList();
		if (realObjects == null || realObjects.size() <= 0)
			return false;
		Map<Integer, WorldObject> distanceMap = new TreeMap<Integer, WorldObject>();
		for (WorldObject real : realObjects) {
			if (object.getPlane() != real.getPlane() || real.getId() != object.getId())
				continue;
			int distance = Utils.getDistanceTo(object, real);
			if (distance != -1)
				distanceMap.put(distance, real);
		}
		if (distanceMap.isEmpty())
			return false;
		List<Integer> sortedKeys = new ArrayList<Integer>(distanceMap.keySet());
		Collections.sort(sortedKeys);
		WorldObject closest = distanceMap.get(sortedKeys.get(0));
		if (Utils.getDistanceTo(object, closest) <= Utils.larger(object.getDef().sizeX, object.getDef().sizeY)) {
			object.setLocation(closest.getX(), closest.getY(), closest.getPlane());
			return true;
		}
		return false;
	}

	public static int getClip(WorldTile tile) {
		return Region.get(tile.getRegionId()).getClipMap().getMasks()[tile.getPlane()][tile.getXInRegion()][tile.getYInRegion()];
	}
}
