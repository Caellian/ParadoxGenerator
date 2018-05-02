/*
 * The MIT License (MIT)
 * Paradox Generator, random sentence generator.
 * Copyright (c) 2018 Tin Švagelj <tin.svagelj@live.com> a.k.a. Caellian
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

package hr.caellian.paradox

import hr.caellian.paradox.data.Localizer
import hr.caellian.paradox.data.Node
import hr.caellian.paradox.lib.Constants
import hr.caellian.paradox.lib.Resources
import java.io.File
import java.util.*

object ParadoxGenerator {

    val seed: Long = 1L
    val random = Random(seed)

    @JvmStatic
    fun main(args: Array<String>) {
        println("${Constants.ProgramName} v${Constants.Version}, Copyright (C)")
        println(Constants.ProgramName + "v" + ", Copyright (C) 2018. " + Constants.ProgramAuthor)

        val path = "values.yaml"
        val localizerPath = "localizers/"
        File("localizers").mkdir()

        val localizer = Localizer("en-GB", "Great Britain")

        val a = Node("dog")
        localizer.localizations["dog"] = "A dog"
        val b = Node("dog.action.bit").also {
            a.children += it
        }
        localizer.localizations["dog.action.bit"] = " bit"
        val c = Node("dog.action.jumped").also {
            a.children += it
        }
        localizer.localizations["dog.action.jumped"] = " just jumped"
        val d = Node("action.across").also {
            c.children += it
        }
        localizer.localizations["action.across"] = " across"
        val e = Node("location.lake").also {
            d.children += it
        }
        localizer.localizations["location.lake"] = " a lake."
        val g = Node("location.forest").also {
            d.children += it
        }
        localizer.localizations["location.forest"] = " a forest."
        val f = Node("me.leg").also {
            b.children += it
        }
        localizer.localizations["me.leg"] = " my leg!"

        val lp = "$localizerPath${localizer.region}.yaml"

        Constants.Mapper.writeValue(File(path), a)
        Constants.Mapper.writeValue(File(lp), localizer)

        random.setSeed(Random().nextLong())
        Resources.localizer = Constants.Mapper.readValue(lp, Localizer::class.java)
        print(Constants.Mapper.readValue(File(path), Node::class.java))
    }
}
