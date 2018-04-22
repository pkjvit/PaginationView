# PaginationView
Pagination view for Android to show large amount of data just like PHP paginator.

## Screen Shots

<div>
<img src="https://raw.githubusercontent.com/pkjvit/PaginationView/master/ScreenShots/device-2018-04-22-181415.png" width="200">
<img width="20">
<img src="https://raw.githubusercontent.com/pkjvit/PaginationView/master/ScreenShots/device-2018-04-22-181319.png" width="200">
</div>

## Usage
1. Add the following in the dependencies section of the application's build.gradle (the one in the app folder).

```
dependencies {
    compile 'com.pkj.wow.paginationview:PaginationView:1.0.1-1'
}
```

2. Add PaginationView to your activity's layout and set parent property "clipChildren" to false.
```xml
<com.pkj.wow.paginationview.PaginationView
        android:id="@+id/pagination_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:clipChildren="false"
        />
```

3. Set total data size, page size and add page update listener which will call after pager update.
```
mPaginationView.setPager(list.size(), 50);
mPaginationView.setOnPagerUpdate(new PaginationView.OnPagerUpdate() {
    @Override
    public void onUpdate(int pageNumber, int pageSize) {
        // query your data from page number to page size according to your requirement
        ...
    }
});
```

## Example

Activity layout file
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:clipChildren="false"
    tools:context=".MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/my_recycler_view"
        android:scrollbars="vertical"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

    <com.pkj.wow.paginationview.PaginationView
        android:id="@+id/pagination_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:clipChildren="false"
        />
        
</LinearLayout>
```

Activity code
```
mAdapter = new MyAdapter(getDataset(0, pageSize));
mRecyclerView.setAdapter(mAdapter);

mPaginationView.setPager(size, 50);
mPaginationView.setOnPagerUpdate(new PaginationView.OnPagerUpdate() {
    @Override
    public void onUpdate(int pageNumber, int pageSize) {
        mAdapter.setDataset(getDataset(pageNumber, pageSize));
        mAdapter.notifyDataSetChanged();
    }
});
```

## Licence
    Copyright 2018 Pankaj Jangid

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
