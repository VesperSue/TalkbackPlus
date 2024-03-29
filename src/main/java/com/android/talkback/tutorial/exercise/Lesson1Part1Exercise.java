/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.android.talkback.tutorial.exercise;

import android.view.View;
import android.view.ViewGroup;

import pcg.talkbackplus.R;

import java.util.HashSet;
import java.util.Set;

public class Lesson1Part1Exercise extends GridItemExercise {

    private static final int ITEMS_TO_EXPLORE = 5;

    private Set<Integer> mExploredItems = new HashSet<>();

    @Override
    public void onAccessibilityFocused(int index) {
        mExploredItems.add(index);
        if (mExploredItems.size() == ITEMS_TO_EXPLORE) {
            View view = getView();
            setViewsNotImportantForAccessibilityCompat(view);
            notifyExerciseCompleted(true, R.string.tutorial_exercise_completed_auto);
        }
    }

    private void setViewsNotImportantForAccessibilityCompat(View view) {
        if (view == null) {
            return;
        }

        view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setViewsNotImportantForAccessibilityCompat(viewGroup.getChildAt(i));
            }
        }
    }

    @Override
    public void clear() {
        mExploredItems.clear();
    }
}
