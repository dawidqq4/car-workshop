package carworkshop.client.gui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import carworkshop.client.*;
import carworkshop.visitor.*;
import entities.Order;
import entities.Worker;
import entities.orderstates.*;


public class WorkerGUI extends GUI {
	private Worker worker;
	
	public WorkerGUI(Worker worker) {
		this.worker = worker;
	}
	
	@Override
	public void showForm() throws IOException, ClassNotFoundException{
		Scanner read = new Scanner(System.in);
		Pattern choosePattern = Pattern.compile("[1-5]{1}");
		int chooseOption;
		String readedChoose;
		
		do {
			System.out.println("What you want to do? Choose option.");
			showChoose();
			readedChoose = read.nextLine();
			Matcher matcher = choosePattern.matcher(readedChoose);
			if (matcher.matches())
				chooseOption = Integer.parseInt(readedChoose);
			else {
				System.out.println("Wrong choose");
				continue;
			}
			
			switch(chooseOption) {
				case 1:
					showOrders();
					break;
				case 2:
					addRepairPrice();
					break;
				case 3: 
					repairCar();
					break;
				case 4: 
					read.close();
					System.exit(0);
			}
		} while (true);
	}
	
	private void showChoose() {
		System.out.println("1. Show my orders");
		System.out.println("2. Add price to order");
		System.out.println("3. Repair car");
		System.out.println("4. Exit");
	}
	
