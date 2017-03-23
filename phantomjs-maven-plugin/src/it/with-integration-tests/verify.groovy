import org.codehaus.plexus.util.FileUtils;

String buildLog = FileUtils.fileRead(new File(basedir, 'build.log'));

assert !buildLog.contains('phantomjs.binary.path=null') && !buildLog.contains('"phantomjs.binary.path":"null"') : "phantomjs.binary property was not properly set"
assert buildLog.contains('Tests run: 1, Failures: 0, Errors: 0, Skipped: 0') : 'junit tests failed to execute'
assert buildLog.contains('BUILD SUCCESS') : 'build was not successful'
