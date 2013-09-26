package com.github.klieber.phantomjs;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import de.schlichtherle.truezip.file.TFile;

/**
 * Install phantomjs binaries.
 */
@Mojo( name = "install", defaultPhase = LifecyclePhase.PROCESS_TEST_SOURCES )
public class InstallPhantomJsMojo extends AbstractMojo {

	@Parameter( defaultValue = "${project.build.directory}/phantomjs-maven-plugin", property = "outputDir", required = true )
  private File outputDirectory;

  @Parameter(required=true)
  private String version;
  
  @Parameter(defaultValue="https://phantomjs.googlecode.com/files/",required=true)
  private String baseUrl;
  
  @Parameter(defaultValue="${project}",readonly=true)
  private MavenProject mavenProject;
  
  public void execute() throws MojoExecutionException {
		if (!outputDirectory.exists() && !outputDirectory.mkdir()) {
			throw new MojoExecutionException("unable to create directory: " + outputDirectory);
		}
  	
  	PhantomJSArchive phantomJSFile = new PhantomJSArchiveBuilder(version).build();
  	
  	File extractTo = new File(outputDirectory,phantomJSFile.getExtractToPath());
		if (!extractTo.exists()) {
    	StringBuilder url = new StringBuilder();
    	url.append(baseUrl);
    	url.append(phantomJSFile.getArchiveName());
    	
    	try {
				URL downloadLocation = new URL(url.toString());
				getLog().info("Downloading phantomjs binaries from " + url);
				ReadableByteChannel rbc = Channels.newChannel(downloadLocation.openStream());
				File outputFile = new File(outputDirectory, phantomJSFile.getArchiveName());
				FileOutputStream fos = new FileOutputStream(outputFile);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				TFile archive = new TFile(outputDirectory, phantomJSFile.getPathToExecutable());
				
				getLog().info("Extracting "+archive.getAbsolutePath()+" to " + extractTo.getAbsolutePath());
				extractTo.getParentFile().mkdirs();
				archive.cp(extractTo);				
				extractTo.setExecutable(true);
				mavenProject.getProperties().setProperty("phantomjs.binary", extractTo.getAbsolutePath());
			} catch (MalformedURLException e) {
				throw new MojoExecutionException("unable to download " + url, e);
			} catch (IOException e) {
				throw new MojoExecutionException("unable to download " + url, e);
			}
		}
  }
}
