import java.util.*;
import java.io.*;

public class JosephusSim {
   private PersonNode circle;     // a PersonNode pointer that tracks first node
   private int size;              // the number of people in the circle
   private int eliminationCount;  // the number to count to for elimination       
   private PersonNode track;      // a PersonNode pointer to help with elimination

   public JosephusSim(String fileName) {
      try {
         // load names from the file in order, generating a singly linked list of PersonNodes
         Scanner file = new Scanner(new File(fileName));
         while (file.hasNextLine()) {
            String name = file.nextLine().trim();
            if (!name.isEmpty()) {
               add(name);
            }
         }
         
         // make the ring circular by attaching last node's next to front
         if (circle != null) {
            PersonNode curr = circle;
            while (curr.next != null && curr.next != circle) {
               curr = curr.next;
            }
            curr.next = circle;   // close the circle
            track = curr;         // track starts at last node
         }

         // generate random elimination count
         eliminationCount = (int)(Math.random() * size) + 1;

         System.out.println("=== Elimination count is " + eliminationCount + " ===");
         System.out.println("Remaining survivors: " + this);

      } catch(FileNotFoundException e) {
         System.out.println("Something went wrong with " + fileName);
      }
   }
   
   // optional helper method for constructing the circle
   private void add(String val) {
      PersonNode newNode = new PersonNode(val);

      if (circle == null) {
         circle = newNode;
         circle.next = circle;   // circular
         track = circle;
      } else {
         PersonNode temp = circle;
         while (temp.next != circle) {
            temp = temp.next;
         }
         temp.next = newNode;
         newNode.next = circle;
      }

      size++;
   }  

   public void eliminate() {
      // count to the elimination count
      for (int i = 1; i < eliminationCount; i++) {
         track = track.next;
      }

      // print who will be eliminated
      PersonNode eliminatedP = track.next;
      System.out.println(eliminatedP.name + " eliminated!");

      // eliminate the person and update "front" of the circle and size
      track.next = eliminatedP.next;

      if (eliminatedP == circle) {
         circle = eliminatedP.next;
      }

      size--;

      if (size > 1) {
         System.out.println("Remaining survivors: " + this);
      }
   }
   
   public boolean isOver() {
      // check if there's only one person left in the circle
      return circle.next == circle;
   }
   
   public String toString() {
      if (circle == null) return "";

      StringBuilder sb = new StringBuilder();
      PersonNode curr = circle;
      int index = 1;

      do {
         sb.append(index).append("-").append(curr.name);
         curr = curr.next;
         index++;
         if (curr != circle) sb.append(", ");
      } while (curr != circle);

      return sb.toString();
   }

   // Node class
   private static class PersonNode {
      String name;
      PersonNode next;

      PersonNode(String name) {
         this.name = name;
      }
   }
}

/*
 ----jGRASP exec: java JosephusDriver
 === Elimination count is 5 ===
 Remaining survivors: 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-Krystal, 6-River, 7-Soham, 8-Leon, 9-Will, 10-Qiao
 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-Krystal, 6-River, 7-Soham, 8-Leon, 9-Will, 10-Qiao
 
 Continue elimination? <press enter>
 
 Krystal eliminated!
 Remaining survivors: 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-River, 6-Soham, 7-Leon, 8-Will, 9-Qiao
 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-River, 6-Soham, 7-Leon, 8-Will, 9-Qiao
 
 Continue elimination? <press enter>
 
 Qiao eliminated!
 Remaining survivors: 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-River, 6-Soham, 7-Leon, 8-Will
 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-River, 6-Soham, 7-Leon, 8-Will
 
 Continue elimination? <press enter>
 
 River eliminated!
 Remaining survivors: 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-Soham, 6-Leon, 7-Will
 1-Muhammad, 2-Beza, 3-Ibrar, 4-Nur, 5-Soham, 6-Leon, 7-Will
 
 Continue elimination? <press enter>
 
 Beza eliminated!
 Remaining survivors: 1-Muhammad, 2-Ibrar, 3-Nur, 4-Soham, 5-Leon, 6-Will
 1-Muhammad, 2-Ibrar, 3-Nur, 4-Soham, 5-Leon, 6-Will
 
 Continue elimination? <press enter>
 
 Will eliminated!
 Remaining survivors: 1-Muhammad, 2-Ibrar, 3-Nur, 4-Soham, 5-Leon
 1-Muhammad, 2-Ibrar, 3-Nur, 4-Soham, 5-Leon
 
 Continue elimination? <press enter>
 
 Leon eliminated!
 Remaining survivors: 1-Muhammad, 2-Ibrar, 3-Nur, 4-Soham
 1-Muhammad, 2-Ibrar, 3-Nur, 4-Soham
 
 Continue elimination? <press enter>
 
 Muhammad eliminated!
 Remaining survivors: 1-Ibrar, 2-Nur, 3-Soham
 1-Ibrar, 2-Nur, 3-Soham
 
 Continue elimination? <press enter>
 
*/
