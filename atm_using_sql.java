import java.util.*;
import java.sql.*;


class connect
{
    Connection getcon() throws Exception
    {
        String driverName="com.mysql.cj.jdbc.Driver";
        Class.forName(driverName);

        String dburl="jdbc:mysql://localhost:3306/atm";
        String dbuser="root";
        String dbpass="";
        Connection con=DriverManager.getConnection(dburl,dbuser,dbpass);
        return con;
    }
}

public class atm {
 
    Scanner sc=new Scanner(System.in);
    String defaultPassword="2610";


    void customerLogin() throws Exception
    {
        Connection con=new connect().getcon();
        String sql="Select * from customer_details where customer_id=?";
        PreparedStatement pst=con.prepareStatement(sql);
        System.out.print("\nPlease enter your customer ID: ");
	    int customerId=sc.nextInt();
        pst.setInt(1,customerId);
        ResultSet rs=pst.executeQuery();
        int j=0;
        while(rs.next())
        {
            System.out.print("Please enter your pin code: ");
            int customerPin=sc.nextInt();
            if(rs.getInt("pin_code")==customerPin)
            {
                j++;
                customerAccess(customerId);
            }
            else
            {
                j++;
                System.out.println("Incorrect Pincode!");
            }
        }
        if(j==0)
        {
            System.out.println("Incorrect Id!");
        }
    }


    void customerAccess(int customerId) throws Exception
    {
        boolean customer=true;
		 while(customer)
		{
			System.out.println("\n\n\t\t~Customer Portal~\t\t\t\n");        
			System.out.println(
                "1. Deposit\n"+
			    "2. Withdrawal\n"+
			    "3. Check balance\n"+
			    "4. Change pin code\n"+
			    "0. Logout");
            sc.nextLine();
			System.out.print("\nPlease select an option: ");
			String custOption=sc.nextLine();
			switch(custOption)
			{
				case "0": {customer=false; break;}
				case "1": { deposit(customerId); break;}
				case "2": { withdrawal(customerId);break;}
				case "3": { checkbalance(customerId); break;}
				case "4": { changePin(customerId); break;}
				default: System.out.println("\nInvalid choice. Please try again.\n");
			}
		}
    }

   void deposit(int customerId) throws Exception
	{
		System.out.print("\nEnter the amount to be deposited: ");
		int amountAdd=sc.nextInt();
        Connection con=new connect().getcon();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("Select amount from customer_details where customer_id="+customerId);
        int currentAmount=0;
        int newAmount;
        while(rs.next())
        {
            currentAmount=rs.getInt("amount");
        }
        
        newAmount=currentAmount+amountAdd;
        String sql="Update customer_details set amount=? where customer_id="+customerId;
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setInt(1,newAmount);
        pst.executeUpdate();

        System.out.println("\nCurrent Balance:"+currentAmount);
        System.out.println("New Balance:"+newAmount);
	}

