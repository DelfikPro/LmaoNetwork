package pro.delfik.lmao.util;

import implario.util.Converter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

import java.util.List;

import static java.lang.Double.valueOf;

/**
 * Аналог Location, но без ссылки на мир.
 * Используется для конфигурации местоположений без указания мира.
 *
 * @see Location
 * @see Vec3i
 */
public interface Vec {

	/**
	 * Конвертация в Bukkit-Vector.
	 *
	 * @return Вектор Bukkit с идентичными координатами.
	 */
	default Vector toBukkitVector(){
		return new Vector(x(), y(), z());
	}

	/**
	 * Конвертация в Bukkit-Location в указанном мире.
	 * Позиция находится в пределах этого блока, полезно для указания на блок,
	 * Когда точное местоположение не имеет значения.
	 *
	 * @param world Мир, в котором будет находится местоположение.
	 * @return Bukkit-Location
	 * @see Location
	 */
	default Location toBlock(World world){
		return new Location(world, x(), y(), z());
	}

	default Location toBlock(){
		return toBlock(U.world());
	}

	default Block block(World world){
		return toBlock(world).getBlock();
	}

	default Block block(){
		return block(U.world());
	}

	/**
	 * Конвертация в Bukkit-Location с точными координатами.
	 * Если точные координаты не указаны, возвращается позиция в центре блока.
	 * Полезно при телепортации игроков.
	 *
	 * @param world Мир, в котором будет находится местоположение.
	 * @return Bukkit-Location с точными координатами.
	 * @see Location
	 */
	default Location toLocation(World world){
		return new Location(world, x(), y(), z(), yaw(), pitch());
	}

	default Location toLocation(){
		return toLocation(U.world());
	}

	default double distance(Vec vec) {
		return Math.sqrt(
				NumberConversions.square(x() - vec.x()) +
				NumberConversions.square(y() - vec.y()) +
				NumberConversions.square(z() - vec.z())
		);
	}

	/**
	 * Конвертация в округлённый до int Vec3i.
	 *
	 * @return Vec3i с округлёнными координатами.
	 */
	default Vec3i toVec3i(){
		return new Vec3i(x(), y(), z());
	}

	double x();

	default int getX(){
		return (int)x();
	}

	double y();

	default int getY(){
		return (int)y();
	}

	double z();

	default int getZ(){
		return (int)z();
	}

	default float pitch(){
		return 0;
	}

	default float yaw(){
		return 0;
	}

	/**
	 * Генерация объекта Vec из строки.
	 *
	 * @param string Сериализованный Vec.
	 * @return Десериализованный Vec.
	 */
	static Vec toVec(String string) {
		String[] s = string.split(", ");
		if (s.length == 5)
			return new Vec5d(valueOf(s[0]), valueOf(s[1]), valueOf(s[2]), valueOf(s[3]), valueOf(s[4]));
		return new Vec3i(new Integer(s[0]), new Integer(s[1]), new Integer(s[2]));
	}

	static List<Vec> toVecList(List<String> list) {
		return Converter.transform(list, Vec::toVec);
	}

	default int distanceIntSquared(Vec vec) {
		Vec3i a = toVec3i();
		Vec3i b = vec.toVec3i();
		int x = a.x - b.x;
		int y = a.y - b.y;
		int z = a.z - b.z;
		return x * x + y * y + z * z;
	}

	default Vec add(int x, int y, int z) {
		Vec3i v = toVec3i();
		return new Vec3i(v.x + x, v.y + y, v.z + z);
	}

}
