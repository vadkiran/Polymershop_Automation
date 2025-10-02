package data;


import utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "productData")
    public static Object[][] getProductData() {
        String filePath = "resources/testdata.xlsx";
        String sheetName = "Products";
        return ExcelReader.getTestData(filePath, sheetName);
    }
    
    @DataProvider(name = "checkoutData")
    public static Object[][] getCheckoutData() {
        String filePath = "resources/testdata.xlsx";
        String sheetName = "Checkout";
        return ExcelReader.getTestData(filePath, sheetName);
    }
}
