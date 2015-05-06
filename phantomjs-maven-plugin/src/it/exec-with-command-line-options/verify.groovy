import org.codehaus.plexus.util.FileUtils

String buildLog = FileUtils.fileRead(new File(basedir, 'build.log'));

assert buildLog.contains('[DEBUG]'): 'phantomjs script execution failed.';

