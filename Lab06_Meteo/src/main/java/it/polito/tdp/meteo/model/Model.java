package it.polito.tdp.meteo.model;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO meteoDao;
	List<Citta> sequenzaFin;
	int min;
	int giorniCons;
	boolean controllo;

   //INIZIALIZZAZIONE
	public Model() {
		meteoDao=new MeteoDAO();
		controllo=true;
		sequenzaFin=new LinkedList<Citta>();
	}

	// of course you can change the String output with what you think works best
	public Map<String,Double> getUmiditaMedia(int mese) {
		return meteoDao.getRilevamentiPerLocalitaMese(mese);
		//potrei implementare la stampa come voglio qua al posto che usare toString
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		//prendo tutte le citta del database
		List<Citta> citta=this.meteoDao.getAllCitta();
		
		//creo mappa con le liste dei rilevamenti per ogni citta
		Map<Citta,List<Rilevamento>> rilevamenti=new HashMap<Citta,List<Rilevamento>>();
		
		//creo lista dove salvo man mano la sequenza durante la ricorsione
		List<Citta>sequenza=new LinkedList<Citta>();
		
		//per poi fare i controlli sui girni (es non meno di 3 e nn piu di 6per ogni citta)
		Map<Citta,Integer>giorniCitta=new HashMap<Citta,Integer>();
		
		int tot=0;
		
		//ciclo per riempire la mappa "rilevamenti" delle liste dei rilevamenti per ogni citta scelto il mese dall'utente
		//inizializzo mappa con solo le citta e conto dei giorni per ciascuna =0
		for(Citta c: citta) {
			 rilevamenti.put(c,new LinkedList<Rilevamento>(this.meteoDao.getAllRilevamentiLocalitaMese(mese, c.getNome())));	
			 giorniCitta.put(c,0);
		}
		
		//faccio ricursione
		calcolaSequenza(sequenza,tot,rilevamenti,citta,giorniCitta);
		
		//creo stringa dove salvo la risposta finale
		String s=new String();
		
		int i=1;  //serve solo per estestica output
		//riempio stringa finale
		for(Citta c:sequenzaFin) {
		s+=	i+") "+c+"\n";
		i++;
		}
		s+="COSTO TOTALE: "+min+" euro";
		return s;
	}
	
	private void calcolaSequenza(List<Citta> sequenza, int tot, Map<Citta, List<Rilevamento>> rilevamenti,
			List<Citta> citta, Map<Citta, Integer> giorniCitta) {
		
		//caso terminale 
		//CONTROLLO GIORNI TOTALI (racchiude gli altri controlli)
		if(sequenza.size() == NUMERO_GIORNI_TOTALI){
			
			for(Citta c:giorniCitta.keySet()) {
				if(giorniCitta.get(c)>NUMERO_GIORNI_CITTA_MAX)
					return ;
			}
			
			//CONTROLLO ALMENO UN GIORNO IN CITTA'
			for(Citta c:giorniCitta.keySet()) {
				if(giorniCitta.get(c)==0)
					return ;
			}
			
			//CONTROLLO GIORNI CONSECUTIVI
			for(int i=0;i<sequenza.size();i++) {
				//								diverso dall'elemento prima 					diverso dalla seconda citta che viene prima
				if((i==sequenza.size()-1) && !(sequenza.get(i).equals(sequenza.get(i-1))) && !(sequenza.get(i).equals(sequenza.get(i-2)))) {
				return ;
				}
				
				//se la citta non è la prima ad essere inserita nella sequenza parziale
				if(i!=0) {
					//se la citta è uguale a quella del giorno prima aumento i giornicons++
				if(sequenza.get(i).equals(sequenza.get(i-1))) {
					giorniCons++;
				}
				//	se la citta e diversa da quella inserita per il giorno prima
				else {
					//se i giorni consecutivi sono minori del minimo return
					if(giorniCons<NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
						return ;
					}
					giorniCons=1;
				}
				} 
				//se la citta viene inserita per la prima volta
				else {
					giorniCons=1;
				}
			}
			
			// la prim volta per impostare un valore iniziale di min devo accettare che il tot sia piu alto
			//poiche min all'inizio=0 e tot>0 ma appena messo un primo valore ci serve la condizione opposta
			if(controllo) {
			min=tot;
			controllo=false;  //rimarra sempre a FALSE dopo la prima volta che viene inserito un primo min=tot
			}else {  // tutte le altre volte controllo che il tot sia minore di min
				if(tot<min) {
					min=tot;
					sequenzaFin.clear();     // se il tot minore di min allora svuoto la soluzione totale trovata 
					for(Citta c:sequenza) {    // riempio con la nuova sequenza di citta
						sequenzaFin.add(c);
					}
				}
			}
			
			
		}    // finisce il primo caso terminale 
		
		else {                              // avviene la ricursione
			//CONTROLLO GIORNI TOTALI
			for(Citta c:giorniCitta.keySet()) {
				if(giorniCitta.get(c)>NUMERO_GIORNI_CITTA_MAX)
					return ;
			}
			
		for(Citta c: citta) {
			// aggiungo la citta alla sequenza temporanea
				sequenza.add(c);
			//aggiungo  un giorno in + alla rispettiva citta nella mappa
				giorniCitta.put(c,giorniCitta.get(c)+1);
			//calcolo una parte del totale--> costo umidita
				tot+=rilevamenti.get(c).get(sequenza.size()-1).getUmidita();
			//se ho inserito piu di 2 citta 
				if(sequenza.size()>=2) {
			//se la citta inserita è diversa da quella del giorno precedente aggiungo costo fisso 100 euro
				if(!(sequenza.get(sequenza.size()-1).equals(sequenza.get(sequenza.size()-2)))){
				tot+=COST;
				}
				
			//FACCIO RICORSIONE
				}
				calcolaSequenza(sequenza,tot,rilevamenti,citta,giorniCitta);
				
				//BACKTRACKING
				
				// se la sol parziale di sequenze di citta è magg di zero (contiene almeno una citta)
				if(sequenza.size()!=0) {
					// se ho almeno 2 citta inserite
				if(sequenza.size()>=2) {
					//se le cittta di 2 giorni consecutivi sono diverse allora devo togliere 100 euro dal totale 
					//poiche le avevo aggiunte durante lla ricorsione e tornando indietro (livello) non è detto che vada in un'altra citta
					if(!(sequenza.get(sequenza.size()-1).equals(sequenza.get(sequenza.size()-2)))){
						tot-=COST;
						}
					}
				//sottraggo i costi relativi alll'umidita di quel livello perche sto risalendo di lvello (quel giorno non lo considero +)
				tot-=rilevamenti.get(c).get(sequenza.size()-1).getUmidita();
				//tolgo l'ultima citta relativa all'ultimo giorno dalla sequenza temporanea
				sequenza.remove(sequenza.size()-1);
				//tolgo 1 giorno alla mappa dove ci sono salvati i giorni trascorsi per ciascuna citta
				giorniCitta.put(c,giorniCitta.get(c)-1);
				
				
				}
		}
			
			}
		
		
	}

	public List<Integer> getMesiDatabase(){
		return meteoDao.getMesiDatabase();
	}

}
