import java.util.*;

class AllData
{
	Scanner sc=new Scanner(System.in);
	String [][] user=new String[21][]; 
	String defaultPassword="2610";       //Default password for admin login
	int addUser=14;
	String costumerDefault="1111";      //New costumer default password
	
	//0-costumer id  1-password/pin no  2-first name   3-last name  4-amount present
	
	AllData()
	{
		user[0]=new String[]{"19283","7733","Karvi","Joshi","90000"};
		user[1]=new String[]{"14343","0711","Dilkha","Puliyapara","200"};
		user[2]=new String[]{"24216","7777","Shikha","Pande","70000000"};
		user[3]=new String[]{"46334","7584","Aarav","Gupta","38200"};
		user[4]=new String[]{"54292","4389","Kavya","Menon","500000"};
		user[5]=new String[]{"65928","2465","Nevil","Suthar","81200"};
		user[6]=new String[]{"30478","2572","Akshay","Singh","5000"};
		user[7]=new String[]{"73736","6438","Tanvi","Desai","700"};
		user[8]=new String[]{"38857","2809","Sakshi","Singh","45000"};
		user[9]=new String[]{"86436","3470","Siddharth","Mehta","1050000"};
		user[10]=new String[]{"72459","2510","Disha","Porwal","92300"};
		user[11]=new String[]{"64739","1110","Daksh","Shah","47490"};
		user[12]=new String[]{"48634","1432","Devanshi","Soni","50000"};
		user[13]=new String[]{"17063","7455","Vishwa","Kasundra","10000"};
		user[14]=new String[]{"","","",""};
		user[15]=new String[]{"","","",""};
		user[16]=new String[]{"","","",""};
		user[17]=new String[]{"","","",""};
		user[18]=new String[]{"","","",""};
		user[19]=new String[]{"","","",""};
		user[20]=new String[]{"","","",""};
	}
	
	void customerLogin()         //Customer login
	{
		System.out.print("\nPlease enter your customer ID: ");
		String customerId=sc.nextLine();
		int j=0;
		for(int i=0;i<user.length;i++)
		{
			if(user[i][0].equals(customerId))       //Checks customer id correct or not
			{
				System.out.print("Please enter your pin code: ");    
		        String customerPin=sc.nextLine();
				if(user[i][1].equals(customerPin))       //Checks customer pin correct or not
				{
					customerAccess(i);
					j++;
				}
				else
				{
					System.out.println("Incorrect pin!\n");
					j++;
					break;
				}
		   }
	    }
		if(j==0)
		{
			System.out.println("Incorrect Customer Id!\n");
		}
     }
	 
	 
	 void customerAccess(int x)
	 {
		 boolean customer=true;
		 while(customer)
		{
			System.out.println("\n\n\t\t~Customer Portal~\t\t\t\n");         //Customer portal
			System.out.println(
			"1. Deposit\n"+
			"2. Withdrawal\n"+
			"3. Check balance\n"+
			"4. Change pin code\n"+
			"0. Logout");
			System.out.print("\nPlease select an option: ");
			String adminOption=sc.nextLine();
			switch(adminOption)
			{
				case "0": {customer=false; break;}
				case "1": {deposit(x); break;}
				case "2": {withdrawal(x); break;}
				case "3": {checkbalance(x); break;}
				case "4": {changePin(x); break;}
				default: System.out.println("\nInvalid choice. Please try again.\n");
			}
		}
	 }
	 
