import org.codehaus.plexus.util.FileUtils

String buildLog = FileUtils.fileRead(new File(basedir, 'build.log'));

assert buildLog.contains('This script will exit with a non-zero code.'): 'phantomjs script execution failed.';

