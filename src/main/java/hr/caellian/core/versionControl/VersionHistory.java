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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

/**
 * @author Caellian
 */
public class VersionHistory extends ArrayList<VersionData> implements SortedSet<VersionData>, Comparator<VersionData> {
    public VersionHistory() {
    }

    public VersionHistory(List<VersionData> versionData) {
        this.addAll(versionData);
        this.sort(this);
    }

    @Override
    public Comparator<? super VersionData> comparator() {
        return this;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public SortedSet<VersionData> subSet(VersionData fromElement, VersionData toElement) {
        ArrayList<VersionData> versionDatas = new ArrayList<>();
        for (int version = this.indexOf(fromElement); version < this.indexOf(toElement); version++) {
            versionDatas.add(this.get(version));
        }
        return new VersionHistory(versionDatas);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public SortedSet<VersionData> headSet(VersionData toElement) {
        ArrayList<VersionData> versionDatas = new ArrayList<>();
        for (int version = 0; version < this.indexOf(toElement); version++) {
            versionDatas.add(this.get(version));
        }
        return new VersionHistory(versionDatas);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public SortedSet<VersionData> tailSet(VersionData fromElement) {
        ArrayList<VersionData> versionData = new ArrayList<>();
        for (int version = this.indexOf(fromElement); version < this.size(); version++) {
            versionData.add(this.get(version));
        }
        return new VersionHistory(versionData);
    }

    /**
     * @return latest version.
     */
    @Override
    public VersionData first() {
        return this.get(0);
    }

    /**
     * @return oldest version.
     */
    @Override
    public VersionData last() {
        return this.get(this.size() - 1);
    }

    @Override
    public int compare(VersionData preVersionData, VersionData postVersionData) {
        return preVersionData.compareTo(postVersionData);
    }
}
