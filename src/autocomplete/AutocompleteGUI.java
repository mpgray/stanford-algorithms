package autocomplete;

/**
 * Author: Alfredo Rodriguez && Michael Gray
 * Date: 3/5/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.BorderFactory;
import javax.swing.LayoutStyle;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import java.text.Collator;
import java.text.Normalizer;
import java.util.regex.Pattern;
//import java.text.Normalizer;
//import java.util.regex.Pattern;

import edu.princeton.cs.algs4.In;

public class AutocompleteGUI extends JFrame
{
	private static int DEF_WIDTH = 850;
	private static int DEF_HEIGHT = 400;
	private static String searchURL = "https://www.google.com/search?q=";

	// Display top k results
	private final int k;

	private boolean displayWeights = true;

	public AutocompleteGUI(String filename, int k)
	{
		this.k = k;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Autocomplete Me");
		setLocationRelativeTo(null);
		Container content = getContentPane();
		GroupLayout layout = new GroupLayout(content);
		content.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		final AutocompletePanel ap = new AutocompletePanel(filename);
		JButton searchButton = new JButton("Search Google");
		
		//searchButton.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
//		searchButton.addMouseListener(new MouseListener()
//		{
//
//			public void mousePressed(MouseEvent e)
//			{
//			}
//
//			public void mouseReleased(MouseEvent e)
//			{
//			}
//
//			public void mouseEntered(MouseEvent e)
//			{
//			}
//
//			public void mouseExited(MouseEvent e)
//			{
//			}
//
//			@Override public void mouseClicked(MouseEvent e)
//			{
//				searchOnline(ap.getSelectedText());
//			}
//		});

		JCheckBox checkbox = new JCheckBox("Show weights", null, displayWeights);

//		 checkbox.addMouseListener(new MouseListener() {
//
//                  public void mousePressed(MouseEvent e)
//                  {
//                  }
//
//                  public void mouseReleased(MouseEvent e)
//                  {
//
//                  }
//
//                  public void mouseEntered(MouseEvent e)
//                  {
//
//                  }
//
//                  public void mouseExited(MouseEvent e)
//                  {
//
//                  }
//
//                  @Override
//                  public void mouseClicked(MouseEvent e)
//                  {
//
//                  }
//               });

		checkbox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
					
					if ( checkbox.isSelected() )
					{
						displayWeights = true;
						ap.update();
					}
				
					else
					{
						displayWeights = false;
						ap.update();
					}
			}
		});
		JLabel textLabel = new JLabel("Search query:");
		// textLabel.setBorder(BorderFactory.createEmptyBorder(1, 4, 0, 0));
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(textLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE)
				.addComponent(ap, 0, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGroup(layout.createSequentialGroup()
								.addComponent(checkbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))));
		layout.setVerticalGroup(layout.createSequentialGroup().addGroup(
				layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(textLabel).addComponent(ap)
						.addComponent(searchButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(checkbox)));
		setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
		pack();
	}

	private class DiacriticInsensitiveString implements Comparable<DiacriticInsensitiveString>
	{
		private String s;
		private Collator c;

		public DiacriticInsensitiveString(String s)
		{
			this.s = s;
			c = Collator.getInstance();
			c.setStrength(Collator.PRIMARY);
		}

		public int compareTo(DiacriticInsensitiveString o)
		{
			return c.compare(this.s, o);
		}
	}

	private class AutocompletePanel extends JPanel
	{
		private final JTextField searchText;
		private Autocomplete auto;
		private String[] results = new String[k];
		private JList suggestions;
		private JScrollPane scrollPane;
		private JPanel suggestionsPanel;

		// Keep these two values in sync! - used to keep the listbox the same width as the textfield
		//private final int DEF_COLUMNS = 30;
		//private final String suggListLen = "<b>Harry Potter and the Deathly Hallows: Part 1 (2010)</b>";

		// not sure what suggListLen does???
		private final int DEF_COLUMNS = 45;
		private final String suggListLen = "<b>Harry Potter and the Deathly Hallows: Part 1 (2010)</b>";

		public AutocompletePanel(String filename)
		{
			super();

			// Read in the data
			In in = new In(filename);
			int N = Integer.parseInt(in.readLine());
			Term[] terms = new Term[N];
			Term[] termsDiacritics = new Term[N];
			for (int i = 0; i < N; i++)
			{
				String line = in.readLine();
				int tab = line.indexOf('\t');
				double weight = Double.parseDouble(line.substring(0, tab).trim());
				String query = line.substring(tab + 1);
				String deaccentQuery = Normalizer.normalize(query, Normalizer.Form.NFD);
				Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
				deaccentQuery = pattern.matcher(deaccentQuery).replaceAll("");
				terms[i] = new Term(deaccentQuery, weight);
				termsDiacritics[i] = new Term(query, weight);
				terms[i] = new Term(query, weight);
			}

			//Collator c = Collator.getInstance();
			//c.setStrength(Collator.PRIMARY);
			//Arrays.sort(termsDiacritics, c);

			// Create the autocomplete object
			auto = new Autocomplete(terms);

			GroupLayout layout = new GroupLayout(this);
			this.setLayout(layout);
			searchText = new JTextField(DEF_COLUMNS);
			searchText.setMaximumSize(
					new Dimension(searchText.getMaximumSize().width, searchText.getPreferredSize().height));
			searchText.getInputMap().put(KeyStroke.getKeyStroke("UP"), "none");
			searchText.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "none");
			searchText.addFocusListener(new FocusListener()
			{
				@Override public void focusGained(FocusEvent e)
				{
					int pos = searchText.getText().length();
					searchText.setCaretPosition(pos);
				}

				public void focusLost(FocusEvent e)
				{
				}
			});
			JPanel searchTextPanel = new JPanel();
			searchTextPanel.add(searchText);
			searchTextPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
			searchTextPanel.setLayout(new GridLayout(1, 1));
			suggestions = new JList(results);
			suggestions.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			suggestions.setVisible(false);
			suggestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			suggestions.setMaximumSize(
					new Dimension(searchText.getMaximumSize().width, suggestions.getPreferredSize().height));
			suggestions.setPrototypeCellValue(suggListLen);   // Set to make equal to the width of the textfield
			suggestions.setFont(suggestions.getFont().deriveFont(Font.PLAIN, 13));
			Action makeSelection = new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (!suggestions.isSelectionEmpty())
					{
						String selection = (String) suggestions.getSelectedValue();
						if (displayWeights)
							selection = selection.substring(0, selection.indexOf("<td width="));
						selection = selection.replaceAll("\\<.*?>", "");
						searchText.setText(selection);
						getSuggestions(selection);
					}
					searchOnline(searchText.getText());
				}
			};
			Action moveSelectionUp = new AbstractAction()
			{
				@Override public void actionPerformed(ActionEvent e)
				{
					if (suggestions.getSelectedIndex() >= 0)
					{
						suggestions.requestFocusInWindow();
						suggestions.setSelectedIndex(suggestions.getSelectedIndex() - 1);
					}
				}
			};
			Action moveSelectionDown = new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (suggestions.getSelectedIndex() != results.length)
					{
						suggestions.requestFocusInWindow();
						suggestions.setSelectedIndex(suggestions.getSelectedIndex() + 1);
					}
				}
			};
			Action moveSelectionUpFocused = new AbstractAction()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (suggestions.getSelectedIndex() == 0)
					{
						suggestions.clearSelection();
						searchText.requestFocusInWindow();
						//int pos = searchText.getText().length();
						//searchText.select(pos, pos);
						searchText.setSelectionEnd(0);
					} else if (suggestions.getSelectedIndex() >= 0)
					{
						suggestions.setSelectedIndex(suggestions.getSelectedIndex() - 1);
					}
				}
			};
			suggestions.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke("UP"), "moveSelectionUp");
			suggestions.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke("DOWN"), "moveSelectionDown");
			suggestions.getActionMap().put("moveSelectionUp", moveSelectionUp);
			suggestions.getActionMap().put("moveSelectionDown", moveSelectionDown);
			suggestions.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "makeSelection");
			suggestions.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveSelectionUpFocused");
			suggestions.getActionMap().put("moveSelectionUpFocused", moveSelectionUpFocused);
			suggestions.getActionMap().put("makeSelection", makeSelection);
			//            suggestions.setFixedCellHeight(40);
			suggestions.setFixedCellHeight(20);

			suggestionsPanel = new JPanel();

			scrollPane = new JScrollPane(suggestions);
			scrollPane.setVisible(false);
			int prefBarWidth = scrollPane.getVerticalScrollBar().getPreferredSize().width;
			suggestions.setPreferredSize(new Dimension(searchText.getPreferredSize().width, 0));
			scrollPane.setAutoscrolls(true);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

			// TODO: Get rid of the magic numbers
			suggestionsPanel
					.setPreferredSize(new Dimension(searchText.getPreferredSize().width + 2 * prefBarWidth, 1000));
			suggestionsPanel.setMaximumSize(new Dimension(searchText.getMaximumSize().width + 2 * prefBarWidth, 1000));
			suggestionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
			suggestionsPanel.add(scrollPane);
			suggestionsPanel.setLayout(new GridLayout(1, 1));
			this.setPreferredSize(new Dimension(searchText.getPreferredSize().width + 2 * prefBarWidth,
					this.getPreferredSize().height));
			this.setMaximumSize(new Dimension(searchText.getMaximumSize().width + 2 * prefBarWidth,
					searchText.getMaximumSize().height + suggestionsPanel.getMaximumSize().height));
			suggestions.addMouseListener(new MouseAdapter()
			{
				@Override public void mouseClicked(MouseEvent mouseEvent)
				{
					JList theList = (JList) mouseEvent.getSource();
					if (mouseEvent.getClickCount() >= 1)
					{
						int index = theList.locationToIndex(mouseEvent.getPoint());
						if (index >= 0)
						{
							String selection = getSelectedText();
							searchText.setText(selection);
							String text = searchText.getText();
							getSuggestions(text);
							searchOnline(searchText.getText());
						}
					}
				}

				@Override public void mouseEntered(MouseEvent mouseEvent)
				{
					JList theList = (JList) mouseEvent.getSource();
					int index = theList.locationToIndex(mouseEvent.getPoint());
					theList.requestFocusInWindow();
					theList.setSelectedIndex(index);
				}

				@Override public void mouseExited(MouseEvent mouseEvent)
				{
					suggestions.clearSelection();
					searchText.requestFocusInWindow();
				}
			});
			suggestions.addMouseMotionListener(new MouseInputAdapter()
			{
				@Override public void mouseClicked(MouseEvent mouseEvent)
				{
					JList theList = (JList) mouseEvent.getSource();
					if (mouseEvent.getClickCount() >= 1)
					{
						int index = theList.locationToIndex(mouseEvent.getPoint());
						if (index >= 0)
						{
							String selection = getSelectedText();
							searchText.setText(selection);
							String text = searchText.getText();
							getSuggestions(text);
							searchOnline(searchText.getText());
						}
					}
				}

				@Override public void mouseEntered(MouseEvent mouseEvent)
				{
					JList theList = (JList) mouseEvent.getSource();
					int index = theList.locationToIndex(mouseEvent.getPoint());
					theList.requestFocusInWindow();
					theList.setSelectedIndex(index);
				}

				@Override public void mouseMoved(MouseEvent mouseEvent)
				{
					JList theList = (JList) mouseEvent.getSource();
					int index = theList.locationToIndex(mouseEvent.getPoint());
					theList.requestFocusInWindow();
					theList.setSelectedIndex(index);
				}
			});
			searchText.getDocument().addDocumentListener(new DocumentListener()
			{
				public void insertUpdate(DocumentEvent e)
				{
					changedUpdate(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					changedUpdate(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					String text = searchText.getText();
					getSuggestions(text);

					updateListSize();
				}
			});
			searchText.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String selection = getSelectedText();
					searchText.setText(selection);
					getSuggestions(selection);
					searchOnline(searchText.getText());
				}
			});
			layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(
					layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addComponent(searchTextPanel, 0, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(suggestionsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE))

			);
			layout.setVerticalGroup(
					layout.createSequentialGroup().addComponent(searchTextPanel).addComponent(suggestionsPanel));
		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
		}

		private void updateListSize()
		{
			int rows = k;
			if (suggestions.getModel().getSize() < k)
				rows = suggestions.getModel().getSize();
			suggestions.setPreferredSize(
					new Dimension(searchText.getPreferredSize().width, rows * suggestions.getFixedCellHeight()));
		}

		public void update()
		{
			getSuggestions(searchText.getText());
		}

		/**
		 * Makes a call to the implementation of Autocomplete to get suggestions
		 * for the currently entered text.
		 *
		 * @param text string to search for
		 */
		public void getSuggestions(String text)
		{
			if (text.equals(""))
			{
				suggestions.setListData(new String[0]);
				suggestions.clearSelection();
				suggestions.setVisible(false);
				scrollPane.setVisible(false);
			} else
			{
				int textLen = text.length();

				// get all matching terms
				Term[] allResults = auto.allMatches(text);
				if (allResults == null)
				{
					throw new NullPointerException("allMatches() returned null");
				}

				results = new String[Math.min(k, allResults.length)];
				if (Math.min(k, allResults.length) > 0)
				{
					for (int i = 0; i < results.length; i++)
					{

						// A bit of a hack to get the Term's query string and weight from toString()
						String next = allResults[i].toString();
						if (allResults == null)
						{
							throw new NullPointerException("allMatches() returned an array with a null entry");
						}
						int tab = next.indexOf('\t');
						if (tab < 0)
						{
							throw new RuntimeException(
									"allMatches() returned an array with an entry without a tab: '" + next + "'");
						}
						String weight = next.substring(0, tab).trim();
						String query = next.substring(tab);

						// truncate length if needed
						if (query.length() > suggListLen.length())
							query = query.substring(0, suggListLen.length());

						results[i] = "<html><table width=\"" + searchText.getPreferredSize().width + "\">"
								+ "<tr><td align=left>" + query.substring(0, textLen) + "<b>" + query.substring(textLen)
								+ "</b>";
						if (displayWeights)
						{
							results[i] +=
									"<td width=\"10%\" align=right><font size=-1><span id=\"weight\" style=\"float:right;color:gray\">"
											+ weight + "</font>";
						}
						results[i] += "</table></html>";
					}
					suggestions.setListData(results);
					suggestions.setVisible(true);
					scrollPane.setVisible(true);
					// Pressing enter automatically selects the first one.
					// if nothing has been entered
					//suggestions.setSelectedIndex(0);
				} else
				{
					// No suggestions
					suggestions.setListData(new String[0]);
					suggestions.clearSelection();
					suggestions.setVisible(false);
					scrollPane.setVisible(false);
				}
			}
		}

		public String getSelectedText()
		{
			if (!suggestions.isSelectionEmpty())
			{
				String selection = (String) suggestions.getSelectedValue();
				if (displayWeights)
					selection = selection.substring(0, selection.indexOf("<td width="));
				selection = selection.replaceAll("\\<.*?>", "");
				return selection;
			} else
				return getSearchText();
		}

		public String getSearchText()
		{
			return searchText.getText();
		}
	}

	/**
	 * Creates a URI from the user-defined string and searches the web with the
	 * selected search engine
	 * Opens the default web browser (or a new tab if it is already open)
	 *
	 * @param s string to search online for
	 */
	private void searchOnline(String s)
	{
		URI searchAddress = null;
		try
		{
			URI tempAddress = new URI(searchURL + s.trim().replace(' ', '+'));
			searchAddress = new URI(tempAddress.toASCIIString()); // Hack to handle Unicode
		} catch (URISyntaxException e2)
		{
			e2.printStackTrace();
			return;
		}
		try
		{
			Desktop.getDesktop().browse(searchAddress);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

    public static void main(String[] args) {
        final String filename = args[0];
        final int k = Integer.parseInt(args[1]);
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        new AutocompleteGUI(filename, k).setVisible(true);
                    }
                });
    }
}