	 void deposit(int i)          //To deposit money in the logged in costumer account
	 {
		 System.out.print("\nEnter the amount to be deposited: ");
		 String amountAdd=sc.nextLine();
		 System.out.println("\nCurrent balance:"+user[i][4]);
		 double newAmount=Double.parseDouble(user[i][4])+Double.parseDouble(amountAdd); //Converting string to double and adding
		 user[i][4]=Double.toString(newAmount); 
		 System.out.println("New balance: "+user[i][4]);
	 }
	 
	 
	 void withdrawal(int i)       //To withdraw money in the logged in costumer account
	 {
		 System.out.print("\nEnter the amount to be withdrawn: ");
		 String amountRemove=sc.nextLine();
		 double newAmount=Double.parseDouble(user[i][4])-Double.parseDouble(amountRemove);  //Converting string to double and subtracting
		 if(newAmount<0)
		 {
			 System.out.println("Insufficent balance!");
		 }
		 else
		 {
			 System.out.println("\nCurrent balance: "+user[i][4]);
			 user[i][4]=Double.toString(newAmount); 
		     System.out.println("New balance: "+user[i][4]);
		 }
	 }
	 
	 
	 void checkbalance(int i)    //Shows customer details
	 {
		 System.out.println("\nCustomer Id: "+user[i][0]);
		 System.out.println("Name: "+user[i][2]+" "+user[i][3]);
		 System.out.println("Balance avaliable: "+user[i][4]);
		
	 }
	 
	 
	 void changePin(int i)   //Allows to change pin no of customer logged in
	{
		System.out.print("\nEnter new four digit pin:");
		String newPassword=sc.nextLine();
		user[i][1]=newPassword;
		System.out.println("Pin changed successfully!");
	}
	 
	
	void adminLogin()      //Admin login
	{
		int trials=0;
		for(int i=3;i>0;i--)
		{
			if(trials>0){break;}
			System.out.print("\nEnter admin password: ");
		    String adminEnter=sc.nextLine();
			if(!adminEnter.equals(defaultPassword))    //Checks if entered password is same or not
			{
				if(i==1)
				{
					System.out.println("System Locked!\n");
			        break;
				}
				System.out.println("Incorrect Password. No. of attempts left is "+(i-1));
			}
			else
			{
				trials++;
				adminAccess();
			}
		}
	}
	
	
	void adminAccess()              //Admin panel
	{
		 boolean admin=true;
		 while(admin)
		{
			System.out.println("\n\t\t~Admin Portal~\t\t\t\n");
			System.out.println(
			"1. Search customer\n"+
			"2. Add costumer\n"+
			"3. Reset password\n"+
			"0. Logout");
			System.out.print("\nPlease select an option: ");
			String adminOption=sc.nextLine();
			switch(adminOption)
			{
				case "0": {admin=false; break;}
				case "1": {searchCustomer(); break;}
				case "2": {addCustomer(); break;}
				case "3": {resetPassword(); break;}
				default: System.out.println("\nInvalid choice. Please try again.\n");
			}
		}
		System.out.println("Logout Successful!\n");
	}
	
	
	void searchCustomer()        //Admin can search customers and get their details
	{
		System.out.print("\nEnter customer's first or last name:");
		String customerName=sc.nextLine();
		int j=0;
		for(int i=0;i<user.length;i++)
		{
			if(user[i][2].equalsIgnoreCase(customerName)||user[i][3].equalsIgnoreCase(customerName))
			{
				checkbalance(i);
				j++;
			}
		}
		if(j==0)
		{
			System.out.println("\nNo customer with this name!");
		}
	}
	
	
	void addCustomer()      //Admin can add new customers
	{
		user[addUser]=new String[5];
		Random random=new Random();
        user[addUser][0]= Integer.toString(random.nextInt(9000) + 1000);     //To get random four digit number
        user[addUser][1]=costumerDefault;
		System.out.print("\nEnter first name:");
		user[addUser][2]=sc.nextLine();
		System.out.print("Enter last name:");
		user[addUser][3]=sc.nextLine();
		System.out.println("Is the customer depositing?");
		System.out.print("Press y(yes) or n(no):");
		String input=sc.nextLine();
		String yes="y";
		if(input.equalsIgnoreCase(yes))               //Asking if the customer is depositing or not
		{
			System.out.print("Enter amount to deposit:");
		    user[addUser][4]=sc.nextLine();
		}
		else
		{
			user[addUser][4]="0";
		}
        System.out.println("\nCustomer added successfully!");
        checkbalance(addUser);         //Shows added customer details
        addUser++;		
	}
	
	
	void resetPassword()            //Admin a reset its portal password
	{
		System.out.print("\nEnter new four digit password:");
		String newPassword=sc.nextLine();
		defaultPassword=newPassword;
		System.out.println("Password successfully reset!");
	}
	
}
	
class atm
{	
	public static void main(String [] args)
	{
		Scanner sc=new Scanner(System.in);
		AllData ad=new AllData();
		System.out.println("\n------------------------------------------------------");
        System.out.println("--------------------ATM STIMULATOR--------------------\n");
        boolean main=true;	
        while(main)
		{
			System.out.println("\n\tMain Menu\t\t\n");
			System.out.println(
			"1. Costumer Services\n"+
			"2. Admin\n"+
			"0. Exit");
			System.out.print("\nPlease select an option: ");
			String choice=sc.nextLine();
			switch(choice)
			{
				case "0": {main=false; break;}
				case "1": {ad.customerLogin(); break;}
				case "2": {ad.adminLogin(); break;}
				default: System.out.println("\nInvalid choice. Please try again.\n");
			}
		}	
        System.out.println("\nThank you for using the ATM.");		
	}
}