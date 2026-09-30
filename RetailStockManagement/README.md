# Retail Stock Management — Setup & Run Guide

Folder structure:
```
RetailStockManagement/
├── Task1_JavaProgram/
│   └── RetailStockManager.java      (Task 1 - standalone console app)
├── Task2_MySQL/
│   └── schema_and_queries.sql       (Task 2 - DB schema + CRUD queries)
├── Task3_Task4_WebApp/              (Task 3 HTML/Servlet + Task 4 JDBC, combined)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/retail/
│       │   ├── model/Product.java
│       │   ├── dao/DBConnection.java
│       │   ├── dao/ProductDAO.java       (JDBC - Task 4)
│       │   └── servlet/
│       │       ├── AddProductServlet.java
│       │       ├── SearchProductServlet.java
│       │       └── PurchaseServlet.java
│       └── webapp/
│           ├── addProduct.html
│           ├── searchProduct.html
│           └── purchase.html
└── README.md
```

Note on why Task 3 and Task 4 share one project: Task 3 asks for HTML forms + Servlets to
process product/purchase data, and Task 4 asks for a Servlet+JDBC app doing CRUD against the
same database. In practice these run as a single deployed web app — the Servlets in Task 3
are the ones that call the JDBC DAO from Task 4. If your batch needs them as two visibly
separate submissions, just say so and I'll split the Servlets into two copies.

---

## Task 1 — Java program (no setup needed beyond a JDK)

```bash
cd Task1_JavaProgram
javac RetailStockManager.java
java RetailStockManager
```
Follow the on-screen menu to add products, view them, and purchase.

---

## Task 2 — MySQL

1. Open MySQL Workbench or the `mysql` CLI.
2. Run the whole file:
   ```bash
   mysql -u root -p < Task2_MySQL/schema_and_queries.sql
   ```
   Or paste it into Workbench and execute. This creates `retail_db`, the `products` table,
   inserts 10+ sample rows, and includes ready CRUD queries you can point to in your report.

---

## Task 3 & 4 — Web app (HTML + Servlet + JDBC)

### Prerequisites
- JDK 17+
- Apache Tomcat 10.x (uses `jakarta.servlet.*` — matches the code here).
  *If your lab only has Tomcat 9, tell me — the code needs a small tweak to `javax.servlet.*`.*
- Maven
- MySQL Server running, with Task 2's `schema_and_queries.sql` already executed

### Step 1 — set your DB password
Open `src/main/java/com/retail/dao/DBConnection.java` and change:
```java
private static final String PASSWORD = "your_mysql_password";
```
to your actual MySQL root password.

### Step 2 — build the WAR
```bash
cd Task3_Task4_WebApp
mvn clean package
```
This produces `target/RetailStockManagement.war`.

### Step 3 — deploy to Tomcat
Copy the WAR file into Tomcat's `webapps` folder:
```bash
cp target/RetailStockManagement.war /path/to/tomcat/webapps/
```
Start Tomcat:
```bash
/path/to/tomcat/bin/startup.sh      # Linux/Mac
/path/to/tomcat/bin/startup.bat     # Windows
```

### Step 4 — use it
Open a browser:
- Add product: `http://localhost:8080/RetailStockManagement/addProduct.html`
- Search product: `http://localhost:8080/RetailStockManagement/searchProduct.html`
- Purchase product: `http://localhost:8080/RetailStockManagement/purchase.html`

Each form posts to its Servlet, which uses `ProductDAO` (JDBC) to talk to MySQL, then
prints the result (confirmation, search table, or bill) back as HTML.

---

## If you're using Eclipse/IntelliJ instead of the command line
1. Import the `Task3_Task4_WebApp` folder as a **Maven project**.
2. Add Tomcat as a server (Eclipse: Servers view → New → Tomcat 10).
3. Right-click the project → Run As → Run on Server.
4. Browse to the same URLs as above.

---

## Quick checklist to hand in
- [ ] Task 1: `RetailStockManager.java` compiles and runs, shows Out of Stock/Low Stock, rejects over-purchase
- [ ] Task 2: `products` table created, 10+ rows inserted, add/search/update/delete/low-stock queries shown working
- [ ] Task 3: All 3 HTML forms load and submit correctly, Servlets return a bill/results page
- [ ] Task 4: Servlets persist changes to MySQL via JDBC (add a product, refresh DB, confirm it's there)
