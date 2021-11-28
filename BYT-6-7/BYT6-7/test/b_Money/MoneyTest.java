package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}
	
	/*
	 * we would like to be able to get amount of money. We are testing
	 */
	@Test
	public void testGetAmount() {
		assertEquals(Integer.valueOf(10000),SEK100.getAmount());
		assertEquals(Integer.valueOf(1000),EUR10.getAmount());
		assertEquals(Integer.valueOf(20000),SEK200.getAmount());
		assertEquals(Integer.valueOf(2000),EUR20.getAmount());
		assertEquals(Integer.valueOf(0),SEK0.getAmount());
		assertEquals(Integer.valueOf(0),EUR0.getAmount());
		assertEquals(Integer.valueOf(-10000),SEKn100.getAmount());
	}
	
	/*
	 * we would like to be able to get the currency of money. We are testing
	 * whether it is working properly- returns correct values. 
	 */
	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK200.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}
	
	/*
	 * we would like to be able to check whether the formatting is working properly,
	 * we get appropriate values.
	 */
	@Test
	public void testToString() {
		assertEquals("10000 SEK", SEK100.toString());
		assertEquals("1000 EUR", EUR10.toString());
		assertEquals("20000 SEK", SEK200.toString());
		assertEquals("2000 EUR", EUR20.toString());
		assertEquals("0 SEK", SEK0.toString());
		assertEquals("0 EUR", EUR0.toString());
		assertEquals("-10000 SEK", SEKn100.toString());
	}

	/*
	 * testing whether calculations for receiving a universal value
	 * are done correctly/
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(Double.valueOf(1000*1.5),EUR10.universalValue());
		assertEquals(Double.valueOf(10000*0.15),SEK100.universalValue());
			
	}
	
	/*
	 * testing whether 2 values of Money are the same. They can
	 * be of different currency. 
	 */
	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(EUR10));
		assertTrue(SEK0.equals(EUR0));
		assertFalse(SEK0.equals(EUR10));
		
	}
	
	/*
	 * testing whether we are be able to add an amount of money to the current amount. Checking
	 * calculations.
	 */
	@Test
	public void testAdd() {
		assertEquals(Double.valueOf(1000+10000*0.15/1.5),Double.valueOf(EUR10.add(SEK100).getAmount()));
		
	}
	
	/*
	 * testing whether we are be able to subtract an amount of money to the current amount. Checking
	 * calculations.
	 */
	@Test
	public void testSub() {
		assertEquals(Double.valueOf(20000-1000*1.5/0.15),Double.valueOf(SEK200.sub(EUR10).getAmount()));
	}
	
	/*
	 *testing whether money equals 0.
	 */
	@Test
	public void testIsZero() {
		assertFalse(SEK100.isZero());
		assertTrue(EUR0.isZero());
	}
	
	/*
	 * testing whether we are able to negate the value of money.
	 */
	@Test
	public void testNegate() {
		assertEquals(Integer.valueOf(-10000), SEK100.negate().getAmount());
	}
	
	/*
	 * testing whether we are able to compare values of money and distinguish between
	 * bigger, smaller values.
	 */
	@Test
	public void testCompareTo() {
		assertEquals("comparable",0, SEK100.compareTo(SEK100));
		assertEquals("comparable",1, SEK100.compareTo(SEK0));
		assertEquals("comparable",-1, SEK0.compareTo(SEK100));
	}
}
