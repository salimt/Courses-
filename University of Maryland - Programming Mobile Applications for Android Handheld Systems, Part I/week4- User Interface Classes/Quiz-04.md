Quiz 04
=======  

|Attempts|Score|  
|:------:|:---:|  
|  1/100 | 8/8 |  

Question 01
-----------  
Which of the following statements describe key responsibilities of a View?  
* To respond to events directed to them.  
* To contain other Views.  
* To handle device reconfiguration.  
* To draw themselves.  

### Answer  
* To respond to events directed to them.  
* To draw themselves.  

Question 02
-----------  
Which of the following are properties that can be set on a View?  
* Visibility.  
* Position.  
* Screen Orientation.  
* Opacity (transparency).  

### Answer  
* Visibility.  
* Position.  
* Opacity (transparency).  

Question 03  
-----------  
`(True or False)` - An AutoCompleteTextView is a subclass of ViewGroup. `Hint`: Consult the Android documentation at [http://developer.android.com/reference/classes.html](http://developer.android.com/reference/classes.html)  

### Answer  
`False`  

Question 04
-----------  
Which of the following statements describe the relationship between and AdapterView and its Adapter?  
* The Adapter manages a data set for the AdapterView.  
* The AdapterView creates the Views for the data in the Adapter.  
* The Adapter asks for Views from the AdapterView.  
* Adapters can notify the AdapterView when the Adapter data changes.  

### Answer  
* The Adapter manages a data set for the AdapterView.  
* Adapters can notify the AdapterView when the Adapter data changes.  

Question 05
-----------  
Suppose a layout file declares a LinearLayout called LL that contains two child Views, View1 and View2. In the layout file View1 is given an `android:layout_weight` of 2 and a `layout_width` of `0dp`. View 2 is given an `android:layout_weight` of 3 and a `layout_width` of `0dp`. In this example, which of the following statements must be true?  
* View 1 takes up 2/3 of LL's width.  
* View 2 takes up 2/3 of the display's width.  
* View 1 takes up 2/5 of the display's width.  
* View 2 takes up 3/5 of LL's width.  

### Answer  
View 2 takes up 3/5 of LL's width.  

Question 06
-----------  
When a user long clicks on a View that has registered to show a Context Menu, which one of the following methods will be called?  

### Answer  
`onCreateContextMenu()`.  

Question 07
-----------  
Suppose that an application wants to create and display a Dialog. If the application embodies the Dialog in a DialogFragment, which DialogFragment method will it call to make the Dialog visible to the user?  

### Answer  
`show()`  

Question 08
-----------  
The ActionBar has four functional areas: The App icon, a View control area, an Action Buttons area and an Action Overflow area. What is the purpose of the Action Overflow area?  
See: [http://developer.android.com/design/patterns/actionbar.html](http://developer.android.com/design/patterns/actionbar.html) for more information.  

### Answer  
When Action Buttons cannot fit in or should not be placed on the Action Bar, they are displayed in a separate View that is accessible by touching the Action Overflow area.  