import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;  
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
class FrameEG extends Frame 
{
FrameEG()
{
TVClient tvc=new TVClient();
add(tvc,BorderLayout.CENTER);  
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setSize(d.width,d.height);  
setVisible(true);
}
public static void main(String gg[])
{
FrameEG feg=new FrameEG();
}
} 



class TVClient extends Canvas implements MouseMotionListener,MouseListener,KeyListener
{
private BufferedImage bf;
TVClient()
{
addMouseMotionListener(this);
addMouseListener(this);
addKeyListener(this);
task();
}
public void task()
{
Timer timer = new Timer();
timer.schedule(new TimerTask() {
public void run() {
imageRequest();
}
}, 0, 2*1000);
}

public void imageRequest()
{
try
{
byte ak[]=new byte[1];
ak[0]=50;
Socket socket=new Socket("localhost",6030);
InputStream inputStream;
String FileName="ScreenShot.jpg";
File file=new File(FileName);
String request="IM@#";
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(request);
outputStreamWriter.flush();
byte fileLength[]=new byte[8];
byte dataPacket[]=new byte[1024];
inputStream=socket.getInputStream();
inputStream.read(fileLength);
long fL=(fileLength[0]<<56 |(fileLength[1] & 0xFF)<<48 |(fileLength[2] & 0xFF)<<40|(fileLength[3] & 0xFF)<<32|(fileLength[4] & 0xFF)<<24|(fileLength[5] & 0xFF)<<16|(fileLength[6] & 0xFF)<<8|(fileLength[7] & 0xFF));
outputStream.write(ak);
outputStream.flush();
//FileOutputStream fileOutputStream=new FileOutputStream(FileName);
ByteArrayOutputStream baos=new ByteArrayOutputStream();
int k=0;
for(long i=0;i<fL;)
{
k=inputStream.read(dataPacket);
i=i+k;
baos.write(dataPacket);
outputStream.write(ak);
outputStream.flush();
}
ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
this.bf=ImageIO.read(bais); 
this.repaint();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}//imageRequest

public void update(Graphics g)
{
g.drawImage(this.bf,0,0,null);
}

public void paint(Graphics g) 
{
g.drawImage(this.bf,0,0,null);
}


public void mouseMoved(MouseEvent ev)
{
try
{
Socket socket=new Socket("192.168.43.191",6030);
String reqs="MM"+"@"+String.valueOf(ev.getX())+"@"+String.valueOf(ev.getY())+"@#";
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(reqs);
outputStreamWriter.flush();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}

}

public void mouseDragged(MouseEvent ev)
{ 
try
{
//System.out.println("MD chala");
Socket socket=new Socket("192.168.43.191",6030);
String reqs="MD"+"@"+String.valueOf(ev.getX())+"@"+String.valueOf(ev.getY())+"@#";
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(reqs);
outputStreamWriter.flush();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}


public void mouseClicked(MouseEvent e)
{
try
{
Socket socket=new Socket("192.168.43.191",6030);
String reqs="MC"+"@"+String.valueOf(e.getButton())+"@#";
System.out.println(e.getButton());
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(reqs);
outputStreamWriter.flush();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}

public void mouseEntered(MouseEvent e)
{
//
}

public void mouseExited(MouseEvent e)
{
//
}

public void mousePressed(MouseEvent e)
{
try
{
Socket socket=new Socket("localhost",6030);
String reqs="MP"+"@"+String.valueOf(e.getButton())+"@#";
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(reqs);
outputStreamWriter.flush();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}


public void mouseReleased(MouseEvent e)
{
try
{
Socket socket=new Socket("localhost",6030);
String reqs="MR"+"@"+String.valueOf(e.getButton())+"@#";
System.out.println(e.getButton());
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(reqs);
outputStreamWriter.flush();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}

public void keyPressed(KeyEvent e)
{
try
{
Socket socket=new Socket("localhost",6030);
String reqs="KP"+"@"+e.getKeyChar()+"@#";
//System.out.println(reqs);
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(reqs);
outputStreamWriter.flush();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}//keyPressed

public void keyReleased(KeyEvent e)
{
/*
try
{
Socket socket=new Socket("192.168.43.191",6030);
String reqs="KP"+"@"+String.valueOf(e.getKeyCode())+"@#";
System.out.println(reqs);
OutputStream outputStream=socket.getOutputStream();
OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream);
outputStreamWriter.write(reqs);
outputStreamWriter.flush();
socket.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
*/
}//keyReleased

public void keyTyped(KeyEvent e)
{
//

}
}//class tvclient
