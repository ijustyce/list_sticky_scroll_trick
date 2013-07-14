/*
 * Copyright 2013 Javier Tarazaga
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

package com.example.android.listviewscrolltrick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.listviewscrolltrick.R;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mStickyView;
	private View mPlaceholderView;
	private ListView mListView;
	private View mItemTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mStickyView = (TextView) findViewById(R.id.sticky);
		mStickyView.setText(R.string.sticky_item);
		mListView = (ListView) findViewById(R.id.listView);
		mItemTop = findViewById(R.id.itemTop);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.top_layout, null);

		mPlaceholderView = v.findViewById(R.id.placeholder);
		mListView.addHeaderView(v);

		mListView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@SuppressLint("NewApi")
					@SuppressWarnings("deprecation")
					@Override
					public void onGlobalLayout() {
						onScrollChanged();

						ViewTreeObserver obs = mListView.getViewTreeObserver();
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
							obs.removeOnGlobalLayoutListener(this);
						} else {
							obs.removeGlobalOnLayoutListener(this);
						}
					}
				});

		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				onScrollChanged();
			}
		});

		// Create and set the adapter for the listView.
		SimpleAdapter simpleAdpt = new SimpleAdapter(this, createListViewData(), R.layout.list_item, new String[] {"item"}, new int[] {android.R.id.text1});		
		mListView.setAdapter(simpleAdpt);
	}

	/**
	 * Function used to calculate the position of the sticky view according to the position of the first item in the ListView.
	 */
	private void onScrollChanged() {
		View v = mListView.getChildAt(0);
		int top = (v == null) ? 0 : v.getTop();

		// This check is needed because when the first element reaches the top of the window, the top values from top are not longer valid. 
		if (mListView.getFirstVisiblePosition() == 0) {
			mStickyView.setTranslationY(
					Math.max(0, mPlaceholderView.getTop() + top));

			// Set the image to scroll half of the amount scrolled in the ListView.
			mItemTop.setTranslationY(top / 2);
		}			
	}

	/**
	 * Populate the ListView with example data.
	 * @return
	 */
	private List<Map<String, String>> createListViewData() {
		List<Map<String, String>> itemList = new ArrayList<Map<String,String>>();

		for (int i = 0; i < 30; i++) {
			itemList.add(createItem("item", "Item " + i));
		}

		return itemList;
	}

	/**
	 * Function used to create the HashMap needed for ListView item using Simple Adapter.
	 * @param key
	 * @param name
	 * @return
	 */
	private HashMap<String, String> createItem(String key, String name) {
		HashMap<String, String> item = new HashMap<String, String>();
		item.put(key, name);

		return item;
	}

}
