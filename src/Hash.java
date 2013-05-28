
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author richar
 */
public class Hash {
    
    public static void main(String[] args) {
        Hashtable<String, ArrayList> otro = new Hashtable<String, ArrayList>();
        ArrayList t = new ArrayList();
        ArrayList u = new ArrayList();
        
        u.add("payaso");
        u.add("coche");
        
        t.add(1);
        t.add(2);
        t.add("cadena");
        
        otro.put("a", t);
        otro.put("a", t).add("despues");
        otro.put("a", t).add("agregue");
        otro.put("a", t).add("otro");
        otro.put("b", u);
        otro.put("b", u).add("carrito");
        otro.put("b", u).add("camion");
        
        for (int i = 0; i < t.size(); i++) {
            System.out.print(otro.get("a").get(i));
        }
        
        
        System.out.print("\n###############################################\n");
        
        
        
        
        
        
        for (String image : otro.keySet()) {                        

            Object[] myArc = otro.get(image).toArray();
           
            for (int j = 0; j < otro.get(image).size(); j++) {
                System.out.println(image+" -> "+myArc[j]);
            }           
        
        }
        
        for (Enumeration<ArrayList> e = otro.elements(); e.hasMoreElements();)
            System.out.println(e.nextElement());
        
        
        
        System.out.print("\n###############################################\n");
        
        for (ArrayList arr : otro.values()) {

            for (int j = 0; j < arr.size(); j++) {
                System.out.print(arr.get(j) +" !\n");
            }
            
            
        }
        
    }
    
}




//        Hashtable<String, ArrayList> otro = new Hashtable<String, ArrayList>();
//        ArrayList t = new ArrayList();
//        ArrayList u = new ArrayList();
//        
//        u.add("payaso");
//        u.add("coche");
//        
//        t.add(1);
//        t.add(2);
//        t.add("cadena");
//        
//        otro.put("a", t);
//        otro.put("a", t).add("despues");
//        otro.put("a", t).add("agregue");
//        otro.put("a", t).add("otro");
//        otro.put("b", u);
//        otro.put("b", u).add("carrito");
//        otro.put("b", u).add("camion");
//
//


//System.out.print("\n___________________________________________\n");
//        System.out.print(otro.values().iterator().next()+" otro.values().iterator().next()\n");
//        System.out.print(otro.values()+" values\n");
//        System.out.print(otro.toString() +" otro.toString() \n");
//        
//        System.out.print(otro.elements().nextElement()+" otro.elements().nextElement()\n");



//Enumeration<String> enumer = otro.keys();
//        System.out.print(enumer.nextElement()+"\t-----------------------eumer\n");
//        System.out.print(enumer.nextElement()+"\t-----------------------eumer\n");


//Iterator<ArrayList> it = (Iterator<ArrayList>) otro.keys();
//        Iterator itx = (Iterator) otro.keys();
//        Iterator it2 = otro.values().iterator();
//        
//        System.out.println(itx.next());
//        System.out.println(itx.next());
//        System.out.println(it2.next());
//        System.out.println(it2.next());
//        System.out.println(it.next());
//        System.out.println(it.next());
//        System.out.println(otro.keySet()+"keyset ->>>>>>>>>>>>>>");