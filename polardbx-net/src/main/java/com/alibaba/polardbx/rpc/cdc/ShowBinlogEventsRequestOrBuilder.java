/*
 * Copyright [2013-2021], Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: DumperServer.proto

package com.alibaba.polardbx.rpc.cdc;

public interface ShowBinlogEventsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:dumper.ShowBinlogEventsRequest)
    com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string logName = 1;</code>
     *
     * @return The logName.
     */
    java.lang.String getLogName();

    /**
     * <code>string logName = 1;</code>
     *
     * @return The bytes for logName.
     */
    com.google.protobuf.ByteString
    getLogNameBytes();

    /**
     * <code>int64 pos = 2;</code>
     *
     * @return The pos.
     */
    long getPos();

    /**
     * <code>int64 offset = 3;</code>
     *
     * @return The offset.
     */
    long getOffset();

    /**
     * <code>int64 rowCount = 4;</code>
     *
     * @return The rowCount.
     */
    long getRowCount();
}
