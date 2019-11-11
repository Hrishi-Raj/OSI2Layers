package Protocols;

import java.io.UnsupportedEncodingException;

import NetworkLayer.Physical;

public class StopWait {
	Parity parity= new Parity();
	public byte[] sendAcknowledgement(Physical phy)
	{
		char flag='|';
		byte end= (byte) flag;
		byte l=0,cur;
		byte[] byteframe=null;
		l=phy.recieve();
		l=parity.ParityCheck(l);
		cur=phy.recieve();
		String s=""+(char)l;
		
		//s+=cur;
		while((cur!=end))
		{
			cur=parity.ParityCheck(cur);
			s+=(char)cur;
			cur=phy.recieve();
		}
		System.out.println("StopWait.SendAck:"+s);
		try {
			byteframe=s.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("StopWait.SendAckREAD:"+byteframe);
		return byteframe;
	}
	public void send(byte[] byteframe,Physical phy)
	{
		for(int i=0;i<byteframe.length;i++)
		{
			System.out.println("StopWait.Send:"+byteframe[i]);
			if(!phy.send(byteframe[i]))i--;
				
		}
	}
}
