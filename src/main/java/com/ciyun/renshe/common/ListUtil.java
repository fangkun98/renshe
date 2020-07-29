package com.ciyun.renshe.common;

import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/4/27 17:47
 * @Author Admin
 * @Version 1.0
 */
@Slf4j
public class ListUtil {

    /**
     * 将一组数据固定分组，每组n个元素
     *
     * @param source 要分组的数据源
     * @param n      每组n个元素
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {

        if (null == source || source.size() == 0 || n <= 0)
            return null;
        List<List<T>> result = new ArrayList<>(10);

        int sourceSize = source.size();
        int size = (source.size() / n) + 1;
        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<>(n);
            for (int j = i * n; j < (i + 1) * n; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }

    /**
     * 树转换
     *
     * @param obj                  需要转换的对象
     * @param parentCodeFieldName  父标识字段名
     * @param parentCode           父标识值
     * @param currentCodeFieldName 当前标识字段名
     * @param childrenFiledName    子树的字段名
     * @param c                    需要转换的Class类型
     * @param <T>                  泛型
     * @return 返回List<T>
     */
    public static <T> List<T> tree(Object obj, String parentCodeFieldName, String parentCode, String currentCodeFieldName, String childrenFiledName, Class<T> c) {
        long t1 = System.currentTimeMillis();
        String jsonStr = JSON.toJSONString(obj);
        log.debug("树转换开始 >>>>>>>>>>>>>>>> {}", JSON.toJSONString(obj));
        //获取第一层级的数据
        JSONArray jsonArray = (JSONArray) JSONPath.read(jsonStr, "$[" + parentCodeFieldName + "=" + parentCode + "]");
        if (CollectionUtils.isEmpty(jsonArray)) {
            //为空的话直接返回空集合
            return Lists.newArrayList();
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String code = jsonObject.getString(currentCodeFieldName);
            treeChildren(jsonStr, jsonObject, parentCodeFieldName, code, currentCodeFieldName, childrenFiledName);
        }
        List<T> list = JSONArray.parseArray(jsonArray.toString(), c);
        log.debug("树转换结束, 转换时间: {} ms . >>>>>>>>>>>>>>>> {}", (System.currentTimeMillis() - t1), JSON.toJSONString(list));
        return list;
    }

    private static void treeChildren(String jsonStr, JSONObject currentJsonObj, String parentCodeFieldName, String parentCode, String currentCodeFieldName, String childrenFiledName) {
        JSONArray jsonArray = (JSONArray) JSONPath.read(jsonStr, "$[" + parentCodeFieldName + "=" + parentCode + "]");
        if (CollectionUtils.isEmpty(jsonArray)) {
            return;
        }
        currentJsonObj.put(childrenFiledName, jsonArray);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String code = jsonObject.getString(currentCodeFieldName);
            treeChildren(jsonStr, jsonObject, parentCodeFieldName, code, currentCodeFieldName, childrenFiledName);
        }
    }

    /**
     * - listToTree
     * - <p>方法说明<p>
     * - 将JSONArray数组转为树状结构
     * - @param arr 需要转化的数据
     * - @param id 数据唯一的标识键值
     * - @param pid 父id唯一标识键值
     * - @param child 子节点键值
     * - @return JSONArray
     */
    public static JSONArray listToTree(JSONArray arr, String id, String pid, String child) {
        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        //将数组转为Object的形式，key为数组中的id
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        //遍历结果集
        for (int j = 0; j < arr.size(); j++) {
            //单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            //在hash中取出key为单条记录中pid的值
            JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
            //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            if (hashVP != null) {
                //检查是否有child属性
                if (hashVP.get(child) != null) {
                    JSONArray ch = (JSONArray) hashVP.get(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            } else {
                r.add(aVal);
            }
        }
        return r;
    }

}
