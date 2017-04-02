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

package hr.caellian.paradox.util;

import hr.caellian.paradox.resource.StoredData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Caellian on 30.6.2015., at 1:56.
 */
public class StringManagement {
    public static String generate(ArrayList<String> values) {
        return idToText(getRandom(values, "").split(" "));
    }

    public static String getRandom(ArrayList<String> values, String built) {
        if (values != null && values.size() >= 1) {
            Random random = new Random();
            int rndNumber = random.nextInt(values.size());
            //noinspection SuspiciousMethodCalls
            return getRandom(StoredData.data.get(values.toArray()[rndNumber]), built + values.toArray()[rndNumber] + " ");
        } else {
            return Objects.equals(built, "") ? "" : built.substring(0, built.length() - 1);
        }
    }

    public static String idToText(String[] generated) {
        StringBuilder result = new StringBuilder();
        for (String key : generated) {
            result.append(StoredData.strings.get(key)).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }
}
