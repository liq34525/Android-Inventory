*These rules are based on https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md*

*See also: https://source.android.com/setup/contribute/code-style and  https://github.com/futurice/android-best-practices*

## Contents
1. [File Naming](#1-file-naming)
    - Documentation files
    - Class files
    - Test files
    - Resource files
        - [Drawables](#drawables)
            - icons
            - selector states
        - [Layout files](#layout-files)
        - [Menu files](#menu-files)
2. [Variable Naming](#2-variable-naming)
    - Constants
    - Class (global) variables
    - Method variables
3. [Class Structure](#3-class-structure)
    - [Order](#ordering)
    - [Override methods](#override-methods)
    - [Import statements](#import-statements-order)
4. [Formatting](#4-formatting)
    - [Spacing](#spacing)
    - [Indentation](#use-tabs-for-indentation)
    - [Braces](#brace-style)
    - [Line length](#line-length-limit)
5. [Code guidelines](#5-code-guidelines)
    - [Commenting](#comments)
    - [Method length](#each-method-is-responsible-for-one-thing)
    - [Variable scope](#limit-variable-scope)
    - [Parameter order](#parameter-ordering-in-methods)
    - [Exceptions](#don't-ignore-exceptions)
6. [XML style](#6-XML-style-rules)
7. [Tests](#7-test-style)


# 1. File Naming
#### Documentation files (such as this one) 
- UPPERCASE-DASHES, by convention given in the project description 
- Formatted in markdown

   `EXAMPLE-FILE.md`
   
#### Class files
- UpperCamelCase
- If the class extends an Android component, its name ends with the name of the component

    `SignInActivity`  `SignInFragment` `ImageUploaderService` `ChangePasswordDialog`
    
### Test files
- Test classes should match the name of the class the tests are targeting, followed by `Test`
- For example, a test class for the `DatabaseHelper` should be named `DatabaseHelperTest`
    
    
### Resource files
- lowercase_underscore

#### Drawables

| Asset Type   | Prefix            |		Example               |
|--------------| ------------------|-----------------------------|
| Action bar   | `actbar_`          | `ab_stacked.9.png`          |
| Button       | `btn_`	            | `btn_send_pressed.9.png`    |
| Dialog       | `dialog_`         | `dialog_top.9.png`          |
| Divider      | `divider_`        | `divider_horizontal.9.png`  |
| Icon         | `icon_`	        | `icon_star.png`           |
| Menu         | `menu_	`           | `menu_submenu_bg.9.png`     |
| Notification | `notification_`	| `notification_bg.9.png`     |
| Tabs         | `tab_`            | `tab_pressed.9.png`         |

##### Icons 

| Asset Type                      | Prefix             | Example                      |
| --------------------------------| ----------------   | ---------------------------- |
| Icons                           | `icon_`              | `icon_star.png`                |
| Launcher icons                  | `icon_launcher`      | `icon_launcher_calendar.png`   |
| Menu icons and Action Bar icons | `icon_menu`          | `icon_menu_archive.png`        |
| Status bar icons                | `icon_stat_notify`   | `icon_stat_notify_msg.png`     |
| Tab icons                       | `icon_tab`           | `icon_tab_recent.png`          |
| Dialog icons                    | `icon_dialog`        | `icon_dialog_info.png`         |


##### Selector states

| State	       | Suffix          | Example                     |
|--------------|-----------------|-----------------------------|
| Normal       | `_normal`       | `btn_order_normal.9.png`    |
| Pressed      | `_pressed`      | `btn_order_pressed.9.png`   |
| Focused      | `_focused`      | `btn_order_focused.9.png`   |
| Disabled     | `_disabled`     | `btn_order_disabled.9.png`  |
| Selected     | `_selected`     | `btn_order_selected.9.png`  |


#### Layout files

Layout files should match the name of the Android components that they are intended for but moving the top level component name to the beginning. 

For example, if we are creating a layout for the `SignInActivity`, the name of the layout file should be `activity_sign_in.xml`.

| Component        | Class Name             | Layout Name                   |
| ---------------- | ---------------------- | ----------------------------- |
| Activity         | `UserProfileActivity`  | `activity_user_profile.xml`   |
| Fragment         | `SignUpFragment`       | `fragment_sign_up.xml`        |
| Dialog           | `ChangePasswordDialog` | `dialog_change_password.xml`  |
| AdapterView item | ---                    | `item_person.xml`             |
| Partial layout   | ---                    | `partial_stats_bar.xml`       |

A slightly different case is when we are creating a layout that is going to be inflated by an `Adapter`, e.g to populate a `ListView`. In this case, the name of the layout should start with `item_`.
Note that there are cases where these rules will not be possible to apply. For example, when creating layout files that are intended to be part of other layouts. In this case you should use the prefix `partial_`.

#### Menu files

Similar to layout files, menu files should match the name of the component. 

For example, if we are defining a menu file that is going to be used in the `UserActivity`, then the name of the file should be `activity_user.xml`
A good practice is to not include the word `menu` as part of the name because these files are already located in the `menu` directory.

# 2. Variable naming
- A longer variable name that is __clear__ is better than a short one that is not
- Do not use abbreviations unless agreed upon and documented here

##### Static final fields (constants)
- ALL_CAPS_UNDERSCORED

##### Private class fields
- UpperCamelCase

##### Method variables
- These are the only variables which should be lowerCamelCase


# 3. Class structure

### Ordering:
    1. Constants
    2. Fields
    3. Constructors
    4. Override methods and callbacks (public or private)
    5. Public methods
    6. Private methods
    7. Inner classes or interfaces


```java
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private String Title;
    private TextView TextViewTitle;

    @Override
    public void onCreate() {
        ...
    }

    public void setTitle(String title) {
    	Title = title;
    }

    private void setUpView() {
        ...
    }

    static class AnInnerClass {

    }
}
```

### Override methods

If your class is extending an __Android component__ such as an Activity or a Fragment, it is a good practice to order the override methods so that they __match the component's lifecycle__. For example, if you have an Activity that implements `onCreate()`, `onDestroy()`, `onPause()` and `onResume()`, then the correct order is:

```java
public class MainActivity extends Activity {
	//Order matches Activity lifecycle
    @Override
    public void onCreate() {}

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}

    @Override
    public void onDestroy() {}
}
```

### Import statements order

The ordering of import statements is:

1. Android imports
2. Imports from third parties (com, junit, net, org)
3. java and javax
4. Same project imports

To exactly match the IDE settings, the imports should be:

* Alphabetically ordered within each grouping, with capital letters before lower case letters (e.g. Z before a).
* There should be a blank line between each major grouping (android, com, junit, net, org, java, javax).

More info [here](https://source.android.com/source/code-style.html#limit-variable-scope)


# 4. Formatting

### Spacing

- Only one line spacing between methods and within methods

- No space at the beginning of a method

    ```java
    //do not do this:
    int myMethod() {
        
        int k = 0;
        
        return 1;
    }
    
    
    //too many spaces above
    int method2() {
        int k = 0;
        
        
        return 2;
    }
    ```
    
    ```java
    //this is correct:
    int myMethod() {
        int k = 0;
        
        return 1;
    }
    
    int method2() {
        int k = 0;
        
        return 2;
    }
    ```


- All method names should be immediately followed by a left parenthesis
    
    `method (i, j); //do not do this`
        
    `method(i, j); //this is correct`  
       
- All array dereferences should be immediately followed by a left square bracket
    
    `array [0]  //do not do this`
        
    `array[0]   //this is correct`  
       
- Operators and operands should be separated amongst themselves by one space
    
    `a=b+c;         //do not do this`
    
    `a = b + c;     //correct`
        
- Commas and semicolons are always followed by whitespace

    `for (int i = 0;i < 10;i++);    //do not do this`
    
    `for (int i = 0; i < 10; i++);  //correct`

- Keywords `if` `while` `for` `switch` `catch` must be followed by a space
    
    `if(meow) {     //bad kitty`

    `if (meow) {    //good kitty`



### Use tabs for indentation

Line up indentation for line wraps, and if you must wrap a method call, put each parameter on a separate line.

```java
Instrument i = someLongExpression(  that, 
                                    wouldNotFit, 
                                    on, 
                                    one, 
                                    line);
```

### Brace style

Braces go on the same line as the code before them.

```java
class MyClass {
    int func() {
        if (something) {
            // ...
        } 
        else if (somethingElse) {
            // ...
        } 
    }
}
```

Braces around the statements are required unless the condition and the body fit on one line.
If the condition and the body fit on one line and that line is shorter than the max line length, then braces are not required, e.g.

```java
if (condition) body();
```

This is __bad__:

```java
if (condition)
    body();  // bad!
```


### Line length limit

Code lines should not exceed __100 characters__. If the line is longer than this limit there are usually two options to reduce its length:

* Extract a local variable or method (preferable).
* Apply line-wrapping to divide a single line into multiple ones.

There are two __exceptions__ where it is possible to have lines longer than 100:

* Lines that are not possible to split, e.g. long URLs in comments.
* `package` and `import` statements.

#### Line-wrapping strategies

There isn't an exact formula that explains how to line-wrap and quite often different solutions are valid. However there are a few rules that can be applied to common cases.

__Break at operators__

When the line is broken at an operator, the break comes __before__ the operator. For example:

```java
int longName = anotherVeryLongVariable + anEvenLongerOne - thisRidiculousLongOne
        + theFinalOne;
```

__Assignment Operator Exception__

An exception to the `break at operators` rule is the assignment operator `=`, where the line break should happen __after__ the operator.

```java
int longName =
        anotherVeryLongVariable + anEvenLongerOne - thisRidiculousLongOne + theFinalOne;
```

__Method chain case__

When multiple methods are chained in the same line - for example when using Builders - every call to a method should go in its own line, breaking the line before the `.`

```java
Picasso.with(context).load("http://ribot.co.uk/images/sexyjoe.jpg").into(imageView);
```

```java
Picasso.with(context)
        .load("http://ribot.co.uk/images/sexyjoe.jpg")
        .into(imageView);
```

__Long parameters case__

When a method has many parameters or its parameters are very long, we should break the line after every comma `,`

```java
loadPicture(context, "http://ribot.co.uk/images/sexyjoe.jpg", mImageViewProfilePicture, clickListener, "Title of the picture");
```

```java
loadPicture(context,
        "http://ribot.co.uk/images/sexyjoe.jpg",
        mImageViewProfilePicture,
        clickListener,
        "Title of the picture");
```

# 5. Code guidelines
### Comments

Code should be self-explanatory and *not need* comments. Variable and method names should explain what the code is doing, and comments should only be necessary in specific cases.

### Each method is responsible for one thing

Methods should be as short as possible, ideally 2-3 lines long. If you find that you are commenting what a part of the code does, that is a good indication that that code can have its own method with an appropriate name.

### Limit variable scope

_The scope of local variables should be kept to a minimum (Effective Java Item 29). By doing so, you increase the readability and maintainability of your code and reduce the likelihood of error. Each variable should be declared in the innermost block that encloses all uses of the variable._

_Local variables should be declared at the point they are first used. Nearly every local variable declaration should contain an initializer. If you don't yet have enough information to initialize a variable sensibly, you should postpone the declaration until you do._ - ([Android code style guidelines](https://source.android.com/source/code-style.html#limit-variable-scope))

### Parameter ordering in methods

In general, methods should have as few parameters as possible.

When programming for Android, it is quite common to define methods that take a `Context`. If you are writing a method like this, then the __Context__ must be the __first__ parameter.

The opposite case are __callback__ interfaces that should always be the __last__ parameter.

Examples:

```java
// Context always goes first
public User loadUser(Context context, int userId);

// Callbacks always go last
public void loadUserAsync(Context context, int userId, UserCallback callback);
```

###  Don't ignore exceptions

You must never do the following:

```java
void setServerPort(String value) {
    try {
        serverPort = Integer.parseInt(value);
    } catch (NumberFormatException e) { }
}
```

_While you may think that your code will never encounter this error condition or that it is not important to handle it, ignoring exceptions like above creates mines in your code for someone else to trip over some day. You must handle every Exception in your code in some principled way. The specific handling varies depending on the case._ - ([Android code style guidelines](https://source.android.com/source/code-style.html))

See alternatives [here](https://source.android.com/source/code-style.html#dont-ignore-exceptions).


### Don't catch generic exceptions

You should not do this:

```java
try {
    someComplicatedIOFunction();        // may throw IOException
    someComplicatedParsingFunction();   // may throw ParsingException
    someComplicatedSecurityFunction();  // may throw SecurityException
    // phew, made it all the way
} catch (Exception e) {                 // I'll just catch all exceptions
    handleError();                      // with one generic handler!
}
```

See the reason why and some alternatives [here](https://source.android.com/source/code-style.html#dont-catch-generic-exception)


### Fully qualify imports

This is bad: `import foo.*;`

This is good: `import foo.Bar;`

See more info [here](https://source.android.com/source/code-style.html#fully-qualify-imports)


### Annotations practices

According to the Android code style guide, the standard practices for some of the predefined annotations in Java are:

* `@Override`: The @Override annotation __must be used__ whenever a method overrides the declaration or implementation from a super-class. For example, if you use the @inheritdocs Javadoc tag, and derive from a class (not an interface), you must also annotate that the method @Overrides the parent class's method.

* `@SuppressWarnings`: The @SuppressWarnings annotation should only be used under circumstances where it is impossible to eliminate a warning. If a warning passes this "impossible to eliminate" test, the @SuppressWarnings annotation must be used, so as to ensure that all warnings reflect actual problems in the code.

More information about annotation guidelines can be found [here](http://source.android.com/source/code-style.html#use-standard-java-annotations).


__Classes, Methods and Constructors__

When annotations are applied to a class, method, or constructor, they are listed after the documentation block and should appear as __one annotation per line__ .

```java
/* This is the documentation block about the class */
@AnnotationA
@AnnotationB
public class MyAnnotatedClass { }
```

__Fields__

Annotations applying to fields should be listed __on the same line__, unless the line reaches the maximum line length.

```java
@Nullable @Mock DataManager mDataManager;
```


### Logging guidelines

Use the logging methods provided by the `Log` class to print out error messages or other information that may be useful for developers to identify issues:

* `Log.v(String tag, String msg)` (verbose)
* `Log.d(String tag, String msg)` (debug)
* `Log.i(String tag, String msg)` (information)
* `Log.w(String tag, String msg)` (warning)
* `Log.e(String tag, String msg)` (error)

As a general rule, we use the class name as tag and we define it as a `static final` field at the top of the file. For example:

```java
public class MyClass {
    private static final String TAG = MyClass.class.getSimpleName();

    public myMethod() {
        Log.e(TAG, "My error message");
    }
}
```

VERBOSE and DEBUG logs __must__ be disabled on release builds. It is also recommended to disable INFORMATION, WARNING and ERROR logs but you may want to keep them enabled if you think they may be useful to identify issues on release builds. If you decide to leave them enabled, you have to make sure that they are not leaking private information such as email addresses, user ids, etc.

To only show logs on debug builds:

```java
if (BuildConfig.DEBUG) Log.d(TAG, "The value of x is " + x);
```

## 6. XML style rules

### Use self closing tags

When an XML element doesn't have any contents, you __must__ use self closing tags.

This is good:

```xml
<TextView
	android:id="@+id/text_view_profile"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content" />
```

This is __bad__ :

```xml
<!-- Don\'t do this! -->
<TextView
    android:id="@+id/text_view_profile"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" >
</TextView>
```


### Resources naming

Resource IDs and names are written in __lowercase_underscore__.

#### ID naming

IDs should be prefixed with the name of the element in lowercase underscore. For example:


| Element            | Prefix            |
| -----------------  | ----------------- |
| `TextView`           | `text_`             |
| `ImageView`          | `image_`            |
| `Button`             | `button_`           |
| `Menu`               | `menu_`             |

Image view example:

```xml
<ImageView
    android:id="@+id/image_profile"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

Menu example:

```xml
<menu>
	<item
        android:id="@+id/menu_done"
        android:title="Done" />
</menu>
```

#### Strings

String names start with a prefix that identifies the section they belong to. For example `registration_email_hint` or `registration_name_hint`. If a string __doesn't belong__ to any section, then you should follow the rules below:


| Prefix             | Description                           |
| -----------------  | --------------------------------------|
| `error_`             | An error message                      |
| `msg_`               | A regular information message         |
| `title_`             | A title, i.e. a dialog title          |
| `action_`            | An action such as "Save" or "Create"  |



#### Styles and Themes

Unlike the rest of resources, style names are written in __UpperCamelCase__.

### Attributes ordering

As a general rule you should try to group similar attributes together. A good way of ordering the most common attributes is:

1. View Id
2. Style
3. Layout width and layout height
4. Other layout attributes, sorted alphabetically
5. Remaining attributes, sorted alphabetically

# 7. Test style

Test classes should match the name of the class the tests are targeting, followed by `Test`. For example, if we create a test class that contains tests for the `DatabaseHelper`, we should name it `DatabaseHelperTest`.

Test methods are annotated with `@Test` and should generally start with the name of the method that is being tested, followed by a precondition and/or expected behaviour.

* Template: `@Test void methodNamePreconditionExpectedBehaviour()`
* Example: `@Test void signInWithEmptyEmailFails()`

Precondition and/or expected behaviour may not always be required if the test is clear enough without them.

Sometimes a class may contain a large amount of methods, that at the same time require several tests for each method. In this case, it's recommendable to split up the test class into multiple ones. For example, if the `DataManager` contains a lot of methods we may want to divide it into `DataManagerSignInTest`, `DataManagerLoadUsersTest`, etc. Generally you will be able to see what tests belong together because they have common [test fixtures](https://en.wikipedia.org/wiki/Test_fixture).



