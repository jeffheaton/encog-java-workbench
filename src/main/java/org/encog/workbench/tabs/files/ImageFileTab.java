/*
 * Encog(tm) Workbench v3.3
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-workbench
 *
 * Copyright 2008-2014 Heaton Research, Inc.
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
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package org.encog.workbench.tabs.files;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.frames.document.tree.ProjectFile;

public class ImageFileTab extends BasicFileTab {
	
	private BufferedImage image;
	
	public ImageFileTab(ProjectFile file) {
		super(file);
		
		try {
		    this.image = ImageIO.read(file.getFile());
		} catch (IOException e) {
			EncogWorkBench.displayError("Error Loading Image", e);
		}
		
	}
		
	public boolean close()
	{
		return true;
	}
	
	public void paint(final Graphics g) {
		super.paint(g);
		g.drawImage(this.image, 0,0, this);
	}
	
}
