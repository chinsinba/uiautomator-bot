package in.BBAT.dataMine.manager;

import org.eclipse.core.runtime.Path;

/**
 * @author Syed Mehtab
 */
public class MineManager {

	public static void createDb() throws Exception {
		String path ="127.0.0.1"+":"+"1527";
		MineManagerHelper.init("BBATDATA", true, true, path + Path.SEPARATOR + "sample","app","app");
	}
	public static void main(String[] args) {
		try {
			createDb();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
