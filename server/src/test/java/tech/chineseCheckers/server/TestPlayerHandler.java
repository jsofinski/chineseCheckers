package tech.chineseCheckers.server;

import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class TestPlayerHandler {

	
	@Test
	void testReciveName() {
		
		/**
		 * Check if the server name registration works properly. 
		 * This test check if:
		 * - server recive name and parse it
		 * - server rejects name that was used by another user
		 * - server accept proper name
		 * 
		 * The proper communication should look like that:
		 * [S] NAME_GET
		 * [K] NAME_SET Mike
		 * [S] NAME_GET
		 * [K] NAME_SET ProperName
		 * [S] NAME_ACCEPTED
		 * [K] DISCONNECT - necessery to kill thread 
		 * [S] DISCONNECTED
		 */
		
		
		PlayerSocket mockedSocked = mock(PlayerSocket.class);
		SharedData mockedData = mock(SharedData.class);
		
		Set<String> names = new HashSet<String>();
		names.add("Mike");
		names.add("John");
		
		// Set messages send by client to server
		when(mockedSocked.recive()).thenReturn("NAME_SET Mike").thenReturn("NAME_SET ProperName").thenReturn("DISCONNECT");
		when(mockedData.getNames()).thenReturn(names);
		
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> sends = ArgumentCaptor.forClass(String.class);
		
		PlayerHandler ph = new PlayerHandler(mockedSocked, mockedData);
		ph.run();
		
		// Verify that ProperName was saved in Sharedata
		verify(mockedData).addName(argument.capture());
		// addName should be called only once with ProperName string as a argument
		assertTrue(argument.getAllValues().size() == 1);
		assertTrue(argument.getValue().equals("ProperName"));
		
		// Verify that proper messages were send to the client
		verify(mockedSocked, times(4)).send(sends.capture());
		// NAME_GET, NAME_GET, NAME_ACCEPTED, 
		assertTrue(sends.getAllValues().size() == 4);
		Iterator<String> it = sends.getAllValues().iterator();
		assertTrue(it.next().equals("NAME_GET"));
		assertTrue(it.next().equals("NAME_GET"));
		assertTrue(it.next().equals("NAME_ACCEPTED"));
		assertTrue(it.next().equals("DISCONNECTED"));
	}
}
