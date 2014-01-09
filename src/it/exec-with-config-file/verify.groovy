import org.codehaus.plexus.util.FileUtils

def platform = System.properties['os.name'].toLowerCase();

def expected = 'target/phantomjs-maven-plugin/';

if (platform.contains('win')) {
    expected += 'phantomjs.exe';
} else if (platform.contains('mac')) {
    expected += 'bin/phantomjs';
} else if (platform.contains('nux')) {
    expected += '/bin/phantomjs';
}

File binary = new File(basedir, expected);

assert binary.isFile()
assert binary.canExecute()

File cookiesFile = new File(basedir, "target/cookies.txt");

assert cookiesFile.isFile() : 'phantomjs script execution failed.';

