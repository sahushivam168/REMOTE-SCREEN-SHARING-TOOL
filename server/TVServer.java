import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.*;
import java.io.*;
import java.awt.event.InputEvent;
class TVServer 
{
private int port;
private ServerSocket serverSocket;
TVServer(int port)
{ 
try
{
this.port=port;
serverSocket=new ServerSocket(port);
startListening();
}catch(Exception ee)
{
System.out.println("Cannot start server : "+ee.getMessage());
System.exit(0);
}
}

public void startListening()
{
Socket xyz;
while(true)
{
System.out.println("Server is ready and is listening on port : "+port);
try
{
xyz=serverSocket.accept();
RequestProcess rp=new RequestProcess(xyz);
}catch(Exception e)
{
System.out.println(e);
}
}
}
public static void main(String gg[])
{
TVServer tvs=new TVServer(Integer.parseInt(gg[0]));
}
}

class RequestProcess extends Thread
{
Socket socket;
RequestProcess(Socket s)
{
this.socket=s;
start();
}
public void run()
{
try
{
String request;
StringBuffer stringBuffer;
InputStreamReader inputStreamReader;
InputStream inputStream;
FileInputStream fileInputStream;
OutputStream outputStream;
FileOutputStream fileOutputStream;
inputStream=socket.getInputStream();
inputStreamReader=new InputStreamReader(inputStream);
stringBuffer=new StringBuffer();
int x;
while(true)
{
x=inputStreamReader.read();
if(x=='#' || x==-1) break;
stringBuffer.append((char)x);
}
request=stringBuffer.toString();
int m=request.indexOf('@');
String req=request.substring(0,m);

if(req.equalsIgnoreCase("IM"))
{
String fileName=screenCapture();
File file=new File(fileName);
System.out.println("screen Captured");
byte dataPacket[]=new byte[1024];
fileInputStream=new FileInputStream(file);
long fL=file.length();
byte fileLength[]=new byte[8];
fileLength[0]=(byte)(fL>>56);
fileLength[1]=(byte)(fL>>48);
fileLength[2]=(byte)(fL>>40);
fileLength[3]=(byte)(fL>>32);
fileLength[4]=(byte)(fL>>24);
fileLength[5]=(byte)(fL>>16);
fileLength[6]=(byte)(fL>>8);
fileLength[7]=(byte)(fL>>0);
outputStream=socket.getOutputStream();
outputStream.write(fileLength);
outputStream.flush();
inputStream.read();
long i=0;
while(i<fL)
{
i=i+fileInputStream.read(dataPacket);
outputStream.write(dataPacket);
outputStream.flush();
inputStream.read();
}
}//IMG


if(req.equalsIgnoreCase("MM"))//MouseMove
{
int r1=request.indexOf('@',m+1);
int xc=Integer.parseInt(request.substring(m+1,r1));
int r2=request.indexOf('@',r1+1);
int yc=Integer.parseInt(request.substring(r1+1,r2));
try
{
Robot robot = new Robot();
robot.mouseMove(xc,yc);
}catch(Exception e)
{
System.out.println(e);
}
}//MM

if(req.equalsIgnoreCase("MC"))//MouseClicked
{
int rq1=request.indexOf('@',m+1);
int bt=Integer.parseInt(request.substring(m+1,rq1));
System.out.println(bt);
try
{
Robot robot = new Robot();
if(bt==1)
{
robot.mousePress(InputEvent.BUTTON1_MASK);
robot.mouseRelease(InputEvent.BUTTON1_MASK);
}
if(bt==2)
{
robot.mousePress(InputEvent.BUTTON2_MASK);
robot.mouseRelease(InputEvent.BUTTON2_MASK);
}
if(bt==3)
{
robot.mousePress(InputEvent.BUTTON3_MASK);
robot.mouseRelease(InputEvent.BUTTON3_MASK);
}
}catch(Exception e)
{
System.out.println(e);
}
}//MC

if(req.equalsIgnoreCase("MD"))//MouseDragged
{
int mdx=request.indexOf('@',m+1);
int mx=Integer.parseInt(request.substring(m+1,mdx));
int mdy=request.indexOf('@',mdx+1);
int my=Integer.parseInt(request.substring(mdx+1,mdy));
try
{
Robot robot = new Robot();
robot.mouseMove(mx,my);
}catch(Exception e)
{
System.out.println(e);
}
}//MD

if(req.equalsIgnoreCase("MP"))//MousePressed
{
int rqst1=request.indexOf('@',m+1);
int bt2=Integer.parseInt(request.substring(m+1,rqst1));
System.out.println(bt2);
try
{
Robot robot = new Robot();
if(bt2==1)
{
robot.mousePress(InputEvent.BUTTON1_MASK);
//robot.mouseRelease(InputEvent.BUTTON1_MASK);
}
if(bt2==2)
{
robot.mousePress(InputEvent.BUTTON2_MASK);
//robot.mouseRelease(InputEvent.BUTTON2_MASK);
}
if(bt2==3)
{
robot.mousePress(InputEvent.BUTTON3_MASK);
//robot.mouseRelease(InputEvent.BUTTON3_MASK);
}
}catch(Exception e)
{
System.out.println(e);
}
}

if(req.equalsIgnoreCase("MR"))//MouseReleased
{
int rqs1=request.indexOf('@',m+1);
int bt111=Integer.parseInt(request.substring(m+1,rqs1));
System.out.println(bt111);
try
{
Robot robot = new Robot();
if(bt111==1)
{
robot.mouseRelease(InputEvent.BUTTON1_MASK);
}
if(bt111==2)
{
robot.mouseRelease(InputEvent.BUTTON2_MASK);
}
if(bt111==3)
{
robot.mouseRelease(InputEvent.BUTTON3_MASK);
}
}catch(Exception e)
{
System.out.println(e);
}
}

if(req.equalsIgnoreCase("KP"))
{
int kr=request.indexOf('@',m+1);
int kc=Integer.parseInt(request.substring(m+1,kr));
System.out.println(kc);
try
{
Robot robot = new Robot();
robot.keyPress(kc);
}catch(Exception e)
{
System.out.println(e);
}
}//KeyPressed

if(req.equalsIgnoreCase("KR"))
{
int kr1=request.indexOf('@',m+1);
int kc1=request.substring(m+1,kr1);
System.out.println(kc1);
try
{
Robot robot = new Robot();
robot.keyRelease(kc1);
}catch(Exception e)
{
System.out.println(e);
}
}//KeyReleased

if(req.equalsIgnoreCase("KT"))
{
//

}//KeyType




}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}

}//run

public String screenCapture()
{
try 
{
Robot robot = new Robot();
String format = "jpg";
String fileName ="ScreenShot."+format;
Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
ImageIO.write(screenFullImage,format,new File(fileName));
return fileName;
}catch (Exception e) 
{
return e.getMessage();
}
}//scrnCapture


}//class