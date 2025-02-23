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

package com.alibaba.polardbx.qatest.ddl.auto.tablegroup;

import com.alibaba.polardbx.optimizer.config.table.ComplexTaskMetaManager;
import com.alibaba.polardbx.optimizer.partition.PartitionStrategy;
import com.alibaba.polardbx.qatest.util.ConnectionManager;
import com.alibaba.polardbx.qatest.util.JdbcUtil;
import com.google.common.collect.ImmutableList;
import net.jcip.annotations.NotThreadSafe;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by luoyanxin.
 *
 * @author luoyanxin
 */
@RunWith(Parameterized.class)
@NotThreadSafe
public class AlterTableGroupAddPartitionTest extends AlterTableGroupBaseTest {

    private static List<ComplexTaskMetaManager.ComplexTaskStatus> tableStatus =
        Stream.of(
            ComplexTaskMetaManager.ComplexTaskStatus.DELETE_ONLY,
            ComplexTaskMetaManager.ComplexTaskStatus.WRITE_REORG,
            ComplexTaskMetaManager.ComplexTaskStatus.READY_TO_PUBLIC,
            ComplexTaskMetaManager.ComplexTaskStatus.PUBLIC).collect(Collectors.toList());

    static List<PartitionRuleInfo> partitionRuleInfos = new ArrayList<>(Arrays
        .asList(
            new PartitionRuleInfo(PartitionStrategy.RANGE,
                1,
                PARTITION_BY_BIGINT_RANGE,
                "alter tablegroup " + tableGroupName + " add partition (partition p9 values less than(100400))"),
            new PartitionRuleInfo(PartitionStrategy.RANGE_COLUMNS,
                2,
                PARTITION_BY_INT_BIGINT_RANGE_COL,
                "alter tablegroup " + tableGroupName + " add partition (partition p9 values less than(400, 100400))"),
            new PartitionRuleInfo(PartitionStrategy.LIST,
                1,
                PARTITION_BY_BIGINT_LIST,
                "alter tablegroup " + tableGroupName
                    + " add partition (partition p9 values in (100300,100301,100302,100303,100304,100305))"),
            new PartitionRuleInfo(PartitionStrategy.LIST_COLUMNS,
                4,
                PARTITION_BY_INT_BIGINT_LIST, "alter tablegroup " + tableGroupName
                + " add partition (partition p9 values in ((1,100300),(1,100301),(1,100302),(1,100303),(1,100304),(1,100305)))"))
    );

    private static PartitionRuleInfo partitionRuleInfo;
    static boolean firstIn = true;
    final static String logicalDatabase = "AlterTableGroupAddPartition";

    public AlterTableGroupAddPartitionTest(PartitionRuleInfo partitionRuleInfo) {
        super(logicalDatabase, ImmutableList.of(partitionRuleInfo.getTableStatus().name()));
        this.partitionRuleInfo = partitionRuleInfo;
        firstIn = true;
    }

    @Test
    public void testDDLOnly() {

    }

    @Parameterized.Parameters(name = "{index}:partitionRuleInfo={0}")
    public static List<PartitionRuleInfo[]> prepareData() {
        List<PartitionRuleInfo[]> status = new ArrayList<>();
        tableStatus.stream().forEach(c -> {
            partitionRuleInfos.stream().forEach(o -> {
                PartitionRuleInfo pi =
                    new PartitionRuleInfo(o.strategy, o.initDataType, o.partitionRule, o.alterTableGroupCommand);
                pi.setTableStatus(c);
                status.add(new PartitionRuleInfo[] {pi});
            });
        });
        return status;
    }

    @Before
    public void setUpTables() {
        if (firstIn) {
            setUp(true, partitionRuleInfo, false);
            firstIn = false;
        }
        partitionRuleInfo.connection = getTddlConnection1();
        String sql = "use " + logicalDatabase;
        JdbcUtil.executeUpdateSuccess(tddlConnection, sql);
    }
}
