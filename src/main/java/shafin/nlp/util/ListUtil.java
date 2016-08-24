package shafin.nlp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ListUtil {

	public static List<String> pickNRandom(List<String> lst, int n) {
		List<String> copy = new LinkedList<String>(lst);
		Collections.shuffle(copy);
		return copy.subList(0, n);
	}
	
	public static <E> List<E> intersect(List<E> a, List<E> b){
		List<E> intersects = new ArrayList<>();
		for(E aE: a){
			if(b.contains(aE)){
				intersects.add(aE);
			}
		}
		return intersects;
	}
	
	public static <E> List<E> AminusB(List<E> a, List<E> b){
		List<E> minus = new ArrayList<>();
		for(E aE: a){
			if(!b.contains(aE)){
				minus.add(aE);
			}
		}
		return minus;
	}
	
	public static void main(String[] args) {
        List<String> myList = new ArrayList<String>();
        myList.add("one");
        myList.add("two");
        myList.add("three");
        myList.add("four");
        myList.add("five");

        printList(myList);
    }

    public static void printList(List<String> myList) {
        for (String string : myList) {
            System.out.println(string);
        }
    }
}
