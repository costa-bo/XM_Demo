# XM Demo

1. [Package contents](#usrdesc)
2. [Software prerequisites](#usrPrerequisites)
3. [Test case(s) execution](#usrtcexec)
4. [Test case execution reporting](#usrtcerep)

### User Guide

1. **Package contents**

   <a name="usrdesc"></a>
   The <b>Demo</b> folder consists of the following folders and files:

   |file/folder| description|
          |---------------|----------------|
   |src|folder that contains the project and the resources needed to run the project|
   |.gitignore| A gitignore file specifies intentionally untracked files that Git should ignore.|
   |launch-test.cmd| Command prompt file for the execution of the tests|
   |launch-test.sh| Shell script file for the execution of the tests|
   |pom.xml|file that contains the libraries needed and is used by maven for build|
   |readme.md|file that contains the test automation project 'guidelines'|

2. **Software Prerequisites**

   <a name="usrPrerequisites"></a>

   The following software must be installed to the machine where the test cases run from
    * Java JDK 11

      https://www.oracle.com/java/technologies/javase-jdk8-downloads.html

      Dependency required for the execution of Test Cases. To ensure that Java has
      been installed in your system, just open a command line prompt and run the following command:
        ```
        java -version
        ````
      If everything installed as expected, you should take the following output:
        ```
        java version "11"
        ```

    * Apache Maven

      https://maven.apache.org/download.cgi

      Depedency required for the execution of Test Cases. Now you should set the maven path
      to your system PATH variable. Read more at https://maven.apache.org/install.html
      To ensure that maven has been installed successfully in your system open a command prompt and
      run the following command:
        ```
        mvn -version
        ```
      If everything installed correctly, you should see something like the following:
        ```
        Apache Maven 3.9.2
        ```
      
3. **Test case(s) execution**

   <a name="usrtcexec"></a>

   **Windows**

   To run all tests you first need to navigate in the root of the test framework project. Then click on the
   address bar and type "cmd". A command line windows will open for the current folder.
   Start the test cases execution by typing:
     ```
     launch-test.cmd
     ```

   If you want to run a specific test case, just type after the command the specified id of the test case, for example:
    ```
    launch-test.cmd TC_UI_01_DEMO
    ```

   **Linux**

   To run all tests you first need to navigate in the root of the test framework project using a bash shell.
   First make sure that the "launch-test.sh" is marked as an executable file and start the test cases execution by typing:

    ```
    ./launch-test.sh
    ```

   If you want to run a specific test case, just type after the command the specified id of the test case, for example:
   ```
   ./launch-test.sh TC_UI_01_DEMO
   ```

   According to your settings, you may see a chrome window open. It is strongly recommended to not touch your device
   or do any other action till the test framework finish the execuction.

4. **Test case execution reporting**

   <a name="usrtcerep"></a>

   After the test execution complete, you can review the results by navigating to:
   ```
   <Test Framework Root Folder>/target/site/serenity/index.html
   ```

   Just open this file with a browser of your choice.