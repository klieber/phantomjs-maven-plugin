/*-
 * #%L
 * PhantomJS Maven Core
 * %%
 * Copyright (C) 2013 - 2017 Kyle Lieber
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */
package com.github.klieber.phantomjs.download;

import com.github.klieber.phantomjs.archive.PhantomJSArchive;
import com.github.klieber.phantomjs.util.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RuleBasedDownloaderTest {

  private static final String VERSION = "1.2";

  @Mock
  private Predicate<String> predicateA;

  @Mock
  private Predicate<String> predicateB;

  @Mock
  private Downloader downloaderA;

  @Mock
  private Downloader downloaderB;

  @Mock
  private PhantomJSArchive phantomJsArchive;

  private RuleBasedDownloader ruleBasedDownloader;

  @Before
  public void before() {
    Map<Downloader, Predicate<String>> rules = new LinkedHashMap<Downloader, Predicate<String>>();
    rules.put(downloaderA, predicateA);
    rules.put(downloaderB, predicateB);

    this.ruleBasedDownloader = new RuleBasedDownloader(rules);
  }

  @Test
  public void shouldUseDownloaderA() throws Exception {
    when(phantomJsArchive.getVersion()).thenReturn(VERSION);
    when(predicateA.apply(VERSION)).thenReturn(true);

    this.ruleBasedDownloader.download(phantomJsArchive);

    verify(downloaderA).download(phantomJsArchive);
    verifyNoMoreInteractions(downloaderB);
  }

  @Test
  public void shouldUseDownloaderB() throws Exception {
    when(phantomJsArchive.getVersion()).thenReturn(VERSION);
    when(predicateA.apply(VERSION)).thenReturn(false);
    when(predicateB.apply(VERSION)).thenReturn(true);

    this.ruleBasedDownloader.download(phantomJsArchive);

    verify(downloaderB).download(phantomJsArchive);
    verifyNoMoreInteractions(downloaderA);
  }

  @Test
  public void shouldUseDownloaderBAfterAFails() throws Exception {
    when(phantomJsArchive.getVersion()).thenReturn(VERSION);
    when(predicateA.apply(VERSION)).thenReturn(true);
    when(predicateB.apply(VERSION)).thenReturn(true);

    doThrow(new DownloadException("DownloaderA Failed")).when(downloaderA).download(phantomJsArchive);

    this.ruleBasedDownloader.download(phantomJsArchive);

    verify(downloaderA).download(phantomJsArchive);
    verify(downloaderB).download(phantomJsArchive);
  }

  @Test
  public void shouldFailDownloadDueToDownloaderAFailure() throws Exception {
    when(phantomJsArchive.getVersion()).thenReturn(VERSION);
    when(predicateA.apply(VERSION)).thenReturn(true);
    when(predicateB.apply(VERSION)).thenReturn(false);

    doThrow(new DownloadException("DownloaderA Failed")).when(downloaderA).download(phantomJsArchive);

    assertThatThrownBy(() -> this.ruleBasedDownloader.download(phantomJsArchive))
      .isInstanceOf(DownloadException.class)
      .hasMessage("DownloaderA Failed");

    verify(downloaderA).download(phantomJsArchive);
    verifyNoMoreInteractions(downloaderB);
  }

  @Test
  public void shouldFailDownloadDueToNoMatches() throws Exception {
    when(phantomJsArchive.getVersion()).thenReturn(VERSION);
    when(predicateA.apply(VERSION)).thenReturn(false);
    when(predicateB.apply(VERSION)).thenReturn(false);

    assertThatThrownBy(() -> this.ruleBasedDownloader.download(phantomJsArchive))
      .isInstanceOf(DownloadException.class)
      .hasMessage("No matching Downloader found.");

    verifyNoMoreInteractions(downloaderA);
    verifyNoMoreInteractions(downloaderB);
  }
}
