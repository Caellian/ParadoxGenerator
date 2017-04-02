/*
 * The MIT License (MIT)
 * Paradox Generator, random sentence generator.
 * Copyright (c) 2017 Tin Švagelj <tin.svagelj@live.com> a.k.a. Caellian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package hr.caellian.core.versionControl;

import java.net.URL;
import java.util.Optional;

/**
 * Class used to store and handle version data.
 *
 * @author Caellian
 */

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class VersionData implements Comparable<VersionData> {
    public final Version version;
    public final Optional<String> name;
    public final Optional<String> changelog;
    public final Optional<URL> downloadLink;

    public VersionData(Version version) {
        this(version, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public VersionData(Version version, Optional<String> name, Optional<String> changelog, Optional<URL> downloadLink) {
        this.version = version;
        this.name = name;
        this.changelog = changelog;
        this.downloadLink = downloadLink;
    }

    @Override
    public int compareTo(VersionData other) {
        return this.version.major - other.version.major != 0 ? this.version.major - other.version.major :
                (this.version.minor - other.version.minor != 0 ? this.version.minor - other.version.minor : (this.version.patch - other.version.patch));
    }

}
