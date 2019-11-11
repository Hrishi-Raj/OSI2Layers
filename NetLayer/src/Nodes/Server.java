package Nodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import NetworkLayer.DataLink;

public class Server {
	public static void main(String args[])throws IOException
	{
		DataLink dataLink= null;
		
		int port;
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		//port= Integer.parseInt(br.readLine());
		port=4100;
		dataLink=new DataLink(port);
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
