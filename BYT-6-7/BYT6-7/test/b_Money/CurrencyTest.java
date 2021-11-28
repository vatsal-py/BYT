package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}


	// checking whether we are able to get name of a currency.


	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	//we would like to be able to get rate of a currency.
	@Test
	public void testGetRate() {
		assertEquals(Double.valueOf(0.15), SEK.getRate());
		assertEquals(Double.valueOf(0.20), DKK.getRate());
		assertEquals(Double.valueOf(1.5), EUR.getRate());
	}
	
	/*
	 * testing if we are able to set rate of a currency. checking
	 * whether it is working properly- returns correct values. 
	 */
	@Test
	public void testSetRate() {
		SEK.setRate(2.0);
		assertEquals(Double.valueOf(2.0), SEK.getRate());
		// checking huge numbers
		SEK.setRate(5000000000000.0);
		assertEquals(Double.valueOf(5000000000000.0), SEK.getRate());
	}
	
	/*
	 * testing whether calculations are done correctly.
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(0.15*10, SEK.universalValue(10), 0.00001);
		assertEquals(0.20*10, DKK.universalValue(10), 0.00001);
		assertEquals(1.5*10, EUR.universalValue(10), 0.00001);
	}
	
	/*
	 *  testing whether calculations are done correctly.
	 */
	@Test
	public void testValueInThisCurrency() {
		assertEquals(10*0.20/0.15,SEK.valueInThisCurrency(10, DKK), 0.0001);
		assertEquals(10*0.15/0.20,DKK.valueInThisCurrency(10, SEK), 0.0001);
		assertEquals(10*1.5/0.20,DKK.valueInThisCurrency(10, EUR), 0.0001);
		
	}

}
