
public class Main {
	public static void main(String args[]) {
		String[] r = Comune.GetAllRegion();
		for (int i = 0; i < r.length; i++) {
			System.out.println(r[i]);
			String[] p = Comune.GetAllProvince(r[i]);
			for (int j = 0; j < p.length; j++) {
				System.out.println(p[j]);
				String[] c = Comune.GetAllComuni(p[j]);
				for (int k = 0; k < c.length; k++) {
					//System.out.print(c[k]);
				}
			}
		}
	}
}
