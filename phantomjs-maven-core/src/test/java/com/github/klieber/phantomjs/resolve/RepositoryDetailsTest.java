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
package com.github.klieber.phantomjs.resolve;

import com.github.klieber.phantomjs.resolve.RepositoryDetails;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryDetailsTest {

  @Mock
  private RepositorySystem repositorySystem;

  @Mock
  private RepositorySystemSession repositorySystemSession;

  private List<RemoteRepository> remoteRepositories = new ArrayList<>();

  @InjectMocks
  private RepositoryDetails repositoryDetails;

  @Before
  public void before() {
    repositoryDetails = new RepositoryDetails(repositorySystem, repositorySystemSession, remoteRepositories);
  }

  @Test
  public void testRepositorySystem() {
    assertThat(repositoryDetails.getRepositorySystem()).isSameAs(repositorySystem);
  }

  @Test
  public void testRepositorySystemSession() {
    assertThat(repositoryDetails.getRepositorySystemSession()).isSameAs(repositorySystemSession);
  }

  @Test
  public void testRemoteRepository() {
    assertThat(repositoryDetails.getRemoteRepositories()).isEqualTo(remoteRepositories);
  }
}
