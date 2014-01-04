# jconfig

jconfig is a tool built for <b>Java</b> programmers to easily manage 
configurations of an application. Entries can be stored as <b>Booleans</b>, 
<b>Numbers</b>, and <b>String</b>. The generic interface of the library allows 
users to <b>implement their own model</b> as well as use the built in defaults.

## Usage
Using jconfig is very easy and it is documented. The built in 
implementations include an XML-based and a JSON-based configuration 
managers. Have a look at the test cases in the "test" folder.

## Version
0.1

## Build
jconfig uses Apache Ant ([http://ant.apache.org/](http://ant.apache.org/)) for builds. All properties used reside in 
the text file "<b>ant.properties</b>" and the ant script is "<b>build.xml</b>". <p>
The simplest way to build is simply running the ant executable in the root project 
folder, You need to make sure that ant executable is in the Path. 
See [http://ant.apache.org/manual/install.html](http://ant.apache.org/manual/install.html).
<p>
    <i>C:\jconfig\ant</i> will build fine if the root project folder is "<b>C:\jconfig</b>" (Windows example)
<p>
The build targets include:<br>
<ul>
    <li>
        <b>build</b> -- compiles the entire source (excluding test cases) and zips the compiled .class files and the source .java files into two separate .jar files in the "<b>lib</b>" folder
    </li>
    <li>
        <b>clean</b> -- cleans all built directories, including their contents (built .jars, .class etc)
    </li>
    <li>
        <b>test</b> -- compiles all sources and runs the main test class under the "test" folder
    </li>
    <li>
        <b>compile</b> -- compiles the entire source (excluding test cases)
    </li>
    <li>
        <b>jar-all</b> -- same as <b>build</b>
    </li>
    <li>
        <b>jar</b> -- compiles the entire source (excluding tests) and zips the compiled .class files in a .jar file and puts it in the "<b>lib</b>" folder
    </li>
    <li>
        <b>jar-sources</b> -- zips the source .java files in a .jar file and puts it in the "<b>lib</b>" folder
    </li>
    <li>
        <b>clean-dist</b> -- cleans the built .jar files
    </li>
    <li>
        <b>clean-build</b> -- cleans the built .class files
    </li>
    <li>
        <b>compile-test</b> -- compiles the entire source (and test cases
    </li>
</ul>

## License
jconfig is released under Apache License 2.0.

jconfig also includes some classes from JSON.org for parsing JSON text. 
The JSON license is available at 
[http://www.json.org/license.html](http://www.json.org/license.html).

# Authors
Pranjal Raihan <prshreshtha at yahoo.com>

# Contributors
None so far
