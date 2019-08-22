
package com.canyinghao.canadapter;

import android.support.v7.widget.GridLayoutManager;

/**
 * Copyright 2016 canyinghao
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class CanSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final CanSpanSize adapter;
    private final int spanCount;
    private boolean isCustomSpanIndex;

    public CanSpanSizeLookup(CanSpanSize adapter, int spanCount) {
        this.adapter = adapter;
        this.spanCount = spanCount;

        setSpanIndexCacheEnabled(true);

    }

    @Override
    public void invalidateSpanIndexCache() {
        super.invalidateSpanIndexCache();
    }

    public void setCustomSpanIndex(boolean customSpanIndex) {
        isCustomSpanIndex = customSpanIndex;
    }

    public boolean isCustomSpanIndex() {
        return isCustomSpanIndex;
    }

    @Override
    public int getSpanSize(int position) {

        try{
            boolean isRow = adapter.isHeaderPosition(position)
                    || adapter.isFooterPosition(position)
                    || adapter.isGroupPosition(position);
            return isRow ? spanCount : adapter.getSpanSize(position);

        }catch (Throwable e){
            e.printStackTrace();
        }
        return 1;

    }


    @Override
    public int getSpanIndex(int position, int spanCount) {

        if(isCustomSpanIndex){
            return adapter.getSpanIndex(position,spanCount);
        }
        return super.getSpanIndex(position,spanCount);
    }


}
