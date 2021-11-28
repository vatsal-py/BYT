package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() throws AccountDoesNotExistException {
		//adding
		testAccount.addTimedPayment("Hans transaction",
				Integer.valueOf(10),
				Integer.valueOf(2),
				new Money(10000000, SEK),
				SweBank,
				"Alice");
		testAccount.tick();
		assertEquals("10000000 SEK",testAccount.getBalance().toString());
		testAccount.tick();
		testAccount.tick();
		assertEquals("0 SEK",testAccount.getBalance().toString());
		assertEquals("11000000",SweBank.getBalance("Alice").toString());
		
		// removing
		testAccount.addTimedPayment("Hans transaction",
				Integer.valueOf(10),
				Integer.valueOf(0),
				new Money(10000000, SEK),
				SweBank,
				"Alice");
		testAccount.removeTimedPayment("Hans transaction");
		testAccount.tick();
		assertEquals("0 SEK",testAccount.getBalance().toString());
		assertEquals("11000000",SweBank.getBalance("Alice").toString());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("Hans transaction",
				Integer.valueOf(10),
				Integer.valueOf(2),
				new Money(10000000, SEK),
				SweBank,
				"Alice");
		assertTrue(testAccount.timedPaymentExists("Hans transaction"));
	}

	@Test
	public void testAddWithdraw() {
		testAccount.withdraw(new Money(10000000, SEK));
		assertEquals("0 SEK", testAccount.getBalance().toString());
		testAccount.deposit(new Money(100, SEK));
		assertEquals("100 SEK", testAccount.getBalance().toString());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(Integer.valueOf(10000000),testAccount.getBalance().getAmount());
		assertEquals(SEK,testAccount.getBalance().getCurrency());
	}
}
