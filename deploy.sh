#!/bin/sh

if [ ! "$1" ]; then
  echo 'please provide the version'
  exit 1;
fi

mkdir -p target/binary-deployer
cd target/binary-deployer

groupId=com.github.klieber
artifactId=phantomjs
version=$1

baseUrl="https://bitbucket.org/ariya/phantomjs/downloads"
#baseUrl="https://phantomjs.googlecode.com/files"

sonatypeUrl="https://oss.sonatype.org/service/local/staging/deploy/maven2/"
repoId=ossrh

deploy() {
  classifier=$1
  extension=$2
  filename="$artifactId-$version-$classifier.$extension"
  echo "deploying $baseUrl/$filename"

  wget "$baseUrl/$filename" && mvn gpg:sign-and-deploy-file -Durl=$sonatypeUrl -DrepositoryId=$repoId -Dfile=$filename -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$version -Dpackaging=$extension -Dclassifier=$classifier -DgeneratePom=false
}

deploy "windows" "zip"
deploy "macosx" "zip"
deploy "linux-x86_64" "tar.bz2"
deploy "linux-i686" "tar.bz2"
