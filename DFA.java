import java.util.*;
class DFA
{
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the String");
		String s=sc.next();
		StringBuilder sb=new StringBuilder("Q0->");
		int state=0;
		int n=s.length();
		for(int i=0;i<n;i++)
		{
			switch(state)
			{
				case 0: if(s.charAt(i)=='a')
						state=1;
					else if(s.charAt(i)=='b')
						state=0;
					else
						state=0;
					sb.append("Q"+state);
					if(i!=n-1)
						sb.append("->");
					break;
				case 1: if(s.charAt(i)=='a')
						state=1;
					else if(s.charAt(i)=='b')
						state=2;
					else
						state=0;
					sb.append("Q"+state);
					if(i!=n-1)
						sb.append("->");
					break;
				case 2:  if(s.charAt(i)=='a')
						state=1;
					else if(s.charAt(i)=='b')
						state=0;
					else
						state=3;
					sb.append("Q"+state);
					if(i!=n-1)
						sb.append("->");
					break;	
				case 3: if(s.charAt(i)=='a')
						state=1;
					else if(s.charAt(i)=='b')
						state=0;
					else
						state=0;
					sb.append("Q"+state);
					if(i!=n-1)
						sb.append("->");
					break;
			}
		}
		if(state==3)
			System.out.println("String is Accepted\nState Transistions :- "+sb);
		else
			System.out.println("String is Rejected");
	}
}