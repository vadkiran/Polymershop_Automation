Polymer Shop Automation Framework

This project contains a comprehensive end-to-end automation framework built using Selenium WebDriver, TestNG, and Maven to test the functional areas of the Polymer Shop web application. The framework incorporates advanced features like Page Object Model (POM), Shadow DOM handling, real-time logging (Log4j 2), and detailed reporting (ExtentReports 5.x).

🚀 Key Technologies
Language: Java 11+

Build Tool: Maven

Testing Framework: TestNG (v7.x)

Web Automation: Selenium WebDriver (v4.x)

Reporting: ExtentReports (v5.x) with custom listeners for screenshots.

Logging: Log4j 2 (v2.23.1)

Driver Management: WebDriverManager

📁 Project Structure
The project follows a standard Maven structure optimized for robust automation:

Directory

Purpose

src/main/java/managers

Handles the thread-safe WebDriver instance (DriverManager).

src/main/java/pages

Contains the Page Object Model (POM) classes (e.g., HomePage, BasePage).

src/main/java/utils

Utility classes like ConfigReader and WaitUtility.

src/test/java/listeners

Custom listeners (ExtentReportListener) for logging and reporting integration.

src/test/java/test

All TestNG test classes (e.g., CartTests, InvalidFeatureTests).

src/main/resources

Configuration files (Config.properties, log4j2.xml).

src/test/resources

TestNG XML suites (testng.xml).

output/test-output

Generated Extent Reports HTML files.

output/screenshots

Location for failure screenshots captured by the listener.

⚙️ Setup and Prerequisites
Java Development Kit (JDK): Ensure you have JDK 11 or newer installed.

Maven: Ensure Maven is installed and configured in your system path.

IDE: Use an IDE like IntelliJ or Eclipse.

Dependency Management
All project dependencies are managed via the pom.xml file.

Log4j 2 Version: Defined in <properties> as 2.23.1.

ExtentReports Version: Currently set to 5.1.1.

Run a Maven refresh/update after cloning the project to download all necessary libraries.

▶️ How to Run Tests
Tests are executed using the testng.xml file.

1. Run via Maven
Use the Maven Surefire Plugin configured in the pom.xml to run the suite from the command line:

mvn clean install
mvn test 

2. Run via TestNG XML
You can also run the testng.xml file directly from your IDE (Right-click testng.xml -> Run As -> TestNG Suite).

Test Suite Configuration
The primary configuration file is src/test/resources/testng.xml.

Base URL: Set by the <parameter name="baseUrl" value="...">.

Browser: Set by the <parameter name="browser" value="...">.

Listeners: Both listeners.TestListener and listeners.ExtentReportListener are active.

📊 Reporting and Logging
Extent Reports
A new report is generated in the output/test-output/ directory with a timestamp for every execution.

Failure steps automatically capture screenshots and embed them in the report.

Log4j 2 Logging
The src/main/resources/log4j2.xml file is configured to output logs to both the Console and a Rolling File.

Log files are stored in the designated log directory (e.g., logs/app.log).
Project Structure

polymershop/
│
├── src/
│   ├── main/
│   │   ├── java/com/polymershop/
│   │   │   ├── managers/
│   │   │   │   └── DriverManager.java              ✅ WebDriver management
│   │   │   ├── pages/
│   │   │   │   ├── BasePage.java                   ✅ Shadow DOM utilities
│   │   │   │   ├── HomePage.java                   ✅ Fixed cart badge
│   │   │   │   ├── ProductListingPage.java         ✅ Product grid handling
│   │   │   │   ├── ProductDetailPage.java          ✅ Detail page with size/qty
│   │   │   │   ├── CartPage.java                   ✅ Cart operations
│   │   │   │   └── CheckoutPage.java               ✅ Checkout flow
│   │   │   ├── utils/
│   │   │   │   ├── ConfigReader.java               ✅ Properties reader
│   │   │   │   └── ExcelReader.java                ✅ Test data from Excel
│   │   │   └── data/
│   │   │       └── TestDataProvider.java           ✅ TestNG DataProvider
│   │   │
│   │   └── resources/
│   │       ├── config.properties                   ✅ Configuration
│   │       └── testdata.xlsx                       ✅ Test data (2 sheets)
│   │
│   └── test/
│       ├── java/com/polymershop/
│       │   ├── tests/
│       │   │   ├── HomePageTests.java              ✅ 3 tests
│       │   │   ├── ProductTests.java               ✅ 9 tests
│       │   │   ├── CartTests.java                  ✅ 6 tests
│       │   │   ├── CheckoutTests.java              ✅ 5 tests
│       │   │   └── InvalidFeatureTests.java        🆕 Invalid feature documentation
│       │   ├── listeners/
│       │   │   └── TestListener.java               ✅ Screenshot on failure
│       │   └── suites/
│       │       └── TestNGRunner.java               ✅ Programmatic runner
│       │
│       └── resources/
│           └── testng.xml                          ✅ Test suite config
│
├── output/
│   ├── screenshots/                                 📸 Failure screenshots
│   ├── test-output/                                 📊 TestNG HTML reports
│   └── logs/                                        📝 Execution logs
│
├── pom.xml                                          ✅ Maven dependencies
├── testng.xml                                       ✅ Suite configuration
└── README.md                                        📖 This file