package suites;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlClass;
import java.util.ArrayList;
import java.util.List;

/**
 * TestNGRunner - Programmatic TestNG execution
 */
public class TestNGRunner {
    
    public static void main(String[] args) {
        // Create TestNG instance
        TestNG testng = new TestNG();
        
        // Create Suite
        XmlSuite suite = new XmlSuite();
        suite.setName("Polymer Shop Automation Suite");
        suite.setVerbose(1);
        
        // Create Test
        XmlTest test = new XmlTest(suite);
        test.setName("All Tests");
        
        // Add test classes
        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass("test.HomePageTests"));
        classes.add(new XmlClass("test.ProductTests"));
        classes.add(new XmlClass("test.CartTests"));
        classes.add(new XmlClass("test.CheckoutTests"));
        
        test.setXmlClasses(classes);
        
        // Add test to suite
        List<XmlTest> tests = new ArrayList<>();
        tests.add(test);
        suite.setTests(tests);
        
        // Add suite to TestNG
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testng.setXmlSuites(suites);
        
        // Run tests
        testng.run();
    }
}