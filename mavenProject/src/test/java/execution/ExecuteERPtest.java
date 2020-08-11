package execution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import excelUtility.ReadExcelSheet;
import utility.commonData;

public class ExecuteERPtest {
	WebDriver driver;
	
  @BeforeClass
  public void setUp() {
	  	System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
	  	driver = new ChromeDriver();
		driver.get(commonData.URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }
	
  @Test
  public void LoginTest() throws IOException, InterruptedException {
	  	ReadExcelSheet rs = new  ReadExcelSheet();
		ArrayList username = rs.readExcelData(0);
		ArrayList password = rs.readExcelData(1);
		
		for(int i=0; i<username.size(); i++) {
			WebElement uname = driver.findElement(By.id("loginform-username"));
			uname.click();	
			String str = (String) username.get(i);
			uname.sendKeys(str);
			WebElement pword = driver.findElement(By.id("loginform-password"));
			String str1 = (String) password.get(i);
			pword.sendKeys(str1);
			WebElement signInBtn = driver.findElement(By.name("login-button"));
			signInBtn.click();
		}
		System.out.println("Login Test successful");
  }
  
  @Test(dependsOnMethods = "LoginTest", groups = "CompanyProfile")
  public void ClickOnCompanyProfile() {
	  WebElement company = driver.findElement(By.xpath("//a[@title ='Company']"));
	  company.click();
	  WebElement CompanyProfile = driver.findElement(By.xpath("//a[contains(text(),'Company Profile')]"));
	  CompanyProfile.click();
  }
  
 @Test(dependsOnMethods = "ClickOnCompanyProfile", groups = "CompanyProfile")
 public void setCompanyProfile() throws InterruptedException {
	 Thread.sleep(3000);
	  WebElement companyName = driver.findElement(By.xpath("//*[@id=\"company-company_name\"]"));
	  companyName.clear();
	  companyName.sendKeys("xyzLabs");
	  WebElement email = driver.findElement(By.id("company-company_email"));
	  email.clear();
	  email.sendKeys("xyztest@gmail.com");
	  WebElement address = driver.findElement(By.id("company-company_address"));
	  address.clear();
	  address.sendKeys("xyzLabs", Keys.ENTER, "Trivandrum", Keys.ENTER, "Kerala");
	  WebElement cleardata = driver.findElement(By.xpath("//*[@id=\"company-started_at-kvdate\"]/span[@title='Clear field']"));
	  cleardata.click();
	  WebElement date = driver.findElement(By.id("company-started_at"));
	  date.click();
	  date.sendKeys("07-08-2020");
	  driver.findElement(By.id("company-status")).click();
	  driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
  }
 
 @Test(dependsOnGroups = "CompanyProfile", groups = "settings")
 public void clickOnSettings() throws InterruptedException  {
	 Thread.sleep(2000);
	 driver.findElement(By.xpath("//a[@title ='Settings']")).click();
	 
	  }
 @Test(dependsOnMethods = "clickOnSettings", groups = "settings" )
 public void createUser() throws InterruptedException  {
	 Thread.sleep(2000);
	 driver.findElement(By.xpath("//*[@id=\"signupform-name\"]")).sendKeys("Divya");
	 Thread.sleep(2000);
	 WebElement company = driver.findElement(By.id("signupform-company"));
	 Select dropdown = new Select(company);
	 dropdown.selectByVisibleText("xyzLabs");
	 WebElement branch = driver.findElement(By.xpath("//*[@id='signupform-branch']/div[43]/label"));
	 branch.click();
	 driver.findElement(By.id("signupform-email")).sendKeys("abc@gmail.com");
	 driver.findElement(By.id("signupform-phone")).sendKeys("12345678");
	 driver.findElement(By.id("signupform-username")).sendKeys("12345678");
	 driver.findElement(By.id("signupform-password")).sendKeys("1234abcd");
	 driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
 }

 @Test(dependsOnMethods = "createUser", groups = "settings" )
 public void assignAdminrole() {
	 
	 try {
		 WebElement assignRole = driver.findElement(By.xpath("//*[@id='w0']/table/tbody/tr[17]/td[5]/a[4]"));
		 assignRole.click();
		}
		catch(org.openqa.selenium.StaleElementReferenceException ex)
		{
			WebElement assignRole = driver.findElement(By.xpath("//*[@id='w0']/table/tbody/tr[17]/td[5]/a[4]"));
			assignRole.click();
		}
	 driver.findElement(By.xpath("//*[@id='w0']/table[1]/descendant::td/input[@value='admin']")).click();
	 driver.findElement(By.xpath("//button[contains(text(),'Add')]")).click();
 }

 @AfterClass
 public void closeBrowser() {
	 driver.close();
 }
}
