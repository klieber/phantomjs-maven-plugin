import org.codehaus.plexus.util.FileUtils

def platform = System.properties['os.name'].toLowerCase();

def expected = 'target/phantomjs-maven-plugin/phantomjs-2.1.1-';

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

assert buildLog.contains('Downloading phantomjs binary') : 'phantomjs binaries were not downloaded.';
assert !buildLog.contains('phantomjs.binary.path=null') && !buildLog.contains('"phantomjs.binary.path":"null"') : "phantomjs.binary property was not properly set"
assert buildLog.contains('Hello, world!'): 'phantomjs script execution failed.';

