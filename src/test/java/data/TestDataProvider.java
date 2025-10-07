package data;

import utils.ExcelReader;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
    
    private static final String TEST_DATA_PATH = "src/main/resources/testdata.xlsx";
    
    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        ExcelReader reader = new ExcelReader(TEST_DATA_PATH);
        Object[][] data = reader.getTestData("CheckoutData");
        reader.close();
        return data;
    }
    
    @DataProvider(name = "productData")
    public Object[][] getProductData() {
        ExcelReader reader = new ExcelReader(TEST_DATA_PATH);
        Object[][] data = reader.getTestData("ProductData");
        reader.close();
        return data;
    }
}