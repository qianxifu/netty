/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.resolver.dns;

import io.netty.util.concurrent.Promise;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

final class DnsNameResolverContext extends AbstractDnsNameResolverContext<InetSocketAddress> {
    DnsNameResolverContext(DnsNameResolver parent, String hostname, int port, Promise<InetSocketAddress> promise) {
        super(parent, hostname, port, promise);
    }

    @Override
    protected boolean finishResolveWithIPv4(List<InetAddress> resolvedAddresses, int port) {
        final int size = resolvedAddresses.size();

        for (int i = 0; i < size; i ++) {
            InetAddress a = resolvedAddresses.get(i);
            if (a instanceof Inet4Address) {
                promise.trySuccess(new InetSocketAddress(a, port));
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean finishResolveWithIPv6(List<InetAddress> resolvedAddresses, int port) {
        final int size = resolvedAddresses.size();

        for (int i = 0; i < size; i ++) {
            InetAddress a = resolvedAddresses.get(i);
            if (a instanceof Inet6Address) {
                promise.trySuccess(new InetSocketAddress(a, port));
                return true;
            }
        }

        return false;
    }
}
