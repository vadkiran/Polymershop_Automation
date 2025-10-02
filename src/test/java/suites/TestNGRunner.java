package suites;


import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

public class TestNGRunner {

    public static void main(String[] args) {
        TestNG testng = new TestNG();

        // Create a new XML suite programmatically
        XmlSuite suite = new XmlSuite();
        suite.setName("Polymer Shop Test Suite");
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(2);

        // Create a test for Chrome
        XmlTest chromeTest = new XmlTest(suite);
        chromeTest.setName("Chrome Tests");
        chromeTest.setClasses(createClassList("com.polymershop.tests.HomePageTests", 
        		"com.polymershop.tests.ProductTests", "com.polymershop.tests.CartTests", "com.polymershop.tests.CheckoutTests"));
        
        // Create a test for Firefox
        XmlTest firefoxTest = new XmlTest(suite);
        firefoxTest.setName("Firefox Tests");
        firefoxTest.setClasses(createClassList("com.polymershop.tests.HomePageTests", "com.polymershop.tests.ProductTests", "com.polymershop.tests.CartTests"));
        
        // Set the listeners
        suite.addListener("com.polymershop.listeners.TestListener");

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        testng.setXmlSuites(suites);
        testng.run();
    }
    
    private static List<org.testng.xml.XmlClass> createClassList(String... classNames) {
        List<org.testng.xml.XmlClass> classes = new ArrayList<>();
        for (String className : classNames) {
            classes.add(new org.testng.xml.XmlClass(className));
        }
        return classes;
    }
}
