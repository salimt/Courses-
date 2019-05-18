{
 "cells": [
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "<a href=\"https://www.bigdatauniversity.com\"><img src = \"https://ibm.box.com/shared/static/ugcqz6ohbvff804xp84y4kqnvvk3bq1g.png\" width = 300, align = \"center\"></a>\n",
    "\n",
    "<h1 align=center><font size = 5>Lab: Access DB2 on Cloud using Python</font></h1>"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "# Introduction\n",
    "\n",
    "This notebook illustrates how to access your database instance using Python by following the steps below:\n",
    "1. Import the `ibm_db` Python library\n",
    "1. Identify and enter the database connection credentials\n",
    "1. Create the database connection\n",
    "1. Create a table\n",
    "1. Insert data into the table\n",
    "1. Query data from the table\n",
    "1. Retrieve the result set into a pandas dataframe\n",
    "1. Close the database connection\n",
    "\n",
    "\n",
    "__Notice:__ Please follow the instructions given in the first Lab of this course to Create a database service instance of Db2 on Cloud.\n",
    "\n",
    "## Task 1: Import the `ibm_db` Python library\n",
    "\n",
    "The `ibm_db` [API ](https://pypi.python.org/pypi/ibm_db/) provides a variety of useful Python functions for accessing and manipulating data in an IBM® data server database, including functions for connecting to a database, preparing and issuing SQL statements, fetching rows from result sets, calling stored procedures, committing and rolling back transactions, handling errors, and retrieving metadata.\n",
    "\n",
    "\n",
    "We import the ibm_db library into our Python Application\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": true
   },
   "outputs": [],
   "source": [
    "import ibm_db"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "When the command above completes, the `ibm_db` library is loaded in your notebook. \n",
    "\n",
    "\n",
    "## Task 2: Identify the database connection credentials\n",
    "\n",
    "Connecting to dashDB or DB2 database requires the following information:\n",
    "* Driver Name\n",
    "* Database name \n",
    "* Host DNS name or IP address \n",
    "* Host port\n",
    "* Connection protocol\n",
    "* User ID\n",
    "* User Password\n",
    "\n",
    "\n",
    "\n",
    "__Notice:__ To obtain credentials please refer to the instructions given in the first Lab of this course\n",
    "\n",
    "Now enter your database credentials below\n",
    "\n",
    "Replace the placeholder values in angular brackets <> below with your actual database credentials \n",
    "\n",
    "e.g. replace \"database\" with \"BLUDB\"\n",
    "\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": true
   },
   "outputs": [],
   "source": [
    "#Replace the placeholder values with the actuals for your Db2 Service Credentials\n",
    "dsn_driver = \"{IBM DB2 ODBC DRIVER}\"\n",
    "dsn_database = \"database\"            # e.g. \"BLUDB\"\n",
    "dsn_hostname = \"hostname\"            # e.g.: \"dashdb-txn-sbox-yp-dal09-04.services.dal.bluemix.net\"\n",
    "dsn_port = \"port\"                    # e.g. \"50000\" \n",
    "dsn_protocol = \"protocol\"            # i.e. \"TCPIP\"\n",
    "dsn_uid = \"username\"                 # e.g. \"abc12345\"\n",
    "dsn_pwd = \"password\"                 # e.g. \"7dBZ3wWt9XN6$o0J\""
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Task 3: Create the database connection\n",
    "\n",
    "Ibm_db API uses the IBM Data Server Driver for ODBC and CLI APIs to connect to IBM DB2 and Informix.\n",
    "\n",
    "\n",
    "Create the database connection\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#Create database connection\n",
    "#DO NOT MODIFY THIS CELL. Just RUN it with Shift + Enter\n",
    "dsn = (\n",
    "    \"DRIVER={0};\"\n",
    "    \"DATABASE={1};\"\n",
    "    \"HOSTNAME={2};\"\n",
    "    \"PORT={3};\"\n",
    "    \"PROTOCOL={4};\"\n",
    "    \"UID={5};\"\n",
    "    \"PWD={6};\").format(dsn_driver, dsn_database, dsn_hostname, dsn_port, dsn_protocol, dsn_uid, dsn_pwd)\n",
    "\n",
    "try:\n",
    "    conn = ibm_db.connect(dsn, \"\", \"\")\n",
    "    print (\"Connected to database: \", dsn_database, \"as user: \", dsn_uid, \"on host: \", dsn_hostname)\n",
    "\n",
    "except:\n",
    "    print (\"Unable to connect: \", ibm_db.conn_errormsg() )\n"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Task 4: Create a table in the database\n",
    "\n",
    "In this step we will create a table in the database with following details:\n",
    "\n",
    "<img src = \"https://ibm.box.com/shared/static/ztd2cn4xkdoj5erlk4hhng39kbp63s1h.jpg\" align=\"center\">\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#Lets first drop the table INSTRUCTOR in case it exists from a previous attempt\n",
    "dropQuery = \"drop table INSTRUCTOR\"\n",
    "\n",
    "#Now execute the drop statment\n",
    "dropStmt = ibm_db.exec_immediate(conn, dropQuery)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Dont worry if you get this error:\n",
    "If you see an exception/error similar to the following, indicating that INSTRUCTOR is an undefined name, that's okay. It just implies that the INSTRUCTOR table does not exist in the table - which would be the case if you had not created it previously.\n",
    "\n",
    "Exception: [IBM][CLI Driver][DB2/LINUXX8664] SQL0204N  \"ABC12345.INSTRUCTOR\" is an undefined name.  SQLSTATE=42704 SQLCODE=-204"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#Construct the Create Table DDL statement - replace the ... with rest of the statement\n",
    "createQuery = \"create table INSTRUCTOR(id INTEGER PRIMARY KEY NOT NULL, fname ...)\"\n",
    "\n",
    "#Now fill in the name of the method and execute the statement\n",
    "createStmt = ibm_db.replace_with_name_of_execution_method(conn, createQuery)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Double-click __here__ for the solution.\n",
    "\n",
    "<!-- Hint:\n",
    "\n",
    "createQuery = \"create table INSTRUCTOR(ID INTEGER PRIMARY KEY NOT NULL, FNAME VARCHAR(20), LNAME VARCHAR(20), CITY VARCHAR(20), CCODE CHAR(2))\"\n",
    "\n",
    "createStmt = ibm_db.exec_immediate(conn,createQuery)\n",
    "\n",
    "\n",
    "-->"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Task 5: Insert data into the table\n",
    "\n",
    "In this step we will insert some rows of data into the table. \n",
    "\n",
    "The INSTRUCTOR table we created in the previous step contains 3 rows of data:\n",
    "\n",
    "<img src=\"https://ibm.box.com/shared/static/j5yjassxefrjknivfpekj7698dqe4d8i.jpg\" align=\"center\">\n",
    "\n",
    "We will start by inserting just the first row of data, i.e. for instructor Rav Ahuja \n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#Construct the query - replace ... with the insert statement\n",
    "insertQuery = \"...\"\n",
    "\n",
    "#execute the insert statement\n",
    "insertStmt = ibm_db.exec_immediate(conn, insertQuery)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Double-click __here__ for the solution.\n",
    "\n",
    "<!-- Hint:\n",
    "\n",
    "insertQuery = \"insert into INSTRUCTOR values (1, 'Rav', 'Ahuja', 'TORONTO', 'CA')\"\n",
    "\n",
    "insertStmt = ibm_db.exec_immediate(conn, insertQuery)\n",
    "\n",
    "\n",
    "-->"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Now use a single query to insert the remaining two rows of data"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#replace ... with the insert statement that inerts the remaining two rows of data\n",
    "insertQuery2 = \"...\"\n",
    "\n",
    "#execute the statement\n",
    "insertStmt2 = ibm_db.exec_immediate(conn, insertQuery2)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Double-click __here__ for the solution.\n",
    "\n",
    "<!-- Hint:\n",
    "\n",
    "insertQuery2 = \"insert into INSTRUCTOR values (2, 'Raul', 'Chong', 'Markham', 'CA'), (3, 'Hima', 'Vasudevan', 'Chicago', 'US')\"\n",
    "\n",
    "insertStmt2 = ibm_db.exec_immediate(conn, insertQuery2)\n",
    "\n",
    "\n",
    "-->"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Task 6: Query data in the table\n",
    "\n",
    "In this step we will retrieve data we inserted into the INSTRUCTOR table. \n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#Construct the query that retrieves all rows from the INSTRUCTOR table\n",
    "selectQuery = \"select * from INSTRUCTOR\"\n",
    "\n",
    "#Execute the statement\n",
    "selectStmt = ibm_db.exec_immediate(conn, selectQuery)\n",
    "\n",
    "#Fetch the Dictionary (for the first row only) - replace ... with your code\n",
    "..."
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Double-click __here__ for the solution.\n",
    "\n",
    "<!-- Hint:\n",
    "\n",
    "#Construct the query that retrieves all rows from the INSTRUCTOR table\n",
    "selectQuery = \"select * from INSTRUCTOR\"\n",
    "\n",
    "#Execute the statement\n",
    "selectStmt = ibm_db.exec_immediate(conn, selectQuery)\n",
    "\n",
    "#Fetch the Dictionary (for the first row only)\n",
    "ibm_db.fetch_both(selectStmt)\n",
    "\n",
    "-->"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#Fetch the rest of the rows and print the ID and FNAME for those rows\n",
    "while ibm_db.fetch_row(selectStmt) != False:\n",
    "   print (\" ID:\",  ibm_db.result(selectStmt, 0), \" FNAME:\",  ibm_db.result(selectStmt, \"FNAME\"))"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Double-click __here__ for the solution.\n",
    "\n",
    "<!-- Hint:\n",
    "\n",
    "#Fetch the rest of the rows and print the ID and FNAME for those rows\n",
    "while ibm_db.fetch_row(selectStmt) != False:\n",
    "    print (\" ID:\",  ibm_db.result(selectStmt, 0), \" FNAME:\",  ibm_db.result(selectStmt, \"FNAME\"))\n",
    "\n",
    "-->"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Bonus: now write and execute an update statement that changes the Rav's CITY to MOOSETOWN "
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#Enter your code below\n"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Double-click __here__ for the solution.\n",
    "\n",
    "<!-- Hint:\n",
    "\n",
    "updateQuery = \"update INSTRUCTOR set CITY='MOOSETOWN' where FNAME='Rav'\"\n",
    "updateStmt = ibm_db.exec_immediate(conn, updateQuery))\n",
    "\n",
    "-->"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Task 7: Retrieve data into Pandas \n",
    "\n",
    "In this step we will retrieve the contents of the INSTRUCTOR table into a Pandas dataframe"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": true
   },
   "outputs": [],
   "source": [
    "import pandas\n",
    "import ibm_db_dbi"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": true
   },
   "outputs": [],
   "source": [
    "#connection for pandas\n",
    "pconn = ibm_db_dbi.Connection(conn)"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#query statement to retrieve all rows in INSTRUCTOR table\n",
    "selectQuery = \"select * from INSTRUCTOR\"\n",
    "\n",
    "#retrieve the query results into a pandas dataframe\n",
    "pdf = pandas.read_sql(selectQuery, pconn)\n",
    "\n",
    "#print just the LNAME for first row in the pandas data frame\n",
    "pdf.LNAME[0]"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "#print the entire data frame\n",
    "pdf"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Once the data is in a Pandas dataframe, you can do the typical pandas operations on it. \n",
    "\n",
    "For example you can use the shape method to see how many rows and columns are in the dataframe"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "pdf.shape"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Task 8: Close the Connection\n",
    "We free all resources by closing the connection. Remember that it is always important to close connections so that we can avoid unused connections taking up resources.\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {
    "collapsed": false
   },
   "outputs": [],
   "source": [
    "ibm_db.close(conn)"
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "## Summary\n",
    "\n",
    "In this tutorial you established a connection to a database instance of DB2 Warehouse on Cloud from a Python notebook using ibm_db API. Then created a table and insert a few rows of data into it. Then queried the data. You also retrieved the data into a pandas dataframe."
   ]
  },
  {
   "cell_type": "markdown",
   "metadata": {},
   "source": [
    "Copyright &copy; 2017-2018 [cognitiveclass.ai](cognitiveclass.ai?utm_source=bducopyrightlink&utm_medium=dswb&utm_campaign=bdu). This notebook and its source code are released under the terms of the [MIT License](https://bigdatauniversity.com/mit-license/).\n"
   ]
  },
  {
   "cell_type": "code",
   "execution_count": null,
   "metadata": {},
   "outputs": [],
   "source": []
  }
 ],
 "metadata": {
  "kernelspec": {
   "display_name": "Python 3",
   "language": "python",
   "name": "python3"
  },
  "language_info": {
   "codemirror_mode": {
    "name": "ipython",
    "version": 3
   },
   "file_extension": ".py",
   "mimetype": "text/x-python",
   "name": "python",
   "nbconvert_exporter": "python",
   "pygments_lexer": "ipython3",
   "version": "3.6.6"
  },
  "widgets": {
   "state": {},
   "version": "1.1.2"
  }
 },
 "nbformat": 4,
 "nbformat_minor": 2
}
