package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {

//	private LinkedList<String> dizionario = new ArrayList<String>();
	private LinkedList<String> dizionario = new LinkedList<String>();
	
	public void clearDictionary() {
		dizionario.clear();
	}
	
	public void loadDictionary(String language) {
		try { 
			 FileReader fr = new FileReader(language+".txt"); 
			 BufferedReader br = new BufferedReader(fr);
			 String parola; 
			 while ((parola = br.readLine()) != null) { 
				 dizionario.add(parola.toLowerCase());
			 } 
			 br.close(); 
			 fr.close();
			 } catch (IOException e){ 
				 System.out.println("Errore nella lettura del file"); 
			 } 

	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		LinkedList<RichWord> l = new LinkedList<RichWord>();
		for (int i=0;i<inputTextList.size();i++) {
			if (dizionario.contains(inputTextList.get(i))) {
				l.add(new RichWord(inputTextList.get(i),true));
			}
			else {
				l.add(new RichWord(inputTextList.get(i),false));
			}
		}
		return l;
	}
	
	public List<RichWord> spellCheckTextDicotomica(List<String> inputTextList) {
		LinkedList<RichWord> l = new LinkedList<RichWord>();
		for (int i=0;i<inputTextList.size();i++) {
			boolean a = false;
			int low = 0;
			int high = dizionario.size()-1;
			while (low<=high) {
				int mid = (low+high)/2;
				if (dizionario.get(mid).compareTo(inputTextList.get(i))==0) {
					l.add(new RichWord(inputTextList.get(i),true));
					a=true;
					break;
				}
				else if (dizionario.get(mid).compareTo(inputTextList.get(i))<0) {
					low=mid+1;
				}
				else {
					high=mid-1;
				}
			}
			if(a==false) {
				l.add(new RichWord(inputTextList.get(i),false));
			}
		}
		return l;
	}
}