	private void showOrders() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		LinkedList<Order> orders = new LinkedList<Order>();
		connection.getOos().writeObject("findworkerorders");
		connection.getOos().writeObject(worker);
		while (true) {
			try {
				Order order = (Order) connection.getOis().readObject();
				if (order == null)
					break;
				else
					orders.add(order);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println(orders);
	}
	
	private void addRepairPrice() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		LinkedList<Order> orders = new LinkedList<Order>();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		connection.getOos().writeObject("findworkerorders");
		connection.getOos().writeObject(worker);
		while (true) {
			try {
				Order order = (Order) connection.getOis().readObject();
				if (order == null)
					break;
				else
					orders.add(order);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println(orders);
		
		System.out.println("Enter ID order: ");
		Integer id = reader.nextInt();
		connection.getOos().writeObject("findorder");
		connection.getOos().writeObject(id);
		Order order = (Order) connection.getOis().readObject();
		if (order.getId() == id && order.getStatus().equals("werification") && order.getPrice() == 0) {
			LinkedList<RepairMethod> rm = new LinkedList<RepairMethod>(); 
			RepairMethodVisitor rmv = null;
			rm.add(new WheelsRepair());
			rm.add(new EngineRepair());
			rm.add(new OilsRepair());
			rm.add(new OtherRepair());
			
			Pattern choosePattern = Pattern.compile("[tn]{1}");
			System.out.print("Car is new?? Press [t/n]");
			String readedChoose = reader.next();
			Matcher matcher = choosePattern.matcher(readedChoose);
	
			if (readedChoose.equals("t") && matcher.matches())
				rmv = new PriceOfRepairNewCar();
			if (readedChoose.equals("n") && matcher.matches())
				rmv = new PriceOfRepairOldCar();
			for (RepairMethod i : rm) {
				i.accept(rmv);
			}
			System.out.print("Enter price of repair: ");
			order.setPrice(reader.nextFloat());
			connection.getOos().writeObject("updateorder");
			connection.getOos().writeObject(order);
		} else
			System.out.println("Error order ID.");
	}
	
	private void repairCar() throws IOException, ClassNotFoundException {
		TcpConnectionClient connection = TcpConnectionClient.getInstance();
		LinkedList<Order> orders = new LinkedList<Order>();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		connection.getOos().writeObject("findworkerorders");
		connection.getOos().writeObject(worker);
		while (true) {
			try {
				Order order = (Order) connection.getOis().readObject();
				if (order == null)
					break;
				else
					orders.add(order);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println(orders);
		System.out.println("Enter ID order: ");
		Integer id = reader.nextInt();
		connection.getOos().writeObject("findorder");
		connection.getOos().writeObject(id);
		Order order = (Order) connection.getOis().readObject();
		if (order.getId() == id && order.getStatus().equals("inprogress")) {
			helpForm();
			System.out.println("Repairing...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
			order.setStatus("finished");
			order.setState(new FinishedState());
			connection.getOos().writeObject("updateorder");
			connection.getOos().writeObject(order);
		} else
			System.out.println("Error order ID.");
	}
	
	private void helpForm() {
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		Pattern choosePattern = Pattern.compile("[tn]{1}");
		System.out.print("Do you need help with localize elements in car?? Press [t/n]");
		String readedChoose = reader.nextLine();
		Matcher matcher = choosePattern.matcher(readedChoose);
		if (matcher.matches() && readedChoose.equals("t")) {
			NodeCarPart samochod = new NodeCarPart("Samoch�d");
			NodeCarPart nadwozie = new NodeCarPart("Nadwozie");
			samochod.add(nadwozie);
			NodeCarPart elementyZewnetrzne = new NodeCarPart("Elementy zewn�trzne");
			nadwozie.add(elementyZewnetrzne);
			elementyZewnetrzne.add(new LeafCarPart("Karoseria"));
			elementyZewnetrzne.add(new LeafCarPart("B�otnik"));
			elementyZewnetrzne.add(new LeafCarPart("Drzwi"));
			elementyZewnetrzne.add(new LeafCarPart("Pokrywa komory silnika"));
			elementyZewnetrzne.add(new LeafCarPart("Ch�odnica samochodowa"));
			elementyZewnetrzne.add(new LeafCarPart("Maskownica"));
			elementyZewnetrzne.add(new LeafCarPart("Listwy boczne"));
			elementyZewnetrzne.add(new LeafCarPart("Zderzak"));
			elementyZewnetrzne.add(new LeafCarPart("Hak"));
			elementyZewnetrzne.add(new LeafCarPart("Dach"));
			elementyZewnetrzne.add(new LeafCarPart("Dach lamelowy"));
			elementyZewnetrzne.add(new LeafCarPart("Lusterka boczne"));
			elementyZewnetrzne.add(new LeafCarPart("Szyby"));
			elementyZewnetrzne.add(new LeafCarPart("Szyby podgrzewane"));
			elementyZewnetrzne.add(new LeafCarPart("Wycieraczki"));
			elementyZewnetrzne.add(new LeafCarPart("Spryskiwacze"));
			elementyZewnetrzne.add(new LeafCarPart("Deflektor wiatrowy"));
			elementyZewnetrzne.add(new LeafCarPart("Spoiler"));
			
			NodeCarPart elementyWewnetrzne = new NodeCarPart("Elementy Wewn�trzne");
			nadwozie.add(elementyWewnetrzne);
			elementyWewnetrzne.add(new LeafCarPart("Deska rozdzielcza"));
			elementyWewnetrzne.add(new LeafCarPart("Tablica rozdzielcza"));
			elementyWewnetrzne.add(new LeafCarPart("Prze��czniki i regulatory"));
			elementyWewnetrzne.add(new LeafCarPart("Poduszka powietrzna"));
			elementyWewnetrzne.add(new LeafCarPart("Kierownica"));
			elementyWewnetrzne.add(new LeafCarPart("Fotele"));
			elementyWewnetrzne.add(new LeafCarPart("Pasy bezpiecze�stwa"));
			elementyWewnetrzne.add(new LeafCarPart("Lusterko wsteczne"));
			
			
			NodeCarPart podwozie = new NodeCarPart("Podwozie");
			samochod.add(podwozie);
			NodeCarPart ukladNapedowy = new NodeCarPart("Uk�ad nap�dowy");
			podwozie.add(ukladNapedowy);
			ukladNapedowy.add(new LeafCarPart("Sprz�g�o"));
			ukladNapedowy.add(new LeafCarPart("Skrzynia bieg�w"));
			ukladNapedowy.add(new LeafCarPart("Reduktor terenowy"));
			ukladNapedowy.add(new LeafCarPart("Skrzynia rozdzielacza"));
			ukladNapedowy.add(new LeafCarPart("Wa� nap�dowy"));
			ukladNapedowy.add(new LeafCarPart("Most nap�dowy"));
			ukladNapedowy.add(new LeafCarPart("O�"));
			ukladNapedowy.add(new LeafCarPart("P�o�"));
			ukladNapedowy.add(new LeafCarPart("Piasta ko�a"));
			ukladNapedowy.add(new LeafCarPart("Ko�o"));
			ukladNapedowy.add(new LeafCarPart("Obr�cz ko�a"));
			ukladNapedowy.add(new LeafCarPart("Opona"));
			ukladNapedowy.add(new LeafCarPart("Wentyl"));
			ukladNapedowy.add(new LeafCarPart("Peda� przyspieszenia"));
			ukladNapedowy.add(new LeafCarPart("Peda� sprz�g�a"));
			
			NodeCarPart ukladHamulcowy = new NodeCarPart("Uk�ad hamulcowy");
			podwozie.add(ukladHamulcowy);
			ukladHamulcowy.add(new LeafCarPart("Hamulec roboczy"));
			ukladHamulcowy.add(new LeafCarPart("Pompa hamulcowa"));
			ukladHamulcowy.add(new LeafCarPart("Wspomaganie"));
			ukladHamulcowy.add(new LeafCarPart("Przewody hamulcowe"));
			ukladHamulcowy.add(new LeafCarPart("Tarcza hamulcowa"));
			ukladHamulcowy.add(new LeafCarPart("B�ben hamulcowy"));
			ukladHamulcowy.add(new LeafCarPart("Klocki hamulcowe"));
			ukladHamulcowy.add(new LeafCarPart("Hamulec awaryjny"));
			ukladHamulcowy.add(new LeafCarPart("Zwalniacz"));
			
			NodeCarPart ukladKierowniczy = new NodeCarPart("Uk�ad kierowniczy");
			podwozie.add(ukladKierowniczy);
			ukladKierowniczy.add(new LeafCarPart("Kierownica samochodu"));
			ukladKierowniczy.add(new LeafCarPart("Kolumna kierownicy"));
			ukladKierowniczy.add(new LeafCarPart("Wspomaganie kierownicy"));
			ukladKierowniczy.add(new LeafCarPart("Przek�adnia kierownicza"));
			ukladKierowniczy.add(new LeafCarPart("Dr��ki"));
			ukladKierowniczy.add(new LeafCarPart("Temponat"));
			
			NodeCarPart zawieszenie = new NodeCarPart("Zawieszenie");
			podwozie.add(zawieszenie);
			zawieszenie.add(new LeafCarPart("Amortyzator"));
			zawieszenie.add(new LeafCarPart("Spr�yna"));
			zawieszenie.add(new LeafCarPart("Resor"));
			zawieszenie.add(new LeafCarPart("Kolumna McPhersona"));
			zawieszenie.add(new LeafCarPart("Stabilizator"));
			zawieszenie.add(new LeafCarPart("Wahacz"));
			
			NodeCarPart silnik = new NodeCarPart("Silnik");
			samochod.add(silnik);
			silnik.add(new LeafCarPart("T�ok"));
			silnik.add(new LeafCarPart("Pier�cienie t�okowe"));
			silnik.add(new LeafCarPart("Korbow�d"));
			silnik.add(new LeafCarPart("Wa� korbowy"));
			silnik.add(new LeafCarPart("G�owica silnika"));
			silnik.add(new LeafCarPart("Zaw�r"));
			silnik.add(new LeafCarPart("Wa�ek rozrz�du"));
			silnik.add(new LeafCarPart("Blok silnika"));
			silnik.add(new LeafCarPart("Tuleja cylindrowa"));
			silnik.add(new LeafCarPart("Turbospr�arka"));
			silnik.add(new LeafCarPart("�o�ysko"));
			silnik.add(new LeafCarPart("Ko�o zamachowe"));
			silnik.add(new LeafCarPart("Uszczelniacz"));
			
			NodeCarPart ukladySilnikaSpalinowego = new NodeCarPart("Uk�ady silnika spalinowego");
			samochod.add(ukladySilnikaSpalinowego);
			ukladySilnikaSpalinowego.add(new LeafCarPart("Ch�odzenia"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Do�adowania"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Rozruchowy"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Rozrz�du"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Smarowania"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Wtryskowy"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Wydechowy"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Zap�onowy"));
			ukladySilnikaSpalinowego.add(new LeafCarPart("Zasilaj�cy"));
			
			NodeCarPart inneUklady = new NodeCarPart("Inne uk�ady");
			samochod.add(inneUklady);
			NodeCarPart instalacjaElektryczna = new NodeCarPart("Instalacja elektryczna");
			inneUklady.add(instalacjaElektryczna);
			instalacjaElektryczna.add(new LeafCarPart("Alternator"));
			instalacjaElektryczna.add(new LeafCarPart("Pr�dnica"));
			instalacjaElektryczna.add(new LeafCarPart("Regulator napi�cia"));
			instalacjaElektryczna.add(new LeafCarPart("Akumulator"));
			instalacjaElektryczna.add(new LeafCarPart("Uk�ad wysokiego napi�cia"));
			instalacjaElektryczna.add(new LeafCarPart("�wieca zap�onowa"));
			instalacjaElektryczna.add(new LeafCarPart("�wieca �arowa"));
			instalacjaElektryczna.add(new LeafCarPart("Stacyjka"));
			instalacjaElektryczna.add(new LeafCarPart("Klakson"));
			instalacjaElektryczna.add(new LeafCarPart("O�wietlenie"));
			instalacjaElektryczna.add(new LeafCarPart("�wiat�a sygnalizacyjne"));
			instalacjaElektryczna.add(new LeafCarPart("Kierunkowskaz"));
			
			NodeCarPart ukladyBezpieczenstwa = new NodeCarPart("Uk�ady bezpiecze�stwa");
			samochod.add(ukladyBezpieczenstwa);
			NodeCarPart bierne = new NodeCarPart("Bierne");
			ukladyBezpieczenstwa.add(bierne);
			bierne.add(new LeafCarPart("Pasy bezpiecze�stwa"));
			bierne.add(new LeafCarPart("Napinacze pas�w"));
			bierne.add(new LeafCarPart("Poduszka powietrzna"));
			bierne.add(new LeafCarPart("Kurtyna powietrzna"));
			bierne.add(new LeafCarPart("Zag��wki"));
			bierne.add(new LeafCarPart("Wzmocnienia boczne"));
			bierne.add(new LeafCarPart("Strefy kontrolowanego zgniotu"));
			bierne.add(new LeafCarPart("Klatka bezpiecze�stwa"));
			bierne.add(new LeafCarPart("�amana kolumna kierownicy"));
			bierne.add(new LeafCarPart("Szyby hartowane"));
			bierne.add(new LeafCarPart("Szyby klejone"));
			bierne.add(new LeafCarPart("Bezpieczne zderzaki"));
			bierne.add(new LeafCarPart("Uk�ad odcinaj�cy dop�yw paliwa"));

			NodeCarPart czynne = new NodeCarPart("Czynne");
			ukladyBezpieczenstwa.add(czynne);
			czynne.add(new LeafCarPart("ABS"));
			czynne.add(new LeafCarPart("ASR"));
			czynne.add(new LeafCarPart("ESP"));
			czynne.add(new LeafCarPart("Nap�d na cztery ko�a"));
			
			System.out.println("Z kt�rym zesp�em cz�ci masz problem?");
			System.out.println("Wpisz [Nadwozie, Podwozie, Silnik, Uklady silnika, Inne uklady, Bezpieczenstwo]");
			do { 
				readedChoose = reader.nextLine();
				switch(readedChoose) {
					case "Nadwozie":
						printCarStructure(nadwozie,1);
						return;
					case "Podwozie":
						printCarStructure(podwozie,1);
						return;
					case "Silnik":
						printCarStructure(silnik,1);
						return;
					case "Uklady silnika":
						printCarStructure(ukladySilnikaSpalinowego,1);
						return;
					case "Inne uklady":
						printCarStructure(inneUklady,1);
						return;
					case "Bezpieczenstwo":
						printCarStructure(ukladyBezpieczenstwa,1);
						return;
				}
			} while(true);
		}
	}
	
	private static void printCarStructure(CarPart ncp, int tabulator) {
		if(ncp.getSubordinates() != null) {
			List<CarPart> parts = ncp.getSubordinates();
			for (CarPart i : parts) {
				for (int j = 0; j < tabulator; j++) {
					System.out.print("\t");
				}
				System.out.println(i.getName());
				printCarStructure(i, tabulator + 1);
			}
		}
	}
}
