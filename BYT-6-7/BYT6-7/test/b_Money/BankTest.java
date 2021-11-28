package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank",SweBank.getName());
		assertEquals("DanskeBank",DanskeBank.getName());
		assertEquals("Nordea",Nordea.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK,SweBank.getCurrency());
		assertEquals(DKK,DanskeBank.getCurrency());
		assertEquals(SEK,Nordea.getCurrency());
	}
	
	// AccountDoesNotExistException when no changes were made to methods in classes
	@Test
	public void testOpenAccount() throws AccountExistsException {
		SweBank.openAccount("John");
	}
	
	// got NullPointerException  before changes were made to methods in classes
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		Nordea.deposit("Bob", new Money(5000, SEK));
		assertEquals(Integer.valueOf(5000),Nordea.getBalance("Bob"));
	}
	
	// got AccountDoesNotExistException  before changes were made to methods in classes
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		Nordea.withdraw("Bob", new Money(2000, SEK));
		assertEquals(Integer.valueOf(-2000),Nordea.getBalance("Bob"));
	}
	
	// got NullPointerException  before changes were made to methods in classes,
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		Nordea.deposit("Bob", new Money(5000, SEK));
		assertEquals(Integer.valueOf(5000),Nordea.getBalance("Bob"));
	}
	
	// failed before changes were made to methods in classes
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// the same bank
		SweBank.deposit("Bob", new Money(1000, SEK));
		SweBank.transfer("Bob", "Ulrika", new Money(1000, SEK));
		assertEquals(Integer.valueOf(0),SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(1000),SweBank.getBalance("Ulrika"));
		
		// from a bank to another bank
		SweBank.deposit("Bob", new Money(1000, SEK));
		SweBank.transfer("Bob", Nordea, "Bob", new Money(1000, SEK));
		assertEquals(Integer.valueOf(0),SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(1000),Nordea.getBalance("Bob"));
	}
	

	// twice invoked
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		SweBank.addTimedPayment(
				"Bob",
				"Bob's shopping",
				Integer.valueOf(10),
				Integer.valueOf(5),
				new Money(1000, SEK),
				Nordea,
				"Bob");
		
		SweBank.tick();
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(0), Nordea.getBalance("Bob"));
		for(int i=0; i<5; i++) {
			SweBank.tick();
		}
		assertEquals(Integer.valueOf(-1000), SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(1000), Nordea.getBalance("Bob"));
		
		SweBank.addTimedPayment(
				"Bob",
				"Bob's shopping",
				Integer.valueOf(10),
				Integer.valueOf(11),
				new Money(1000, SEK),
				Nordea,
				"Bob");
		SweBank.removeTimedPayment("Bob", "Bob's shopping");
		SweBank.tick();
		SweBank.tick();
		assertEquals(Integer.valueOf(-1000), SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(1000), Nordea.getBalance("Bob"));
	}
}
