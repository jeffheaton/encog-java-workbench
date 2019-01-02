[![Build Status](https://travis-ci.org/encog/encog-java-workbench.svg?branch=master)](https://travis-ci.org/encog/encog-java-workbench)

I am not developing the Encog Workbench further.  This includes bug fixes and enhancement. (Encog core remains under development) The Workbench code here is implemented using Java Swing, which has been [all but deprecated](https://vaadin.com/blog/clock-ticking-loudly-for-swing-and-swt-users) by Oracle. Like most Swing apps, the Encog workbench does not render that well on high resolution monitors. At this point, I do not have the time (nor see much of a purpose) to update the Encog Workbench.  If you are looking for a GUI-type platform, I suggest having a look at [RapidMiner](https://rapidminer.com/) or [Dataiku](https://www.dataiku.com/dss/editions/).  

Encog 3.4 workbench
===================

The workbench can be downloaded from the [core release page](https://github.com/encog/encog-java-core/releases)

To launch the workbench, double-click its executable JAR.  Or launch it with:

java -jar encog-workbench-3.4.0-SNAPSHOT-all

The following links will be helpful getting started with Encog.

To build an executable JAR for the workbench use:

gradle shadowJar

Getting Started:

http://www.heatonresearch.com/encog

Important Links:

http://www.heatonresearch.com/encog
