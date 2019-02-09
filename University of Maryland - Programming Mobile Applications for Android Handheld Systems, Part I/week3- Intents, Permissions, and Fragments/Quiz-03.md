Quiz 03
=======  

|Attempts|Score|  
|:------:|:---:|  
|  1/100 | 9/9 |  

Question 01
-----------  
Suppose that an application includes an Activity named `A`, and that the application declares an `<activity>` tag for `A` within its `AndroidManifest.xml` file. If Activity `A` should be the main entry point for this application, then it will specify an `<intent-filter>` element, containing an `<action>` element. What value should you include to complete the following `<action>` element definition - `<action android:name= "..."/>`?  

### Answer  
`android.intent.action.MAIN`  

Question 02
-----------  
Suppose that an application includes an Activity named `A` and that the application declares an activity tag for `A` within its `AndroidManifest.xml` file. If Activity `A` will be the main entry point for its application and if an icon for this application/activity should appear in the top-level launcher, how should you complete the following `<category>` element - `<category android:name="..."/>`?  

### Answer  
`android.intent.category.LAUNCHER`  

Question 03  
-----------  
The MapLocationFromContacts application created an Intent with the Action, `Intent.ACTION_PICK` and a with data URI representing the contacts database. It then invoked an Activity using `startActivityForResult()`. What type of data will the started Activity return?  
* Text containing Contact Data items.  
* Phone Number.  
* A string Uri.  
* Images.

### Answer  
A String Uri.  

Question 04
-----------  
Suppose you create an application that uses the Vibration Service to make a device vibrate as a deadline approaches. To receive permission to use the Vibrator Service, you will need to add a `<uses-permission>` element to your application's `AndroidManifest.xml` file. What permission value should you use to complete the `<uses-permission>` element - `<uses-permission android:name="..."/>`?  
See: [http://developer.android.com/reference/android/Manifest.permission.html](http://developer.android.com/reference/android/Manifest.permission.html) for more information.  

### Answer  
`android.permission.VIBRATE`  

Question 05
-----------  
Suppose you create an application that captures and stores personal information from the user, such as the medicines they are currently taking. Other applications may want to use this information and then provide add-on services over it, for example, to create 'time to take your pill' reminders. Which of the following tags would you put in your application's `AndroidManifest.xml` file to define a new application-specific permission for accessing your application.  
* `<permission-tree>`  
* `<uses-permission>`  
* `<permission>`  
* `<permission-group>`  

### Answer  
`<permission>`  

Question 06
-----------  
`(True or False)` The Fragment class is a subclass of the Activity class and replaces Activities on large screen devices such as Tablets.  

### Answer  
False  

Question 07
-----------  
In which method do Fragments typically create their user interfaces?  
* `onCreateView()`  
* `onAttach()`  
* `onActivityCreated()`  
* `onCreate()`  

### Answer  
`onCreateView()`  

Question 08
-----------  
Which of the following are good reasons for dynamically modifying application layouts at runtime, rather than by using static layout files.  
* Dynamically-created layouts will appear on the screen and will respond noticeably faster than static layouts will.  
* Dynamic layouts can take advantage of contextual information that's not tracked by Android's configuration system (such as current location, usage time, or ambient light measurements).  
* Dynamically-created user interfaces can adapt to an application's runtime state, such as the amount of data that needs to be displayed at any one time.  
* Static layouts can't take advantage of contextual information, such as the device's orientation.  

### Answer  
* Dynamic layouts can take advantage of contextual information that's not tracked by Android's configuration system (such as current location, usage time, or ambient light measurements).  
* Dynamically-created user interfaces can adapt to an application's runtime state, such as the amount of data that needs to be displayed at any one time.  

Question 09
-----------  
Suppose you have an Activity that hosts a Fragment. This Fragment has invoked the `setRetainInstance()` method, passing in the parameter true. Which of the following Fragment lifecycle methods will not be called if the Activity is later killed and restarted due to a reconfiguration?  
* `onAttach()`  
* `onDestroy()`  
* `onCreateView()`  
* `onCreate()`  
* `onDestroyView()`  

### Answer  
* `onDestroy()`  
* `onCreate()`  