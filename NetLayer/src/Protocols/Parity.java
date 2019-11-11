package Protocols;

public class Parity {
	public byte[] InsertParity(byte[] byteframe)
    {
		System.out.print("InsertParityIN:");
		for(int j=0;j<byteframe.length-1;j++)
		{
			System.out.print(byteframe[j]+",");
			byte parity=0,digit=1;
	    	for(int i=0;i<8;i++)
	    	{
	    		if((byteframe[j]&digit)!=0)
	    		{
	    			parity^=1;
	    		}
	    		digit<<=1;
	    	}
	    	if(parity==1)
	    		digit=(byte) (1<<7);
	    	else
	    		digit=0;
	    	byteframe[j]=(byte) (digit ^ byteframe[j]);
	    	System.out.print(byteframe[j]+" | ");
		}
		System.out.println("InsertParityOUT\n");
    	return byteframe;
    }
	public byte ParityCheck(byte bits)
	{
			System.out.println("ParityCheckIn:"+bits);
			byte parity=0,digit=1;
			for(int i=0;i<8;i++)
			{
				if((bits&digit)!=0)
				{
					parity^=1;
				}
				digit<<=1;
			}
			digit=(byte) (1<<7);
			if((bits&digit)!=0)
				bits^=digit;
			if(parity!=0)
				bits=-41;
		System.out.println("ParityCheckOUT:"+bits+"\n");	
		return bits;
	}
}
