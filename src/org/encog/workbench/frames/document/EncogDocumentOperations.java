package org.encog.workbench.frames.document;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.encog.EncogError;
import org.encog.bot.spider.SpiderOptions;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.PropertyData;
import org.encog.neural.data.TextData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.Network;
import org.encog.neural.networks.layers.FeedforwardLayer;
import org.encog.neural.persist.EncogPersistedObject;
import org.encog.parse.ParseTemplate;
import org.encog.workbench.EncogWorkBench;
import org.encog.workbench.dialogs.EditSpiderOptions;
import org.encog.workbench.dialogs.about.AboutEncog;
import org.encog.workbench.dialogs.select.SelectDialog;
import org.encog.workbench.dialogs.select.SelectItem;
import org.encog.workbench.frames.BrowserFrame;
import org.encog.workbench.frames.Conversation;
import org.encog.workbench.frames.NetworkFrame;
import org.encog.workbench.frames.NetworkQueryFrame;
import org.encog.workbench.frames.ParseTemplateFrame;
import org.encog.workbench.frames.PropertyDataFrame;
import org.encog.workbench.frames.TextEditorFrame;
import org.encog.workbench.frames.TrainingDataFrame;
import org.encog.workbench.frames.manager.EncogCommonFrame;
import org.encog.workbench.frames.visualize.NetworkVisualizeFrame;
import org.encog.workbench.process.ImportExport;
import org.encog.workbench.process.generate.CodeGeneration;
import org.encog.workbench.util.ExtensionFilter;
import org.encog.workbench.util.NeuralConst;

public class EncogDocumentOperations {

	private EncogDocumentFrame owner;
	
	private int trainingCount = 1;
	private int networkCount = 1;
	private int spiderCount = 1;
	private int parseCount = 1;
	private int optionsCount = 1;
	private int textCount = 1;
	
	public EncogDocumentOperations(EncogDocumentFrame owner) {
		this.owner = owner;
	}
	
	public void openItem(final Object item) {
		if (item instanceof NeuralDataSet) {
			final BasicNeuralDataSet nds = (BasicNeuralDataSet) item;
			if (owner.getSubwindows().checkBeforeOpen(nds, TrainingDataFrame.class)) {
				final TrainingDataFrame frame = new TrainingDataFrame(
						(BasicNeuralDataSet) item);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} else if (item instanceof Network) {

			final BasicNetwork net = (BasicNetwork) item;
			if (owner.getSubwindows().checkBeforeOpen(net, TrainingDataFrame.class)) {
				final NetworkFrame frame = new NetworkFrame(net);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} else if( item instanceof TextData ) {
			TextData text = (TextData)item;
			if (owner.getSubwindows().checkBeforeOpen(text, TextData.class)) {
				final TextEditorFrame frame = new TextEditorFrame(text);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		}else if( item instanceof PropertyData  ) {
			PropertyData prop = (PropertyData)item;
			if (owner.getSubwindows().checkBeforeOpen(prop, PropertyData.class)) {
				final PropertyDataFrame frame = new PropertyDataFrame(prop);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		} else if( item instanceof ParseTemplate  ) {
			ParseTemplate data = (ParseTemplate)item;
			if (owner.getSubwindows().checkBeforeOpen(data, ParseTemplate.class)) {
				final ParseTemplateFrame frame = new ParseTemplateFrame(data);
				frame.setVisible(true);
				owner.getSubwindows().add(frame);
			}
		}
		else if( item instanceof SpiderOptions )
		{
			EditSpiderOptions dialog = new EditSpiderOptions(owner);
			SpiderOptions so = (SpiderOptions)item;
			
			dialog.setObjectName(so.getName());
			dialog.setObjectDescription(so.getDescription());
			dialog.setTimeout(so.getTimeout());		
			dialog.setMaxDepth(so.getMaxDepth());
			dialog.setUserAgent(so.getUserAgent());
			dialog.setCorePoolSize(so.getCorePoolSize());
			dialog.setPoolSize(so.getMaximumPoolSize());
			dialog.setKeepAlive(so.getKeepAliveTime());
			dialog.setDbDriver(so.getDbClass());
			dialog.setDbPWD(so.getDbPWD());
			dialog.setDbUID(so.getDbUID());
			dialog.setDbURL(so.getDbURL());
			dialog.setWorkload(so.getWorkloadManager());
			if( so.getFilter().size()>0 )
			{
				dialog.setFilter(so.getFilter().get(0));
			}
			dialog.setStartup(so.getStartup());
			
			if(dialog.process())
			{
				so.setName(dialog.getObjectName());
				so.setDescription(dialog.getObjectDescription());
				so.setTimeout(dialog.getTimeout());		
				so.setMaxDepth(dialog.getMaxDepth());
				so.setUserAgent(dialog.getUserAgent());
				so.setCorePoolSize(dialog.getCorePoolSize());
				so.setMaximumPoolSize(dialog.getPoolSize());
				so.setKeepAliveTime(dialog.getKeepAlive());
				so.setDbClass(dialog.getDbDriver());
				so.setDbPWD(dialog.getDbPWD());
				so.setDbUID(dialog.getDbUID());
				so.setDbURL(dialog.getDbURL());
				so.setWorkloadManager(dialog.getWorkload());
				so.getFilter().clear();
				if( dialog.getFilter()!=null && dialog.getFilter().length()>0 )
				{
					so.getFilter().add(dialog.getFilter());
				}
				so.setStartup(dialog.getStartup());
			}
		}
	}

	public void performEditCopy() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.copy();
		}

	}

	public void performEditCut() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.cut();
		}
	}

