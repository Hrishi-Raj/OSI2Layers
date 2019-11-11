package NetworkLayer;

import java.nio.charset.StandardCharsets;

import Protocols.Parity;
import Protocols.StopWait;

public class DataLink {
	
    private String flag,address="127.000.000.001";
    private char esc;
	int port;
	private Physical phy=null;
	
	public DataLink(String address,int port)
	{
		this.address=address;
		this.port=port;
		this.phy=new Physical(address,port);
		flag="|";
		esc='~';
	}
	public DataLink(int port)
	{
		this.port=port;
		this.phy=new Physical(port);
		flag="|";
		esc='~';
	}
	Physical getPhyLayer()
	{
		return this.phy;
	}
	byte[] framing(String segment)
	{
		System.out.println("DataLink.framingIN:"+segment);
		String frame="";
		frame+=flag;
		frame+=address;
		frame+=":";
		
		for(int i=0;i<segment.length();i++)
		{
			if(segment.charAt(i)==esc||i%4==0)
				frame+=esc;
			frame=frame+Character.toString(segment.charAt(i));
		}
		frame+=esc;
		frame+=flag;
		System.out.println("DataLink.framingOUT:"+frame);
		byte[] byteframe;
		byteframe = frame.getBytes(StandardCharsets.UTF_8);
		System.out.println("DataLink.framingOUTBYTE:"+byteframe);
		return byteframe;
	}
	String deframing(byte[] byteframe)
	{
		System.out.println("DataLink.deframingIN:"+byteframe);
		String segment="";
		String frame = new String(byteframe,StandardCharsets.UTF_8);
		System.out.print("DataLink.deframingSTR:");
		System.out.println(frame);
		for(int i=17;i<frame.length();i++)
		{
			if(frame.charAt(i)==esc&&frame.charAt(i-1)==esc)
			{
				segment=segment.concat(Character.toString(esc));
			}
			else if(frame.charAt(i)!=esc){
			segment=segment.concat(Character.toString(frame.charAt(i)));
			}
		}
		System.out.println("DataLink.DeframingOUT:"+segment);
		return segment;
		
	}
	void FlowCtrl(byte[] byteframe)
	{
		System.out.println("DataLink.FlowControl:-");
		StopWait stopwait=new StopWait();
		stopwait.send(byteframe,phy);
	}
	byte[] ErrorCtrl(byte[] byteframe)
	{
		System.out.println("DataLink.ErrorControl:-");
		Parity parity=new Parity();
		return parity.InsertParity(byteframe);
	}
	byte ErrorCheck(byte bits)
	{
		System.out.println("DataLink.ErrorControl:-");
		Parity parity=new Parity();
		return (parity.ParityCheck(bits));
	}
	public void send(String message)
	{
		System.out.println("DataLinkSend:"+message);
		byte[] byteframe=this.framing(message);
		byteframe=this.ErrorCtrl(byteframe);
		this.FlowCtrl(byteframe);
	}
	public String recieve()
	{
		StopWait stopwait=new StopWait();
		byte[] byteframe=stopwait.sendAcknowledgement(phy);	
		String message=this.deframing(byteframe);
		System.out.println("DataLinkRecieve:"+message);
		return message;
	}
}