    void withdrawal(int customerId) throws Exception
	{
		System.out.print("\nEnter the amount to be withdrawn: ");
		int amountRemove=sc.nextInt();
        Connection con=new connect().getcon();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("Select amount from customer_details where customer_id="+customerId);
        int currentAmount=0;
        int newAmount;
        while(rs.next())
        {
            currentAmount=rs.getInt("amount");
        }
        
        newAmount=currentAmount-amountRemove;
        if(newAmount<0)
        {
            System.out.println("\nCurrent balance: "+currentAmount);
            System.out.println("The amount entered is insufficient!");
        }
        else
        {   
            String sql="Update customer_details set amount=? where customer_id="+customerId;
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1,newAmount);
            pst.executeUpdate();

            System.out.println("\nCurrent Balance:"+currentAmount);
            System.out.println("New Balance:"+newAmount);
        }
	}

    void checkbalance(int customerId) throws Exception   
	{
		Connection con=new connect().getcon();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("Select * from customer_details where customer_id="+customerId);
        while(rs.next())
        {
            System.out.println("\nCustomer Id: "+rs.getInt("customer_id"));
		    System.out.println("Name: "+rs.getString("first_name")+" "+rs.getString("last_name"));
		    System.out.println("Balance avaliable: "+rs.getInt("amount"));
        }
	}

    void changePin(int customerId) throws Exception 
	{
        Connection con=new connect().getcon();
		System.out.print("\nEnter new four digit pin: ");
		int newPassword=sc.nextInt();
		String sql="Update customer_details set pin_code=? where customer_id="+customerId;
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setInt(1,newPassword);
        int i=pst.executeUpdate();
        if(i>0)
        {
            System.out.println("Pin changed successfully!");
        }
		else
        {
            System.out.println("Error :0");
        }
	}

    void adminLogin() throws Exception     
	{
		int trials=0;
		for(int i=3;i>0;i--)
		{
			if(trials>0){break;}
			System.out.print("\nEnter admin password: ");
		    String adminEnter=sc.nextLine();
			if(!adminEnter.equals(defaultPassword))    
			{
				if(i==1)
				{
					System.out.println("System Locked!\n");
			        break;
				}
				System.out.println("Incorrect Password. Number of attempts left is "+(i-1));
			}
			else
			{
				trials++;
				adminAccess();
			}
		}
    }

    void adminAccess() throws Exception              
	{
		 boolean admin=true;
		 while(admin)
		{
			System.out.println("\n\t\t~Admin Portal~\t\t\t\n");
			System.out.println(
			"1. Search customer\n"+
			"2. Add customer\n"+
			"3. Delete customer\n"+
			"4. Reset password\n"+
			"0. Logout");
			System.out.print("\nPlease select an option: ");
			String adminOption=sc.nextLine();
			switch(adminOption)
			{
				case "0": { admin=false; break;}
				case "1": { searchCustomer(); break;}
				case "2": { addCustomer(); break;}
				case "3": { deleteCustomer(); break;}
				case "4": { resetPassword(); break;}
				default: System.out.println("\nInvalid choice. Please try again.\n");
			}
		}
		System.out.println("Logout Successful!\n");
	}

    void searchCustomer() throws Exception
	{
        Connection con=new connect().getcon();
		System.out.print("\nEnter customer's first or last name:");
		String customerName=sc.nextLine();
        String sql="Select * from customer_details where first_name=? or last_name=?";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setString(1,customerName);
        pst.setString(2,customerName);
        ResultSet rs=pst.executeQuery();
        int j=0;
        while(rs.next())
        {
            j++;
            System.out.println("\nCustomer Id: "+rs.getInt("customer_id"));
		    System.out.println("Name: "+rs.getString("first_name")+" "+rs.getString("last_name"));
		    System.out.println("Balance avaliable: "+rs.getInt("amount"));
        }
        if(j==0)
        {
            System.out.println("No customer with this name!");
        }
	}

    void addCustomer() throws Exception
	{
        Connection con=new connect().getcon();
		Random random = new Random();
        int customerId = random.nextInt(90000)+10000;
        int defaultPin=1111;
		System.out.print("\nEnter first name:");
		String firstName=sc.nextLine();
		System.out.print("Enter last name:");
		String lastName=sc.nextLine();
		System.out.println("Is the customer depositing?");
		System.out.print("Press y(yes) or n(no):");
		String input=sc.nextLine();
        int amount=0;
		if(input.equalsIgnoreCase("y"))           
		{
			System.out.print("Enter amount to deposit:");
		    amount=sc.nextInt();
		}

        String sql="Insert into customer_details values (?,?,?,?,?)";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setInt(1,customerId);
        pst.setInt(2,defaultPin);
        pst.setString(3,firstName);
        pst.setString(4,lastName);
        pst.setInt(5,amount);    
        int i=pst.executeUpdate();  
        if(i>0)
        {
            System.out.println("\nCustomer added successfully!");        
        }
        else
        {
            System.out.println("\nAddition failed!");        
        }	
	}

    void deleteCustomer() throws Exception
	{
        Connection con=new connect().getcon();
		System.out.print("\nEnter customer's first or last name:");
		String customerName=sc.nextLine();
        String sql="Select * from customer_details where first_name=? or last_name=?";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setString(1,customerName);
        pst.setString(2,customerName);
        ResultSet rs=pst.executeQuery();
        int j=0;
        int customerId=0;
        while(rs.next())
        {
            j++;
            customerId=rs.getInt("customer_id");
            System.out.println("\nCustomer Id: "+rs.getInt("customer_id"));
		    System.out.println("Name: "+rs.getString("first_name")+" "+rs.getString("last_name"));
		    System.out.println("Balance avaliable: "+rs.getInt("amount"));
        }
        if(j==0)
        {
            System.out.println("No customer with this name!");
            return;
        }

        Statement st=con.createStatement();
        int i=st.executeUpdate("Delete from customer_details where customer_id="+customerId);
        if(i>0)
        {
            System.out.println("Deleted successfully!");
        }
        else
        {
            System.out.println("Deletion failed!");
        }
	}

    void resetPassword()        
	{
		System.out.print("\nEnter new four digit password: ");
		defaultPassword=sc.nextLine();
		System.out.println("Password successfully reset!");
	}
	
    public static void main(String [] args) throws Exception
    {
        Scanner sc=new Scanner(System.in);
        Connection con=new connect().getcon();
        atm a=new atm();

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
				case "0": 
                {
                    main=false;
                    System.out.println("\nThank you for using the ATM!\n");
                    break;
                }
				case "1": 
                {
                    a.customerLogin();
                    break;
                }
				case "2":
                {
                    a.adminLogin();
                    break;
                }
				default: System.out.println("\nInvalid choice. Please try again.\n");
			}
        }
    }
}
