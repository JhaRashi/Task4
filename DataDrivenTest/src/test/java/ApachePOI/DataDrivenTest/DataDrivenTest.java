package ApachePOI.DataDrivenTest;

	import java.io.IOException;
	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.testng.Assert;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;


	@SuppressWarnings("unused")
	public class DataDrivenTest {

		WebDriver driver;
		
		@BeforeClass
		public void setup()
		{
			System.setProperty("webdriver.chrome.driver","D:\\\\Applications\\\\chromedriver-win64\\\\chromedriver.exe");
			driver=new ChromeDriver();
			driver.get("https://www.saucedemo.com/");
			driver.manage().window().maximize();
			//driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		}
		
		@Test(dataProvider="loginData")
		public void loginTest(String user,String pwd,String exp)
		{
			driver.get("https://www.saucedemo.com/");
			
			WebElement txtEmail=driver.findElement(By.id("user-name"));
			txtEmail.clear();
			txtEmail.sendKeys(user);
			
			
			WebElement txtPassword=driver.findElement(By.id("password"));
			txtPassword.clear();
			txtPassword.sendKeys(pwd);
			
			driver.findElement(By.id("login-button")).click(); //Login  button
			
			String exp_title="Dashboard / nopCommerce administration";
			String act_title=driver.getTitle();
			
			if(exp.equals("Valid"))
			{
				if(exp_title.equals(act_title))
				{
					driver.findElement(By.linkText("Logout")).click();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			else if(exp.equals("Invalid"))
			{
				if(exp_title.equals(act_title))
				{
					driver.findElement(By.linkText("Logout")).click();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
			
		}
		
		@DataProvider(name="loginData")
		public String[][] getData() throws IOException
		{
			/*String loginData[][]= {
									{"admin@yourstore.com","admin","Valid"},
									{"admin@yourstore.com","adm","Invalid"},
									{"adm@yourstore.com","admin","Invalid"},
									{"adm@yourstore.com","adm","Invalid"}
								};*/
			
			//get the data from excel
			String path="/DataDrivenTest/DataFile/loginData.xlsx";
			XLUtility xlutil=new XLUtility(path);
			
			int totalrows=xlutil.getRowCount("Sheet1");
			int totalcols=xlutil.getCellCount("Sheet1",1);	
					
			String loginData[][]=new String[totalrows][totalcols];
				
			
			for(int i=1;i<=totalrows;i++) //1
			{
				for(int j=0;j<totalcols;j++) //0
				{
					loginData[i-1][j]=xlutil.getCellData("Sheet1", i, j);
				}
					
			}
			
			return loginData;
		}
	}
		
		//@AfterClass
		//void tearDown()
		//{
			//driver.close();
		//}	
	//}

