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

import pcg.talkbackplus.R;

public class Lesson1Part2Exercise extends GridItemExercise {

    @Override
    public void onAccessibilityClicked(int index) {
        if (index == 7) {
            notifyExerciseCompleted(false, R.string.tutorial_lesson_1_page2_complete_message);
        }
    }
}
