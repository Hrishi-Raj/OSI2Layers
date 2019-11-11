package Nodes;

import java.io.*;

import NetworkLayer.*;

public class Client {
	
	public static void main(String args[])throws IOException
	{
		DataLink dataLink= null;
		
		String serverAdd;int port;
		System.out.println("Server Address: ");
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		//serverAdd=br.readLine();
		serverAdd="127.000.000.001";
		System.out.println("port: ");
		//port= Integer.parseInt(br.readLine());
		port=4100;
		dataLink=new DataLink(serverAdd,port);
		String Smessage= "",Rmessage="";
		
		while(Rmessage!="Bye")
		{
			Smessage=br.readLine();
			dataLink.send(Smessage);
			Rmessage=dataLink.recieve();
			System.out.print("\nServer:\t"+Rmessage+"\n");
		}
	}
}
