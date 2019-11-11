package NetworkLayer;

import java.io.DataInputStream; 
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Physical {
	
	private Socket socket            = null; 
    //private DataInputStream  input   = null; 
    private DataOutputStream out     = null;
    private ServerSocket server      = null;
    private DataInputStream  in      = null;
    public Physical(String address,int port)
    {
    	try {
    		socket= new Socket(address,port);
    		
    		//input = new DataInputStream(System.in);
    		out = new DataOutputStream(socket.getOutputStream());
    		in = new DataInputStream(socket.getInputStream());
    		System.out.println("Connected");
    	}
    	catch(UnknownHostException u)
    	{
    		u.printStackTrace();
    	} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    public Physical(int port)
    {
    	try {
			server=new ServerSocket(port);
			socket=server.accept();
			//input = new DataInputStream(System.in);
    		out = new DataOutputStream(socket.getOutputStream());
    		in = new DataInputStream(socket.getInputStream());
    		System.out.println("Connected");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    String encoding(byte bits)
    {
    	System.out.println("Physical.EncodingIN:"+bits+","+(char)bits);
    	String signal="";int level=0;
    	char pulse;
    	int largest=(byte) (1<<7);
    	for(int j=0,i=0;j<8;j++)
    	{
    		if(i==0&&j!=0)
    		{
    			pulse=(char) ('0'+level);
    			signal+=pulse;
    			level=0;
    		}
    		if((bits&largest)!=0)
    		{
    			level=level|1;
    			if(i==0)
    			level<<=1;
    			//System.out.print(level+","+i+"  ");
    		}
    		
    		bits<<=1;
    		i=(1-i);
    		//System.out.print(level+","+i+"  ");
    	}
    	pulse=(char) ('0'+level);
		signal+=pulse;
    	System.out.println("Physical.EncodingOUT:"+signal);
    	return signal;
    }
    byte decoding(String signal)
    {
    	byte bits=0;
    	System.out.println("Physical.decodingIN:"+signal);
    	for(int i=0;i<4;i++)
    	{
    		switch(signal.charAt(i))
    		{
    		case '0': bits<<=2;break;
    		case '1': bits<<=2;bits+=1;break;
    		case '2': bits<<=2;bits+=2;break;
    		case '3': bits<<=2;bits+=3;break;
    		}
    	}
    	System.out.println("Physical.decodeingOUT:"+bits);
    	return bits;
    }
    void noise()
    {
    	//
    }
    public boolean send(byte b)
    {
    	String s=this.encoding(b);
    	boolean by;
    	System.out.println("Physical.Send:"+s);
		try {
			out.writeUTF(s);
			while(in.available()<=0);
			by=in.readBoolean();
			System.out.println("Acknowledgeent:"+b+"\n");
			if(by)
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return false;
    }
    public byte recieve()
    {
    	String signal=null;
    	byte bits=0;
    	
    	try {
    		//int i=0;
			/*while(in.available()<=0&&i<1000)
				i++;
			if(i==100)
				return (byte)'|';*/
			signal=in.readUTF();	
			out.writeBoolean(true);
						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("Physical.recieve:"+signal);
    		bits=this.decoding(signal);
    		return bits;
    	
    }
}
