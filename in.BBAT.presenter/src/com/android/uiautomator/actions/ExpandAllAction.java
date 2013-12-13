/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.uiautomator.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.android.uiautomator.XMLArea;

public class ExpandAllAction extends Action {

	XMLArea mView;

    public ExpandAllAction(XMLArea xmlArea) {
        super("&Expand All");
        mView = xmlArea;;
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return ImageHelper.loadImageDescriptorFromResource("icons/expandall.png");
    }

    @Override
    public void run() {
        mView.expandAll();
    }
}
