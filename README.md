# Online Polling System
### Enterprise-Level Java Console Application

---

## Table of Contents
1. [Project Overview](#overview)
2. [Tech Stack](#tech-stack)
3. [Project Structure](#project-structure)
4. [Database Setup](#database-setup)
5. [Eclipse IDE Setup](#eclipse-ide-setup)
6. [JDBC Configuration](#jdbc-configuration)
7. [How to Run](#how-to-run)
8. [Application Features](#application-features)
9. [Sample Credentials](#sample-credentials)
10. [Troubleshooting](#troubleshooting)

---

## Project Overview

The **Online Polling System** is a menu-driven Java console application built using enterprise-level
design patterns (DAO, Layered Architecture). It enables voter registration, nominee management, vote
casting with duplicate-vote prevention, and result reporting.

---

## Tech Stack

| Layer        | Technology                         |
|--------------|------------------------------------|
| Language     | Java 8+                            |
| IDE          | Eclipse IDE for Java Developers    |
| Database     | MySQL 5.7+ (via XAMPP)             |
| Connectivity | JDBC (mysql-connector-j)           |
| Pattern      | DAO Pattern + Layered Architecture |

---

## Project Structure

```
OnlinePollingSystem/
├── src/
│   ├── com/client/
│   │   └── UserInterface.java          ← Main entry point & console UI
│   ├── com/model/
│   │   ├── Voter.java
│   │   ├── Nominee.java
│   │   └── Vote.java
│   ├── com/service/
│   │   ├── VoterService.java
│   │   ├── NomineeService.java
│   │   └── VoteService.java
│   ├── com/management/
│   │   ├── DBConnectionManager.java
│   │   ├── VoterManagement.java
│   │   ├── NomineeManagement.java
│   │   └── VoteManagement.java
│   ├── com/exception/
│   │   └── InvalidVoterException.java
│   └── com/util/
│       └── ApplicationUtil.java
├── lib/
│   └── mysql-connector-j-8.x.x.jar    ← Download separately (see below)
├── database/
│   └── online_polling_system.sql       ← Run this first in phpMyAdmin/MySQL CLI
└── README.md
```

---

## Database Setup

### Step 1 – Start XAMPP
1. Open **XAMPP Control Panel**
2. Start **Apache** and **MySQL** services

### Step 2 – Run the SQL Script

**Option A – phpMyAdmin:**
1. Open browser → `http://localhost/phpmyadmin`
2. Click **SQL** tab
3. Paste the contents of `database/online_polling_system.sql`
4. Click **Go**

**Option B – MySQL Command Line:**
```bash
mysql -u root -p < database/online_polling_system.sql
```

### Step 3 – Verify
```sql
USE online_polling_system;
SHOW TABLES;
-- Expected: voter, nominee, vote
SELECT * FROM voter;
SELECT * FROM nominee;
```

---

## Eclipse IDE Setup

### Step 1 – Import Project
1. Open Eclipse
2. **File → Import → General → Existing Projects into Workspace**
3. Browse to the `OnlinePollingSystem` folder
4. Click **Finish**

### Step 2 – Download MySQL Connector JAR
Download from the official source:
```
https://dev.mysql.com/downloads/connector/j/
```
Choose: **Platform Independent → ZIP Archive**
Extract and find: `mysql-connector-j-8.x.x.jar`

### Step 3 – Add JAR to Build Path
1. Right-click project → **Build Path → Configure Build Path**
2. Click **Libraries** tab → **Add External JARs**
3. Navigate to and select `mysql-connector-j-8.x.x.jar`
4. Click **Apply and Close**

---

## JDBC Configuration

The connection is configured in:
```
src/com/management/DBConnectionManager.java
```

Default settings (for XAMPP):
```java
private static final String DB_URL      = "jdbc:mysql://localhost:3306/online_polling_system?useSSL=false&serverTimezone=UTC";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = "";   // Empty for default XAMPP
```

**If your MySQL has a password**, update `DB_PASSWORD` accordingly.

---

## How to Run

### In Eclipse
1. Open `src/com/client/UserInterface.java`
2. Right-click in the editor → **Run As → Java Application**
3. The console will open with the main menu

### From Command Line
```bash
# Compile (from project root)
javac -cp "lib/mysql-connector-j-8.x.x.jar" -d bin src/com/model/*.java src/com/exception/*.java src/com/util/*.java src/com/management/*.java src/com/service/*.java src/com/client/*.java

# Run
java -cp "bin;lib/mysql-connector-j-8.x.x.jar" com.client.UserInterface
# On Linux/Mac use : instead of ;
java -cp "bin:lib/mysql-connector-j-8.x.x.jar" com.client.UserInterface
```

---

## Application Features

### Main Menu
```
============================================================
           ONLINE POLLING SYSTEM - MAIN MENU
============================================================
  1. Voter Module
  2. Nominee Module
  3. Vote Module
  4. Result Module
  5. Exit
============================================================
```

### Voter Module
| Feature             | Description                              |
|---------------------|------------------------------------------|
| Add Voter           | Registers a new voter (age ≥ 18 check)  |
| Update Mobile       | Updates a voter's mobile number          |
| Update Address      | Updates a voter's residential address    |
| Search By ID        | Looks up a voter by their Voter ID       |
| View By District    | Lists all voters in a district           |
| Delete Voter        | Removes a voter with confirmation        |

### Nominee Module
| Feature             | Description                              |
|---------------------|------------------------------------------|
| Add Nominee         | Registers a new nominee                  |
| Update Address      | Updates nominee's address                |
| Search By ID        | Looks up a nominee by ID                 |
| View By District    | Lists nominees in a district             |
| Delete Nominee      | Removes a nominee with confirmation      |

### Vote Module
| Feature             | Description                              |
|---------------------|------------------------------------------|
| Login & Cast Vote   | Voter authenticates, selects a nominee, casts vote. Duplicate voting is prevented. |

### Result Module
| Feature                    | Description                                      |
|----------------------------|--------------------------------------------------|
| District Voting Percentage | % of voters who voted per district               |
| Party Wise Vote Count      | Total votes per party symbol                     |
| District Wise Nominee List | All nominees grouped by district                 |
| Winning Constitution       | Nominee with highest votes per constituency      |

---

## Sample Credentials

The SQL script inserts these sample voters for testing:

| Login ID     | Password   | District    |
|--------------|------------|-------------|
| raj_anand    | raj@123    | Chennai     |
| meena_l      | meena@456  | Madurai     |
| kiran_b      | kiran@789  | Coimbatore  |
| divya_r      | divya@321  | Salem       |
| arun_s       | arun@654   | Chennai     |

Sample nominee IDs: 1001 – 1008

---

## Troubleshooting

| Problem                              | Solution                                                              |
|--------------------------------------|-----------------------------------------------------------------------|
| `ClassNotFoundException` on Driver   | mysql-connector-j JAR not added to build path                        |
| `Communications link failure`        | XAMPP MySQL is not running – start it from XAMPP Control Panel       |
| `Unknown database`                   | Run the SQL script first to create `online_polling_system` database  |
| `Access denied for user 'root'`      | Update DB_PASSWORD in DBConnectionManager.java                       |
| `Duplicate entry` on voter_id        | Auto-generation collision – restart app; voter count recalculates    |
| Date format error                    | Use format dd-MM-yyyy (e.g., 15-08-1990)                             |

---

## Architecture Overview

```
[UserInterface.java]           ← Console I/O, menu loops
        ↓
[Service Layer]                ← Business rules, validation
  VoterService
  NomineeService
  VoteService
        ↓
[Management/DAO Layer]         ← Pure SQL via PreparedStatement
  VoterManagement
  NomineeManagement
  VoteManagement
        ↓
[DBConnectionManager]          ← Singleton JDBC connection
        ↓
[MySQL Database]               ← voter, nominee, vote tables
```

---

*Built with enterprise-level Java standards: DAO pattern, Layered Architecture, PreparedStatement,
custom exceptions, and modular service design.*
