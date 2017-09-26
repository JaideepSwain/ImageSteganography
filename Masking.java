import java.util.Random;

public class Masking {
public static String encrypt(String arg)
{
	System.out.println(arg);
	StringBuilder s=new StringBuilder();
	Random rand=new Random();
	int num1,num2;
	//char c;
	for(int i=0;i<arg.length();i++)
	{
		//c=arg.charAt(i);
		num1=rand.nextInt(255);
		//num2=(((int)arg.charAt(i))+num1)%256;
		num2=(int)arg.charAt(i)^num1;
		s.append((char)num2);
		s.append((char)num1);
		
	}
	System.out.println(s);
	return s.toString();
}
public static String decrypt(StringBuilder arg)
{
	System.out.println(arg);
	StringBuilder s=new StringBuilder();
	for(int i=0;i<arg.length();i+=2)
	{
		//s.append((char)(((int)arg.charAt(i)-(int)arg.charAt(i+1))%256));
		s.append((char)((int)arg.charAt(i)^(int)arg.charAt(i+1)));
	}
	System.out.println(s);
	return s.toString();
}
public static void main(String []arg) {
	Masking obj=new Masking();//project send kar or link bhi
	
	//String s=obj.encrypt("AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTTUUVVWWXXYYZZ11223344556677889900aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz");
	//System.out.println(s);
	StringBuilder sb=new StringBuilder(obj.encrypt("AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTTUUVVWWXXYYZZ11223344556677889900aabbccddeeffgghhiijjkkllmmnnooppqqrrssttuuvvwwxxyyzz"));
	System.out.println(obj.decrypt(sb));
}
}