	public void performEditPaste() {
		final Frame frame = EncogWorkBench.getCurrentFocus();
		if (frame instanceof EncogCommonFrame) {
			final EncogCommonFrame ecf = (EncogCommonFrame) frame;
			ecf.paste();
		}

	}

	public void performExport(final Object obj) {
		ImportExport.performExport(obj);
	}

	public void performFileClose() {
		if (!checkSave()) {
			return;
		}
		EncogWorkBench.getInstance().close();
	}

	public void performFileOpen() {
		try {
			if (!checkSave()) {
				return;
			}

			final JFileChooser fc = new JFileChooser();
			fc.addChoosableFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			final int result = fc.showOpenDialog(owner);
			if (result == JFileChooser.APPROVE_OPTION) {
				EncogWorkBench.load(fc.getSelectedFile().getAbsolutePath());
			}
		} catch (final EncogError e) {
			JOptionPane.showMessageDialog(owner, e.getMessage(),
					"Can't Open File", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void performFileSave() {
		try {
			if (EncogWorkBench.getInstance().getCurrentFileName() == null) {
				performFileSaveAs();
			} else {
				EncogWorkBench.getInstance().getCurrentFile().save(
						EncogWorkBench.getInstance().getCurrentFileName());
			}
		} catch (final EncogError e) {
			JOptionPane.showMessageDialog(owner, e.getMessage(),
					"Can't Open File", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void performFileSaveAs() {
		try {
			final JFileChooser fc = new JFileChooser();
			fc.setFileFilter(EncogDocumentFrame.ENCOG_FILTER);
			final int result = fc.showSaveDialog(owner);

			if (result == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				// no extension
				if (ExtensionFilter.getExtension(file) == null) {
					file = new File(file.getPath() + ".eg");
				}
				// wrong extension
				else if (ExtensionFilter.getExtension(file).compareTo("eg") != 0) {

					if (JOptionPane
							.showConfirmDialog(
									owner,
									"Encog files are usually stored with the .eg extension. \nDo you wish to save with the name you specified?",
									"Warning", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
				}

				// file doesn't exist yet
				if (file.exists()) {
					final int response = JOptionPane.showConfirmDialog(null,
							"Overwrite existing file?", "Confirm Overwrite",
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}

				EncogWorkBench.save(file.getAbsolutePath());
			}
		} catch (final EncogError e) {
			JOptionPane.showMessageDialog(owner, e.getMessage(),
					"Can't Save File", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void performGenerateCode() {
		final CodeGeneration code = new CodeGeneration();
		code.processCodeGeneration();

	}

	public void performImport(final Object obj) {
		ImportExport.performImport(obj);
	}

	public void performNetworkQuery() {
		final BasicNetwork item = (BasicNetwork) owner.getContents().getSelectedValue();

		if (owner.getSubwindows().checkBeforeOpen(item, NetworkQueryFrame.class)) {
			final NetworkQueryFrame frame = new NetworkQueryFrame(item);
			frame.setVisible(true);
			owner.getSubwindows().add(frame);
		}

	}

	public void performNetworkVisualize() {
		final BasicNetwork item = (BasicNetwork) this.owner.getContents()
				.getSelectedValue();

		if (owner.getSubwindows().checkBeforeOpen(item, NetworkVisualizeFrame.class)) {
			final NetworkVisualizeFrame frame = new NetworkVisualizeFrame(item);
			frame.setVisible(true);
			owner.getSubwindows().add(frame);
		}
	}

	public void performObjectsCreate() {

		SelectItem itemTraining, itemNetwork, itemTemplate, itemSpider, itemOptions, itemText;
		final List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(itemNetwork = new SelectItem("Neural Network"));
		list.add(itemTemplate = new SelectItem("Parser Template"));
		list.add(itemOptions = new SelectItem("Property Data"));
		list.add(itemSpider = new SelectItem("Spider Options"));
		list.add(itemText = new SelectItem("Text"));
		list.add(itemTraining = new SelectItem("Training Data"));
				
		final SelectDialog dialog = new SelectDialog(owner, list);
		if(! dialog.process() )
			return;
		
		final SelectItem result = dialog.getSelected();

		if (result == itemNetwork) {
			final BasicNetwork network = new BasicNetwork();
			network.addLayer(new FeedforwardLayer(2));
			network.addLayer(new FeedforwardLayer(3));
			network.addLayer(new FeedforwardLayer(1));
			network.reset();
			network.setName("network-" + this.networkCount++);
			network.setDescription("A neural network");
			EncogWorkBench.getInstance().getCurrentFile().add(network);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if (result == itemTraining) {
			final BasicNeuralDataSet trainingData = new BasicNeuralDataSet(
					NeuralConst.XOR_INPUT, NeuralConst.XOR_IDEAL);

			trainingData.setName("data-" + this.trainingCount++);
			trainingData.setDescription("Training data");
			EncogWorkBench.getInstance().getCurrentFile().add(trainingData);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if( result == itemSpider ) {
			final SpiderOptions options = new SpiderOptions();
			options.setName("spider-" + this.spiderCount++);
			options.setDescription("Spider options");
			EncogWorkBench.getInstance().getCurrentFile().add(options);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if( result == itemTemplate ) {
			final ParseTemplate template = new ParseTemplate();
			template.setName("parse-" + this.parseCount++);
			template.setDescription("A parse template");
			EncogWorkBench.getInstance().getCurrentFile().add(template);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if(result == itemText)
		{
			final TextData text = new TextData();
			text.setName("text-" + this.textCount++);
			text.setDescription("A text file");
			EncogWorkBench.getInstance().getCurrentFile().add(text);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		} else if( result == itemOptions )
		{
			final PropertyData prop = new PropertyData();
			prop.setName("properties-" + this.optionsCount++);
			prop.setDescription("Some property data");
			EncogWorkBench.getInstance().getCurrentFile().add(prop);
			EncogWorkBench.getInstance().getMainWindow().redraw();
		}
	}
	public void performObjectsDelete() {
		final Object object = owner.getContents().getSelectedValue();
		if (object != null) {
			if (owner.getSubwindows().find((EncogPersistedObject) object) != null) {
				EncogWorkBench.displayError("Can't Delete Object",
						"This object can not be deleted while it is open.");
				return;
			}

			if (JOptionPane.showConfirmDialog(owner,
					"Are you sure you want to delete this object?", "Warning",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				EncogWorkBench.getInstance().getCurrentFile().getList().remove(
						object);
				EncogWorkBench.getInstance().getMainWindow().redraw();
			}
		}
	}

	
	public void performBrowse() {
		BrowserFrame browse = new BrowserFrame();
		browse.setVisible(true);
	}

	public void performChat() {
		String you = System.getProperty("user.name");
		if(you==null)
			you = "You";
		Conversation conv = new Conversation("Conversation",you,"Encog");
		conv.setVisible(true);
		
	}

	public void performHelpAbout() {
		AboutEncog dialog = new AboutEncog();
		dialog.process();		
	}
	
	boolean checkSave() {
		final String currentFileName = EncogWorkBench.getInstance()
				.getCurrentFileName();

		if (currentFileName != null
				|| EncogWorkBench.getInstance().getCurrentFile().getList()
						.size() > 0) {
			final String f = currentFileName != null ? currentFileName
					: "Untitled";
			final int response = JOptionPane.showConfirmDialog(null, "Save "
					+ f + ", first?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				performFileSave();
				return true;
			} else if (response == JOptionPane.NO_OPTION) {
				return true;
			} else {
				return false;
			}

		}

		return true;
	}

}
