/*
 * Copyright @ 2018 - Present, 8x8 Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jitsi.nlj.PacketInfo
import org.jitsi.nlj.transform.node.ObserverNode
import org.jitsi.nlj.transform.node.PcapWriter
import org.jitsi.utils.logging2.Logger
import java.util.Date

class ToggleablePcapWriter(
    val parentLogger: Logger,
    val prefix: String
) : ObserverNode("Toggleable pcap writer: $prefix") {
    private var pcapWriter: PcapWriter? = null

    override fun observe(packetInfo: PacketInfo) {
        pcapWriter?.processPacket(packetInfo)
    }

    fun enable() {
        if (pcapWriter == null) { PcapWriter(parentLogger, "/tmp/$prefix-${Date().toInstant()}.pcap") }
    }

    fun disable() {
        pcapWriter?.also {
            pcapWriter = null
            it.close()
        }
    }
}
