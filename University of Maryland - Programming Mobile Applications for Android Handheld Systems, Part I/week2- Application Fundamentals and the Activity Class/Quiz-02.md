Quiz 02
=======  

|Attempts|Score|  
|:------:|:---:|  
|  1/100 |10/10|  

Question 01
-----------  
Which two of the following statements capture the main purposes of the Service class?  
* To enable remote (inter-process) operations.  
* To support long-running, in the background operations.  
* To provide an application's user interface.  
* To manage concurrent access to shared databases.  

### Answer  
* To enable remote (inter-process) operations.  
* To support long-running, in the background operations.  

Question 02
-----------  
Ignoring the initial xml declaration, what is the outermost xml tag used in the AndroidManifest.xml file?  

### Answer  
`manifest`  

Question 03  
-----------  
How would you specify a string called "exit_message" whose value is "Goodbye", within a strings.xml file?  

### Answer  
`<string name="exit_message">Goodbye</string>`  

Question 04
-----------  
`(True or False)` One of the main jobs of the Activity class is to support concurrent access to shared, inter-application data.  

### Answer  
False  

Question 05
-----------  
The example applications for this lecture contain Activities that call `setContentView()`, passing in a resource ID. Which one of the following statements correctly reflects what the `setContentView()` method does?  

### Answer  
It processes the underlying resource file to create Java Objects corresponding to the elements specified in the resource file.  

#### Explanation  
The process of creating Java Objects corresponding to a resource is called `inflating`.  

Question 06
-----------  
Suppose that your application has brought `Activity A` into the foreground. The user then presses a button shown by `Activity A`, which causes `Activity B` to be brought into the foreground, ready for user interaction. At this point, what was most likely the last lifecycle method called on `Activity A` and on `Activity B`?  

### Answer  
`Activity A.onStop()` and `Activity B.onResume()`.  

Question 07
-----------  
Suppose you write an Activity that edits some persistent user information such as the user's account name, but does not save it immediately. This important data must be saved before the application exits. In which lifecycle method should you normally save the data?  

### Answer  
`onPause()`  

#### Explanation  
Remember that `onStop()` and `onDestroy()` may not always be called.  

Question 08
-----------  
Suppose you attach an OnClickListener to a Button in your Activity. When will this Object's `onClick()` method be called?  

### Answer  
When the user presses and releases the Button in quick succession.  

Question 09
-----------  
One example of a configuration change, is when the user changes the device's global font size. If you want to handle this configuration change manually, what value would you add to a android:configChanges attribute in the application's AndroidManifest.xml file? See: [http://developer.android.com/guide/topics/manifest/activity-element.html](http://developer.android.com/guide/topics/manifest/activity-element.html) for more information.  

### Answer  
`fontScale`  

Question 10
-----------  
Unless you have a strong reason for doing so, you should generally avoid handling configuration changes manually. Which one of the following statements best explains why?  

### Answer  
It can be difficult to know and capture each configuration change that might occur.  

#### Explanation  
Despite this difficulty, you may still sometimes decide to handle configuration changes manually. For example, when your application doesn't need to update resources during a particular configuration change and when your application's performance would suffer from an activity restart.  