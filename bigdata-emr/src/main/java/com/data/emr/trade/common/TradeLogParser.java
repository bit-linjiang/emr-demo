package com.data.emr.trade.common;

import com.alibaba.fastjson.JSON;
import com.data.emr.trade.model.TradeLog;

/**
 * 格式化日志数据
 * <p>
 * Created by candy on 2016/11/13.
 */
public class TradeLogParser {


    /**
     * 解析日志的行记录
     *
     * @param line 日志行数
     * @return tradeLog
     */
    public static TradeLog parse(String line) {
        return JSON.parseObject(line, TradeLog.class);
    }


}
