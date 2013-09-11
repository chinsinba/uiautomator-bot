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

package com.android.uiautomator;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class UiAutomatorView extends Composite {


	private ScreenShot shot;
	private XMLArea xmlArea;
	private NodeDetails nodeArea;

	public UiAutomatorView(Composite parent, int style) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout());

		SashForm baseSash = new SashForm(this, SWT.HORIZONTAL);
		shot = new ScreenShot(baseSash);


		// right sash is split into 2 parts: upper-right and lower-right
		// both are composites with borders, so that the horizontal divider can be highlighted by
		// the borders
		SashForm rightSash = new SashForm(baseSash, SWT.VERTICAL);

		nodeArea = new NodeDetails(rightSash);
		xmlArea = new XMLArea(rightSash,nodeArea,shot);
		shot.setXmlArea(xmlArea);

		// sets the ratio of the vertical split: left 5 vs right 3
		baseSash.setWeights(new int[]{4, 4});
	}




	public void setModel(UiAutomatorModel model, File modelBackingFile, Image screenshot) {
		shot.setmModel(model);
		xmlArea.setmModel(model);
		nodeArea.setmModel(model);
		
		shot.mModelFile = modelBackingFile;
		shot.setNewScreenShot(screenshot);
		
		xmlArea.loadTree();
	}

}
