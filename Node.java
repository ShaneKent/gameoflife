import processing.core.*;

public class Node{
   private Point pt;
   
   public Node(Point pt){
      this.pt = pt;
   }

   public void setPosition(Point pt){
      this.pt = pt;
   }

   public Point getPosition(){
      return this.pt;
   }
}
