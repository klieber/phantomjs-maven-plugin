import org.codehaus.plexus.util.FileUtils

def platform = System.properties['os.name'].toLowerCase();

def expected = 'target/phantomjs-maven-plugin/phantomjs-1.9.6-';

if (platform.contains('win')) {
    expected += 'windows/phantomjs.exe';
} else if (platform.contains('mac')) {
    expected += 'macosx/bin/phantomjs';
} else if (platform.contains('nux')) {
    def arch = System.properties['os.arch'].contains('64') ? 'x86_64' : 'i686';
    expected += 'linux-' + arch + '/bin/phantomjs';
}

File binary = new File(basedir, expected);

assert binary.isFile()
assert binary.canExecute()

String buildLog = FileUtils.fileRead(new File(basedir, 'build.log'));

assert buildLog.contains('Hello, world!'): 'phantomjs script execution failed.';

