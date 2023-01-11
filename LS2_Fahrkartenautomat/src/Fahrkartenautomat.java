import java.util.Scanner;

class Fahrkartenautomat {
	/*
	 * ANMERKUNG:
	 * Ich habe einige der Datentypen von double zu int geändert und rechne mit Cent-Beträgen,
	 * da es beim Subtrahieren der double-Werte zu Rundungsfehlern kam.
	 * (vgl. eine der ersten Zusatzaufgaben der Lern-Situation Fahrkartenautomat) 
	 */
	public static void main(String[] args) {
		int zuZahlenderBetragInCent;
		int eingezahlterGesamtbetragInCent;

		Scanner tastatur = new Scanner(System.in);
		
		begruessung();
		// 1 Kartenauswahl und Ticketanzahl
		zuZahlenderBetragInCent = fahrkartenbestellungErfassung(tastatur);
		// 2 Geldeinwurf
		eingezahlterGesamtbetragInCent = fahrkartenBezahlen(tastatur, zuZahlenderBetragInCent);
		// 3 Fahrscheinausgabe
		fahrkartenAusgeben();
		// 4 Rueckgeldberechnung und -ausgabe
		rueckgeldAusgeben(zuZahlenderBetragInCent, eingezahlterGesamtbetragInCent);

		tastatur.close();
	}
	
	public static void begruessung() {
		System.out.println("Herzlich Willkommen!\n");
	}
	
	public static int fahrkartenbestellungErfassung(Scanner tastatur) {
		double ticketPreis = 0.0f;
		int ticketAnzahl = 0;
		int zuZahlenInCent = 0;

		// 1 Geldbetrag eingeben
		System.out.println("Fahrkartenbestellvorgang:");
		System.out.println("=========================");
		
		System.out.println("\nWählen Sie ihre Wunschfahrkarte für Berlin AB aus:");
		
		boolean auswahlBeendet = false;
		while(!auswahlBeendet) {
			System.out.println("Kurzstrecke AB [2,00 EUR] (1)");
			System.out.println("Einzelfahrschein AB [3,00 EUR] (2)");
			System.out.println("Tageskarte AB [8,80 EUR] (3)");
			System.out.println("4-Fahrten-Karte AB [9,40 EUR] (4)");
			System.out.println("Bezahlen (9)\n");
			
			ticketPreis = 0;
			byte wahl;
			do {
				System.out.print("Ihre Wahl: ");
				wahl = tastatur.nextByte();
				switch(wahl) {
				case 1:
					ticketPreis = 2.00;
					break;
				case 2:
					ticketPreis = 3.00;
					break;
				case 3:
					ticketPreis = 8.80;
					break;
				case 4:
					ticketPreis = 9.40;
					break;
				case 9:
					// Der Kunde möchte keine weiteren Tickets:
					auswahlBeendet = true;
					break;
				default:
					System.out.println(" >>falsche Eingabe<<");
					wahl = -1;
					break;
				}
			} while(wahl == -1);
			
			// Der Kunde muss eine Ticketanzahl eingeben, falls er ein Ticket gewählt hat:
			if(!auswahlBeendet) {
				ticketAnzahl = -1;
				while(ticketAnzahl == -1) {
					System.out.print("Anzahl der Tickets: ");
					ticketAnzahl = tastatur.nextInt();
					if(ticketAnzahl < 1 || ticketAnzahl > 10) {
						System.out.println(" >> Wählen Sie bitte eine Anzahl von 1 bis 10 Tickets aus. <<");
						ticketAnzahl = -1;
					}
				}
				zuZahlenInCent += (int)(ticketPreis * 100 * ticketAnzahl);
				System.out.printf("\nZwischensumme: %.2f€\n\n", (double)(zuZahlenInCent/100.0));
			}
		}
		return zuZahlenInCent;
	}
	
	public static int fahrkartenBezahlen(Scanner tastatur, int zuZahlenInCent) {
		int eingezahlterGesamtbetragInCent = 0;
		double nochZuZahlen = 0.0;
		double eingeworfeneMuenze;
		while (eingezahlterGesamtbetragInCent < zuZahlenInCent) {
			nochZuZahlen = zuZahlenInCent - eingezahlterGesamtbetragInCent;
			System.out.printf("Noch zu zahlen: %.2f Euro\n", nochZuZahlen/100);
			System.out.print("Eingabe (mind. 5 Cent, höchstens 20 Euro): ");
			eingeworfeneMuenze = tastatur.nextDouble();
			int eingeworfenInCent = (int)(eingeworfeneMuenze * 100);
			switch(eingeworfenInCent) {
			case 5, 10, 20, 50, 100, 200, 500, 1000, 2000:
				eingezahlterGesamtbetragInCent = eingezahlterGesamtbetragInCent + eingeworfenInCent;
				break;
			default:
				System.out.println(">> Kein gültiges Zahlungsmittel");
			}
		}
		return eingezahlterGesamtbetragInCent;
	}
	
	public static void fahrkartenAusgeben() {
		System.out.println("\nFahrschein wird ausgegeben");
		for (int i = 0; i < 8; i++) {
			System.out.print("=");
			try {
				Thread.sleep(200);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\n");
	}
	
	public static void rueckgeldAusgeben(int zuZahlenInCent, int eingezahltInCent) {
		int rueckgabebetragInCent = eingezahltInCent - zuZahlenInCent;
		if (rueckgabebetragInCent > 0) {
			System.out.printf("Der Rückgabebetrag in Höhe von %.2f Euro\n", (double)(rueckgabebetragInCent/100.0));
			System.out.println("wird in folgenden Münzen ausgezahlt:");
			
			rueckgabebetragInCent = muenzRueckgabe(rueckgabebetragInCent, 200);
			rueckgabebetragInCent = muenzRueckgabe(rueckgabebetragInCent, 100);
			rueckgabebetragInCent = muenzRueckgabe(rueckgabebetragInCent, 50);
			rueckgabebetragInCent = muenzRueckgabe(rueckgabebetragInCent, 20);
			rueckgabebetragInCent = muenzRueckgabe(rueckgabebetragInCent, 10);
			muenzRueckgabe(rueckgabebetragInCent, 5);
		}

		System.out.println("\nVergessen Sie nicht, den Fahrschein\n" + "vor Fahrtantritt entwerten zu lassen!\n"
				+ "Wir wünschen Ihnen eine gute Fahrt.");
	}
	
	public static int muenzRueckgabe(int restGeld, int muenzBetragInCent) {
		while (restGeld >= muenzBetragInCent) {
			if(muenzBetragInCent >= 100) {
				System.out.println(muenzBetragInCent/100 + " Euro");
			}else {
				System.out.println(muenzBetragInCent + " Cent");
			}
			restGeld = restGeld - muenzBetragInCent;
		}
		return restGeld;
	}
}

