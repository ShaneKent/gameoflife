import processing.core.*;

public class Main extends PApplet{

   private PImage nodeImage;
   private PImage mouseImage;

   private final int width = 640;
   private final int height = 480;

   private final int widthTiles = this.width / 16;
   private final int heightTiles = this.height / 16;

   private Node[][] world = new Node[widthTiles][heightTiles];
   
   private boolean play;
   private long nextTime;
   
   private long ANIMATION = 100;

   public void setup(){
      nodeImage = loadImage("images/node.png");
      mouseImage = loadImage("images/mouse.png");

      size(this.width, this.height);
      background(color(255, 255, 255));

      for (int x = 0; x < widthTiles; x++){
         for (int y = 0; y< heightTiles; y++){
            Point pt = new Point(x,y);
            setNodeAtPoint(pt, new DeadNode(pt));
         }
      }

      play = false;
      nextTime = System.currentTimeMillis();
   }

   public void draw(){
      long cur = System.currentTimeMillis();
      if (cur >= nextTime){
         if (play){
            playNodes();
         }
         nextTime = cur + ANIMATION;
      }

      background(color(255, 255, 255));
      drawNodesWorld();      
      drawMouse();
   }

   public void playNodes(){
      boolean[][] killOrDie = new boolean[widthTiles][heightTiles];
      for (int x = 0; x < widthTiles; x++){
         for (int y = 0; y < heightTiles; y++){
            Node node = getNodeAtPoint(new Point(x, y));
            killOrDie[x][y] = node.action(this);
         }
      }
      for (int i = 0; i < widthTiles; i++){
         for (int j = 0; j < heightTiles; j++){
            Point pt = new Point(i, j);
            Node node = getNodeAtPoint(pt);
            if (killOrDie[i][j] == false && node.getClass() == AliveNode.class){
               setNodeAtPoint(pt, new DeadNode(pt));
            }else if (killOrDie[i][j] == true && node.getClass() == DeadNode.class){
               setNodeAtPoint(pt, new AliveNode(pt));
            }
         }
      }
   }

   public void drawMouse(){
      Point mouse = new Point(mouseX / 16, mouseY / 16);
      image(mouseImage, mouse.getX() * 16, mouse.getY() * 16);
   }

   public void drawNodesWorld(){
      for (int x = 0; x < widthTiles; x++){
         for (int y = 0; y < heightTiles; y++){
            if (world[x][y].getClass() == AliveNode.class){
               image(nodeImage, x * 16, y * 16);
            }
         }
      }
   }

   public void keyPressed(){
      switch(key){
         case 's':
            play = !play;
            break;
         
         case 'z':
            ANIMATION += 10;
            break;

         case 'x':
            ANIMATION -= 10;
            break;
      }
   }

   public void mousePressed(){
      Point mouse = new Point(mouseX / 16, mouseY / 16);
      Node node = getNodeAtPoint(mouse);

      if (node.getClass() == DeadNode.class){
         setNodeAtPoint(mouse, new AliveNode(mouse));
      }
      else if (node.getClass() == AliveNode.class){
         setNodeAtPoint(mouse, new DeadNode(mouse));
      }
   }

   public void setNodeAtPoint(Point pt, Node node){
      world[pt.getX()][pt.getY()] = node;
   }

   public Node getNodeAtPoint(Point pt){
      return world[pt.getX()][pt.getY()];
   }
   
   public Point checkAndReturnPoint(Point pt){
      if ((pt.getX() >= 0 && pt.getX() < widthTiles) && (pt.getY() >= 0 && pt.getY() < heightTiles)){
         return pt;
      }
      return null;
   }

   public static void main(String[] args){
      PApplet.main("Main");
   }

}
