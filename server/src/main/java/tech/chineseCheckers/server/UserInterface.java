package tech.chineseCheckers.server;

import java.util.Scanner;

public class UserInterface {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void print(String str) {
		System.out.println(str);
	}
	public static String getString() {
		String str = sc.nextLine();
		return str;
	}
	public static String getString(String prompt) {
		print(prompt);
		String str = sc.nextLine();
		return str;
	}
	public static int getInt(String prompt) {
		do {
			try {
				print(prompt);
				String temp = getString("");
				int r = Integer.parseInt(temp);
				return r;
			}
			catch (NumberFormatException e) {
				print("Podana liczba musi byc cyfra!");
			}
		}while(true);
	}
}
