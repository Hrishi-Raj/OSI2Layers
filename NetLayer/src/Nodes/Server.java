package Nodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import NetworkLayer.DataLink;
import NetworkLayer.Physical;

public class Server {
	public static void main(String args[])throws IOException
	{
		DataLink dataLink= null;
		//Physical physical=null;
		int port;
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		//port= Integer.parseInt(br.readLine());
		port=4100;
		//physical = new Physical(port);
		dataLink=new DataLink(port);// TODO framing address
		String Smessage= "",Rmessage="";
		
		while(Rmessage!="Bye")
		{
			Rmessage=dataLink.recieve();
			System.out.print("\nClient:\t"+Rmessage+"\n");
			Smessage=br.readLine();
			dataLink.send(Smessage);
			
		}
		
		
	}
}
