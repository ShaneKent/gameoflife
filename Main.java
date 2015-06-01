import processing.core.*;
import java.util.Random;

public class Main extends PApplet{

   private PImage nodeImage;
   private PImage mouseImage;

   private final int width = 640;
   private final int height = 480;

   private int tileSize = 16;
   private int widthTiles = this.width / this.tileSize;
   private int heightTiles = this.height / this.tileSize;

   public Node[][] world = new Node[widthTiles][heightTiles];
   
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
            setNodeAtPoint(world, pt, new DeadNode(pt));
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
            Node node = getNodeAtPoint(world, new Point(x, y));
            killOrDie[x][y] = node.action(this);
         }
      }
      for (int i = 0; i < widthTiles; i++){
         for (int j = 0; j < heightTiles; j++){
            Point pt = new Point(i, j);
            Node node = getNodeAtPoint(world, pt);
            if (killOrDie[i][j] == false && node.getClass() == AliveNode.class){
               setNodeAtPoint(world, pt, new DeadNode(pt));
            }else if (killOrDie[i][j] == true && node.getClass() == DeadNode.class){
               setNodeAtPoint(world, pt, new AliveNode(pt));
            }
         }
      }
   }

   public void drawMouse(){
      Point mouse = new Point(mouseX / tileSize, mouseY / tileSize);
      image(mouseImage, mouse.getX() * tileSize, mouse.getY() * tileSize, tileSize, tileSize);
   }

   public void drawNodesWorld(){
      for (int x = 0; x < widthTiles; x++){
         for (int y = 0; y < heightTiles; y++){
            if (world[x][y].getClass() == AliveNode.class){
               image(nodeImage, x * tileSize, y * tileSize, tileSize, tileSize);
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

         case 'q':
            if (!play && tileSize > 2){
               tileSize -= 2;
               Node[][] newWorld = new Node[this.width / this.tileSize][this.height / this.tileSize];
               for (int x = 0; x < (this.width / this.tileSize); x++){
                  for (int y = 0; y < (this.height / this.tileSize); y++){
                     Point pt = new Point(x,y);
                     setNodeAtPoint(newWorld, pt, new DeadNode(pt));
                  }
               }

               for (int x = 0; x < this.widthTiles; x++){
                  for (int y = 0; y < this.heightTiles; y++){
                     newWorld[x][y] = world[x][y];
                  }
               }

               this.widthTiles = this.width / this.tileSize;
               this.heightTiles = this.height / this.tileSize;
               world = newWorld;
            }
            break;

         case 'e':
            if (!play && tileSize < 32){
               tileSize += 2;
               Node[][] newWorld = new Node[this.width / this.tileSize][this.height / this.tileSize];
               for (int x = 0; x < (this.width / this.tileSize); x++){
                  for (int y = 0; y < (this.height / this.tileSize); y++){
                     Point pt = new Point(x, y);
                     setNodeAtPoint(newWorld, pt, getNodeAtPoint(world, pt));
                  }
               }

               this.widthTiles = this.width / this.tileSize;
               this.heightTiles = this.height / this.tileSize;
               world = newWorld;
            }
            break;

         case 'r':
            if (!play){
               Random r = new Random();
               for (int x = 0; x < this.widthTiles; x++){
                  for (int y = 0; y < this.heightTiles; y++){
                     int num = r.nextInt(2);
                     Point pt = new Point(x, y);
                     if (num == 0){
                        setNodeAtPoint(world, pt, new DeadNode(pt));
                     }else if (num == 1){
                        setNodeAtPoint(world, pt, new AliveNode(pt));
                     }
                  }
               }
            }
            break;
      }
   }

   public void mousePressed(){
      Point mouse = new Point(mouseX / tileSize, mouseY / tileSize);
      Node node = getNodeAtPoint(world, mouse);

      if (node.getClass() == DeadNode.class){
         setNodeAtPoint(world, mouse, new AliveNode(mouse));
      }
      else if (node.getClass() == AliveNode.class){
         setNodeAtPoint(world, mouse, new DeadNode(mouse));
      }
   }

   public void setNodeAtPoint(Node[][] array, Point pt, Node node){
      array[pt.getX()][pt.getY()] = node;
   }

   public Node getNodeAtPoint(Node[][] array, Point pt){
      return array[pt.getX()][pt.getY()];
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
