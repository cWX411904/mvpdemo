package com.stkj.mvp.present.helper;

import com.google.common.collect.HashBiMap;

/**
 * Created by jarrahwu on 19/03/2018.
 *
 * 数据绑定器. 方便用于 UI 数据查找 事务数据, 事务数据反向也可以找到UI数据
 * @param <UI> UI数据
 * @param <BIZ> 事务数据
 *             
 */
public class SimpleDataBinder<UI, BIZ> implements IDataBinder<UI, BIZ> {

    private HashBiMap<BIZ, UI> biMap = HashBiMap.create();

    /**
     * 添加绑定
     * 
     * @param u UI数据
     * @param b 事务数据
     */
    @Override
    public void addBind(UI u, BIZ b) {
        biMap.forcePut(b, u);
    }

    /**
     * 通过事务数据, 删除对应的数据
     * 
     * @param b
     */
    @Override
    public void removeBindByBiz(BIZ b) {
        biMap.remove(b);
    }

    /**
     * 通过UI数据 删除对应的数据
     * 
     * @param u
     */
    @Override
    public void removeBindByUI(UI u) {
        biMap.inverse().remove(u);
    }

    /**
     * 通过UI数据查找事务数据
     * 
     * @param ui
     * @return
     */
    @Override
    public BIZ bizFromUI(UI ui) {
        return biMap.inverse().get(ui);
    }

    /**
     *
     * 通过事务数据查找UI数据
     *
     * @param biz
     * @return
     */
    @Override
    public UI uiFromBiz(BIZ biz) {
        return biMap.get(biz);
    }

    /**
     * 清空数据
     *
     */
    public void clear() {
        biMap.clear();
    }

    /**
     * 获取大小
     *
     * @return
     */
    @Override
    public int size() {
        return biMap.size();
    }

    /**
     * 置空,并且释放引用
     */
    @Override
    public void release() {
        clear();
        biMap = null;
    }

    /**
     * 获取UI数据数组
     *
     * @param array 需要写入的数组
     */
    @Override
    public void uiArray(UI[] array) {
        biMap.values().toArray(array);
    }

    /**
     * 获取事务的数据数组
     *
     * @param array 需要写入的数组
     */
    @Override
    public void bizArray(BIZ[] array) {
        biMap.keySet().toArray(array);
    }
}
