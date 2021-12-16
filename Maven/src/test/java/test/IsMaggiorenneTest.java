package test;

import root.util.Data;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IsMaggiorenneTest {

	@Test
	public void testMaggiorenne() {
		Data d = new Data(30,11,2000);
		boolean res = d.isMaggiorenne();
		assertEquals(res, true);
		res = new Data(16,12,2003).isMaggiorenne();
		assertEquals(res, true);
		res = new Data(17,12,2003).isMaggiorenne();
		assertEquals(res, false);
	}

}
