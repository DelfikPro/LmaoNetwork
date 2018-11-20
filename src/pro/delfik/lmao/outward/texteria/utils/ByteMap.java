/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.buffer.PooledByteBufAllocator
 *  io.netty.buffer.Unpooled
 *  net.minecraft.server.v1_8_R3.PacketDataSerializer
 */
package pro.delfik.lmao.outward.texteria.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class ByteMap
		extends HashMap<String, Object> {

	private static final long serialVersionUID = 4722366953612558988L;
	private static final byte TYPE_INT = 1;
	private static final byte TYPE_BYTE = 2;
	private static final byte TYPE_LONG = 3;
	private static final byte TYPE_STRING = 4;
	private static final byte TYPE_SHORT = 5;
	private static final byte TYPE_FLOAT = 6;
	private static final byte TYPE_DOUBLE = 7;
	private static final byte TYPE_BOOLEAN = 8;
	private static final byte TYPE_MAP = 9;
	private static final byte TYPE_BYTE_ARRAY = 10;
	private static final byte TYPE_STRING_ARRAY = 11;
	private static final byte TYPE_MAP_ARRAY = 12;
	private static final byte TYPE_VARINT = 13;
	private static final byte TYPE_LONG_VARINT = 14;
	private static final byte TYPE_UUID = 15;
	private static final byte TYPE_VARINT_ARRAY = 16;

	public ByteMap() {
	}

	public ByteMap(HashMap<String, Object> map) {
		super(map);
	}

	public ByteMap(byte[] bytes) {
		try {
			PacketDataSerializer in = new PacketDataSerializer(Unpooled.wrappedBuffer(bytes));
			while (in.readerIndex() < in.capacity()) {
				String key = in.c(256);
				Object[] arr;
				switch (in.readByte()) {
					case 1: {
						this.put(key, in.readInt());
						break;
					}
					case 13: {
						this.put(key, in.e());
						break;
					}
					case 2: {
						this.put(key, in.readByte());
						break;
					}
					case 3: {
						this.put(key, in.readLong());
						break;
					}
					case 14: {
						this.put(key, (long) in.e());
						break;
					}
					case 4: {
						this.put(key, in.c(32767));
						break;
					}
					case 5: {
						this.put(key, in.readShort());
						break;
					}
					case 6: {
						this.put(key, in.readFloat());
						break;
					}
					case 7: {
						this.put(key, in.readDouble());
						break;
					}
					case 8: {
						this.put(key, in.readBoolean());
						break;
					}
					case 15: {
						this.put(key, new UUID(in.readLong(), in.readLong()));
						break;
					}
					case 9: {
						byte[] mapBytes = new byte[in.e()];
						in.readBytes(mapBytes, 0, mapBytes.length);
						this.put(key, new ByteMap(mapBytes));
						break;
					}
					case 10: {
						byte[] b = new byte[in.e()];
						in.readBytes(b, 0, b.length);
						this.put(key, b);
						break;
					}
					case 16: {
						int[] a = new int[in.e()];
						for (int i = 0; i < a.length; ++i) a[i] = in.e();
						this.put(key, a);
						break;
					}
					case 11: {
						arr = new String[in.e()];
						for (int i = 0; i < arr.length; ++i) {
							arr[i] = in.c(32767);
						}
						this.put(key, arr);
						break;
					}
					case 12: {
						arr = new ByteMap[in.e()];
						for (int i = 0; i < arr.length; ++i) {
							byte[] mapBytes = new byte[in.e()];
							in.readBytes(mapBytes, 0, mapBytes.length);
							arr[i] = new ByteMap(mapBytes);
						}
						this.put(key, arr);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] toByteArray() {
		ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();
		try {
			PacketDataSerializer out = new PacketDataSerializer(buffer);
			for (Entry entry : this.entrySet()) {
				out.a((String) entry.getKey());
				Object val = entry.getValue();
				Class<?> clazz = val.getClass();
				if (clazz == Integer.class) {
					int num = (Integer) val;
					if (num >= 0 && num < 2097152) {
						out.writeByte(13);
						out.b(num);
						continue;
					}
					out.writeByte(1);
					out.writeInt(num);
					continue;
				}
				if (clazz == Float.class) {
					out.writeByte(6);
					out.writeFloat((Float) val);
					continue;
				}
				if (clazz == Byte.class) {
					out.writeByte(2);
					out.writeByte((int) (Byte) val);
					continue;
				}
				if (clazz == Short.class) {
					out.writeByte(5);
					out.writeShort((int) (Short) val);
					continue;
				}
				if (clazz == Long.class) {
					long num = (Long) val;
					if (num >= 0L && num < 0x200000L) {
						out.writeByte(14);
						out.b((int) num);
						continue;
					}
					out.writeByte(3);
					out.writeLong(num);
					continue;
				}
				if (clazz == String.class) {
					out.writeByte(4);
					out.a((String) val);
					continue;
				}
				if (clazz == Double.class) {
					out.writeByte(7);
					out.writeDouble((Double) val);
					continue;
				}
				if (clazz == Boolean.class) {
					out.writeByte(8);
					out.writeBoolean((Boolean) val);
					continue;
				}
				if (clazz == UUID.class) {
					out.writeByte(15);
					UUID uuid = (UUID) val;
					out.writeLong(uuid.getMostSignificantBits());
					out.writeLong(uuid.getLeastSignificantBits());
					continue;
				}
				if (clazz == ByteMap.class) {
					out.writeByte(9);
					byte[] bytes = ((ByteMap) val).toByteArray();
					out.b(bytes.length);
					out.writeBytes(bytes);
					continue;
				}
				if (clazz == byte[].class) {
					out.writeByte(10);
					byte[] bytes = (byte[]) val;
					out.b(bytes.length);
					out.writeBytes(bytes);
					continue;
				}
				if (clazz == int[].class) {
					out.writeByte(16);
					int[] arr = (int[]) val;
					out.b(arr.length);
					for (int str : arr) {
						out.b(str);
					}
					continue;
				}
				if (clazz == String[].class) {
					out.writeByte(11);
					String[] arr = (String[]) val;
					out.b(arr.length);
					for (String str : arr) {
						out.a(str);
					}
					continue;
				}
				if (clazz == ByteMap[].class) {
					out.writeByte(12);
					ByteMap[] arr = (ByteMap[]) val;
					out.b(arr.length);
					for (ByteMap map : arr) {
						byte[] serialized = map.toByteArray();
						out.b(serialized.length);
						out.writeBytes(serialized);
					}
					continue;
				}
				throw new IllegalStateException("Unknown value type " + clazz + " for key '" + entry.getKey() + "'");
			}
			byte[] bytes = new byte[buffer.writerIndex()];
			buffer.readBytes(bytes);
			buffer.release();
			return bytes;
		} catch (Exception e) {
			buffer.release();
			e.printStackTrace();
			return new byte[0];
		}
	}

	public String getString(String key) {
		return (String) this.get(key);
	}

	public byte getByte(String key) {
		return (Byte) this.get(key);
	}

	public short getShort(String key) {
		return (Short) this.get(key);
	}

	public float getFloat(String key) {
		return (Float) this.get(key);
	}

	public double getDouble(String key) {
		return (Double) this.get(key);
	}

	public int getInt(String key) {
		return (Integer) this.get(key);
	}

	public long getLong(String key) {
		return (Long) this.get(key);
	}

	public boolean getBoolean(String key) {
		return (Boolean) this.get(key);
	}

	public UUID getUUID(String key) {
		return (UUID) this.get(key);
	}

	public ByteMap getMap(String key) {
		return (ByteMap) this.get(key);
	}

	public byte[] getByteArray(String key) {
		return (byte[]) this.get(key);
	}

	public int[] getIntArray(String key) {
		return (int[]) this.get(key);
	}

	public String[] getStringArray(String key) {
		return (String[]) this.get(key);
	}

	public ByteMap[] getMapArray(String key) {
		return (ByteMap[]) this.get(key);
	}

	public String getString(String key, String def) {
		Object o = this.get(key);
		return o == null ? def : (String) o;
	}

	public byte getByte(String key, byte def) {
		Object o = this.get(key);
		return o == null ? def : (Byte) o;
	}

	public short getShort(String key, short def) {
		Object o = this.get(key);
		return o == null ? def : (Short) o;
	}

	public float getFloat(String key, float def) {
		Object o = this.get(key);
		return o == null ? def : (Float) o;
	}

	public double getDouble(String key, double def) {
		Object o = this.get(key);
		return o == null ? def : (Double) o;
	}

	public int getInt(String key, int def) {
		Object o = this.get(key);
		return o == null ? def : (Integer) o;
	}

	public long getLong(String key, long def) {
		Object o = this.get(key);
		return o == null ? def : (Long) o;
	}

	public boolean getBoolean(String key, boolean def) {
		Object o = this.get(key);
		return o == null ? def : (Boolean) o;
	}

	public ByteMap getMap(String key, ByteMap def) {
		Object o = this.get(key);
		return o == null ? def : (ByteMap) o;
	}

	@Override
	public String toString() {
		Iterator<Entry<String, Object>> i = this.entrySet().iterator();
		if (!i.hasNext()) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		do {
			Entry e = i.next();
			String key = (String) e.getKey();
			Object value = e.getValue();
			sb.append(key);
			sb.append('=');
			String val = value == this ? "(this Map)" : value instanceof byte[] ? Arrays.toString((byte[]) value) : value instanceof Object[] ? Arrays.toString((Object[]) value) : String.valueOf(
					value);
			sb.append(val);
			if (!i.hasNext()) {
				return sb.append('}').toString();
			}
			sb.append(',').append(' ');
		} while (true);
	}

}

