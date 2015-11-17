package fr.cnrs.igmm.mg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class recursuveCombinations {

    public static <T extends Comparable<? super T>> List<List<T>> findSortedCombinations(Collection<T> elements, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        
        if (n == 0) {
            result.add(new ArrayList<T>());
            System.out.println("adding empty list");
            return result;
        }
        
        List<List<T>> combinations = findSortedCombinations(elements, n - 1);
        
        System.out.println(combinations);
        System.out.println("///////////////////////");
        System.out.println();
        for (List<T> combination: combinations) {
            for (T element: elements) {
                if (combination.contains(element)) {
                    continue;
                }
                System.out.println(combination);
                System.out.println(element);
                System.out.println();
                
                
                List<T> list = new ArrayList<T>();
                list.addAll(combination);
                //System.out.println((list));
                
                if (list.contains(element)) {
                    continue;
                }
                
                list.add(element);
                //sort items not to duplicate the items
                //   example: (a, b, c) and (a, c, b) might become  
                //   different items to be counted if not sorted   
                Collections.sort(list);
                
                if (result.contains(list)) {
                    continue;
                }
                
                result.add(list);
            }
        }
        
        return result;
    }
    
	public static void main(String[] args) {
		List<String> listOfNum = new ArrayList<String>(); 
		listOfNum.add(0, "2");
		listOfNum.add(1, "6");
		listOfNum.add(2, "8");
		listOfNum.add(3, "10");
		listOfNum.add(4, "9");
		listOfNum.add(5, "0");
		
		System.out.println(findSortedCombinations(listOfNum, 2));
		

	}

}
