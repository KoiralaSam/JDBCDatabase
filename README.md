# JDBC Database Employee Search Application

A Java Swing application for searching employees from a MySQL database based on department and project criteria.

## Prerequisites

- Java JDK 8 or higher
- MySQL Server installed and running
- MySQL JDBC Driver (included: `mysql-connector-j-9.5.0.jar`)

## Project Structure

```
JDBCDatabase/
├── DB.java                  # Database connection handler
├── InitDB.java              # Database initialization script
├── FillDatabase.java        # Database query methods
├── EmployeeSearchFrame.java # Main GUI application
├── SetProperties.java       # Configuration file generator
├── DatabaseReturn.java      # Return type wrapper class
├── config.properties        # Database configuration file
└── mysql-connector-j-9.5.0.jar  # MySQL JDBC driver
```

## Setup Instructions

### Step 1: Configure Database Connection

1. **Option A: Use SetProperties.java (Recommended)**
   ```bash
   javac SetProperties.java
   java SetProperties
   ```
   This will create/update `config.properties` with your database settings.

2. **Option B: Edit config.properties manually**
   Edit `config.properties` and set:
   ```
   DATABASE_DRIVER=com.mysql.cj.jdbc.Driver
   DATABASE_URL=jdbc:mysql://127.0.0.1:3306/
   DATABASE_USERNAME=your_username
   DATABASE_PASSWORD=your_password
   ```

### Step 2: Initialize the Database

**IMPORTANT:** Run this first to create and populate the database with sample data.

```bash
javac -cp .:./mysql-connector-j-9.5.0.jar InitDB.java
java -cp .:./mysql-connector-j-9.5.0.jar InitDB
```

This will:
- Create the `COMPANY` database
- Create all required tables (EMPLOYEE, DEPARTMENT, PROJECT, etc.)
- Insert sample data (employees, departments, projects, and relationships)

**Expected Output:**
```
Successfully connected to mySql server using TCP/IP...
Database tables created and populated successfully!
```

### Step 3: Compile the Application

```bash
javac -cp .:./mysql-connector-j-9.5.0.jar *.java
```

### Step 4: Run the Application

```bash
java -cp .:./mysql-connector-j-9.5.0.jar EmployeeSearchFrame
```

## Using the Application

### Main Window Features

1. **Database Field**
   - Enter the database name (e.g., "COMPANY")
   - Click "Fill" to load departments and projects from the database

2. **Department List**
   - Select one or more departments
   - Check "Not" to exclude selected departments

3. **Project List**
   - Select one or more projects
   - Check "Not" to exclude selected projects

4. **Search Button**
   - Click to search for employees matching your criteria
   - Results appear in the Employee text area below

5. **Clear Button**
   - Clears the employee results
   - Clears all selections and checkboxes

### Search Examples

**Example 1: Find employees in "Research" department**
- Select "Research" in Department list
- Leave "Not" unchecked
- Don't select any projects
- Click "Search"

**Example 2: Find employees NOT in "Headquarters"**
- Select "Headquarters" in Department list
- Check the "Not" checkbox
- Click "Search"

**Example 3: Find employees working on "ProductX" project**
- Don't select any departments
- Select "ProductX" in Project list
- Click "Search"

**Example 4: Find employees in "Research" working on "ProductX"**
- Select "Research" in Department list
- Select "ProductX" in Project list
- Click "Search"

## Troubleshooting

### Connection Errors

**Error: "Failed to establish database connection"**
- Verify MySQL server is running: `mysql -u root -p`
- Check `config.properties` has correct credentials
- Ensure MySQL is accessible at the configured host/port

**Error: "Unknown database 'company'"**
- Run `InitDB.java` first to create the database
- Or enter the correct database name in the Database field

### Compilation Errors

**Error: "package does not exist" or "cannot find symbol"**
- Ensure you're using the classpath: `-cp .:./mysql-connector-j-9.5.0.jar`
- Verify all `.java` files are in the same directory

### Runtime Errors

**Error: "Table doesn't exist"**
- Run `InitDB.java` to create and populate tables
- Verify the database name matches what you entered

**Error: "Access denied"**
- Check MySQL username and password in `config.properties`
- Ensure the MySQL user has CREATE DATABASE and SELECT privileges

## Database Schema

The application uses the following tables:
- **EMPLOYEE**: Employee information (name, SSN, department, etc.)
- **DEPARTMENT**: Department information
- **PROJECT**: Project information
- **WORKS_ON**: Employee-project relationships
- **DEPT_LOCATIONS**: Department locations
- **DEPENDENT**: Employee dependents

## Notes

- The application automatically creates the database if it doesn't exist
- All search results return distinct employee names (no duplicates)
- Error messages are displayed in popup dialogs if database operations fail
- The Employee text area is scrollable for long result lists

## Author

Based on framework by Lon Smith, Ph.D.

