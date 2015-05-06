def platform = System.properties['os.name'].toLowerCase();

def expected = 'target/phantomjs-maven-plugin/phantomjs-1.9.2-';

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

File cookiesFile = new File(basedir, "target/cookies.txt");

assert cookiesFile.isFile() : 'phantomjs script execution failed.';

