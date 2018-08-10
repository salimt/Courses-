# Running Your Files

We've set up the project structure for you, so once you download the files, you should be able to run each project and its tests individually! And now we have step-by-step guides on how to run tests or a main() method.

[Set up your tests](#the-hell-for-leather-overview) | [Detailed Troubleshooting](#detailed-troubleshooting) | [Run main methods](#running-main)

## IMPORTANT

### Opening Files
You __MUST__ not open an individual project, or bad things might happen! We are not responsible for what may ensue in the event that you disobey. (Just kidding - make a post about it on the discussion board if you do run into problems.) That is to say, open the project with the _same name_ as the GitHub repo.

![the correct way to open vs incorrect](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/what-to-not-do.png)


### "Do you want to add [...] to version control/Git/svn?"
Short answer: probably not

Long answer: Our repositories should have .gitignore files, which should prevent you from committing unwanted residue of your workspace specific to your machine (e.g. workspace.xml, .idea folder). Of course, you're free to add these files to git if you want, since you'll probably only be pushing and pulling to your own repositories for this course. However, it's better practice to leave your git repos clean and free of version- or machine-specific information.



## Test Configurations

### The Hell-for-Leather overview

![Test config gif](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_animation.gif)

<br />
<br />

## DETAILED TROUBLESHOOTING


1. Open your project in IntelliJ. (Don't be afraid of the vintage 15.0.2 edition used in these screenshots!)

![Opened project](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_2.PNG)


<br />

2. If the Project Structure sidebar doesn't open automatically, just click the sideways _1. Project_ button at the far left. If the _1. Project_ button does not appear at all, just press ⌘ + 1 on macOS, or Ctrl + 1 on Windows.

![Opened project with side Project bar also open](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_3.PNG)


<br />

3. Go to the top right, and click the greyed-out down-facing triangle. _Edit Configurations_ will appear. Click on it. 

![Selecting "Edit Configurations"](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_4.PNG)


<br />

4. Pour yourself a cup of coffee. 
   * This step is optional, but it definitely helps me feel like a programmer.

<br />
<br />

5. A new window! Click the green plus and select __JUnit__ from the dropdown.

![Edit configurations with dropdown open](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_5.PNG)


<br />

6. Now you'll get this window with some red error messages.

![Add JUnit application window with no info entered](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_6.PNG)


<br />

7. Go to "Use classpath of module" and select the module you want to test. For me in this tutorial, I'm working on Band, so let's choose that. 

![Use classpath of module selection](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_7.PNG)


<br />

8. Then, go up to "Class".

![Class field selected](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_8.PNG)


<br />

9. Choose the class wherein the tests live. 

![Choosing test class](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_9.PNG)


<br />

10. Alternatively, you can change the _Test Kind_ field from "Class" to "All in directory". That way you can specify the entire test folder to be run at once.

![All in directory selected](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_directory.PNG)


</br> 

11. Here's what it looked like once I configured the tests for each project.

![All configured](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_testsdone.PNG)


<br />

12. Once you're done, hit __Apply__ or __Ok__. Now you can run your tests with the little green triangle! When you want to switch test suites, just click on the same drop-down (that once was an upside-down triange and now has a name) and select your desired test suite.

![dropdown](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_12.png)


<br />

Done!

![Done](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_11.PNG)




<br />

## Running main

If your project has a `main` directory and/or `Main` class, you'll be able to run the main() program.

1. Right-click on the Main class and click on `Run Main.main()` or some variant of it.

![Clicking on Main and selection Run Main.main()](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_main.PNG)


<br />

2. A console window should pop up. You're able to type into it and give input.

![Showing console](https://edx-course-spdx-kiczales.s3.amazonaws.com/SC/GitHubPictures/github_readme_main_2.PNG)

You can also now run it by selecting Main and hitting the green arrow at the top. 
