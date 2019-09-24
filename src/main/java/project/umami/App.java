/*
Copyright 2019 Orchidware Studios LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package project.umami;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Parse Tree visualization
 * 
 * To compile and run: 
 * 		mvn clean compile javafx:run
 */
public class App extends Application 
{
	private static Scene scene;
	private int sceneWidth = 1750;
	private int sceneHeight = 1250;
	private HashMap<Integer, LocationNode> locations = 
		new HashMap<Integer, LocationNode>();
	
	private List<TreeTableNode> treeTableNodes = null;
	private float rowInterval = 0.0f;
	private float columnInterval = 0.0f;
	private HashMap<Integer, HashMap<Integer, Integer>> adjacencyMatrix =
		new HashMap<Integer, HashMap<Integer, Integer>>();
	
	private HashMap<Integer, TreeTableNode> lookupTable = 
		new HashMap<Integer, TreeTableNode>();

	Pane treePane = new Pane();	
	GridPane gridPane = new GridPane();
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		launch();
	}
	
	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		createModel();
		createMatrix();

		gridPane.setAlignment(Pos.TOP_LEFT);
		
		createMoleculeSelectionPanel();
		createTreePanel();
		
		gridPane.add(treePane, 0, 1, 2, 1);
		
		Scene scene = new Scene(
				gridPane, 
				sceneWidth, 
				sceneHeight);
		
		primaryStage.setTitle("SMILES Parse Tree Viewer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * 
	 * @param fxml
	 * @throws IOException
	 */
	static void setRoot(String fxml) throws IOException 
	{
		scene.setRoot(loadFXML(fxml));
	}

	/**
	 * 
	 * @param fxml
	 * @return
	 * @throws IOException
	 */
	private static Parent loadFXML(String fxml) throws IOException 
	{
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	/**
	 * 
	 */
	private void createMatrix()
	{
		for (TreeTableNode node : treeTableNodes)
		{
			HashMap<Integer, Integer> parent = adjacencyMatrix.get(node.getParentId());
			
			if (parent == null)
			{
				adjacencyMatrix.put(node.getParentId(), new HashMap<Integer, Integer>());
				parent = adjacencyMatrix.get(node.getParentId());				
			}
			
			parent.put(node.getChildId(), 1);			
		}
	}
	
	/**
	 * 
	 */
	private void createModel()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try 
		{
			InputStream fileInputStream = new FileInputStream("C:\\ProjectUmami\\data\\tree.json");
			treeTableNodes = Arrays.asList(mapper.readValue(fileInputStream, TreeTableNode[].class));
			fileInputStream.close();

			for (TreeTableNode treeTableNode : treeTableNodes) 
			{
				System.out.println(treeTableNode.toString());
				
				lookupTable.put(treeTableNode.getChildId(), treeTableNode);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		int previousLevel = -1;
		int numLevels = 0;
		int maxColumns = 0;

		int columns = 0;

		for (TreeTableNode node : treeTableNodes) 
		{
			numLevels = node.getLevel();

			if (previousLevel != numLevels) 
			{
				previousLevel = numLevels;

				if (columns > maxColumns) 
				{
					maxColumns = columns;
				}

				columns = 0;
			} 
			else 
			{
				columns++;
			}
		}		
		
		rowInterval = sceneHeight / (numLevels + 1);
		columnInterval = sceneWidth / (maxColumns + 1);
	} 
	
	/**
	 * 
	 */
	private void createTreePanel()
	{
		int border = 5;

		int previousLevel = -1;
		int currentLevel = 0;

		int column = 0;
		
		for (TreeTableNode node : treeTableNodes) 
		{
			currentLevel = node.getLevel();

			if (previousLevel != currentLevel) 
			{
				column = 0;

				previousLevel = currentLevel;
			}

			Group lineGroup = null;
			Group textGroup = null;
			Group textDataGroup = null;
			
			Rectangle r = null;
						
			// Internal nodes
			Color nodeColor = Color.rgb(0, 0, 200, 0.90);
						
			int myId = node.getChildId();
			HashMap<Integer, Integer> myChildren = adjacencyMatrix.get(myId);
			
			if (myChildren != null)
			{
				if (myChildren.keySet().size() == 1)
				{
					Set<Integer> children = myChildren.keySet();
					
					Iterator<Integer> it = children.iterator(); 
					
					if (it.hasNext())					
					{
						Integer childId = it.next();
						
						HashMap<Integer, Integer> childsChildren = adjacencyMatrix.get(childId);
							
						if (childsChildren == null)
						{
							// Node immediately above a leaf.
							nodeColor = Color.rgb(0, 200, 0, 1.0);									
						}	
					}
				}
			}
						
			float width = columnInterval * 0.75f; 
			float height = ((columnInterval > rowInterval) ? rowInterval : columnInterval) * .55f;
			float x = border + column * columnInterval;
			float y = border + currentLevel * rowInterval;

			locations.put(node.getChildId(), new LocationNode(x, y));

			r = new Rectangle();
			r.setX(x);
			r.setY(y);
			r.setWidth(width);
			r.setHeight(height);
			r.setArcWidth(10);
			r.setArcHeight(10);

			LocationNode parentLocation = locations.get(node.getParentId());

			Line line = new Line(
					x + width / 2.0f, 
					y, 
					parentLocation.getX() + width / 2.0f,
					parentLocation.getY() + height);
			
			line.setStrokeWidth(1.0f);

			lineGroup = new Group(line);

			Text text = new Text();
			text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
			text.setText(Integer.toString(node.getChildId()));
			text.setX(x + width * 0.05f);
			text.setY(y + height * 0.4f);
			text.setFill(Color.YELLOW);
			textGroup = new Group(text);
			
			Text textData = new Text();
			textData.setFont(Font.font("verdana", FontWeight.THIN, FontPosture.REGULAR, 10));
			textData.setText(node.getData());
			textData.setX(x + width * 0.05f);
			textData.setY(y + height * 0.75f);
			textData.setFill(Color.BEIGE);
			textDataGroup = new Group(textData);
			
			r.setStrokeWidth(2.0);	
			r.setStroke(Color.GRAY);
			
			if (node.getNumChildren() == 0 &&
				node.getChildId() != 0) 
			{
				if (node.getData().compareTo("<epsilon>") == 0)
				{
					// Epsilon node
					r.setFill(Color.rgb(25, 25, 25, 1.0));
				}				
				else
				{
					// Leaf node
					r.setFill(Color.rgb(255, 0, 0, 1.0));
				}
			} 
			else 
			{
				r.setFill(nodeColor); 
			}
			
			column++;

				
			if (r != null)
			{
				treePane.getChildren().add(r);
			}
						
			if (lineGroup != null)
			{
				treePane.getChildren().add(lineGroup);
			}

			if (textGroup != null)
			{
				treePane.getChildren().add(textGroup);
			}
			
			if (textDataGroup != null)
			{
				treePane.getChildren().add(textDataGroup);
			}

			if (lineGroup != null)
			{
				lineGroup.toBack();
			}	
		}
	}
	
	/**
	 * 
	 */
	private void createMoleculeSelectionPanel()
	{
		Group moleculeGroup = null;
		Group moleculeLabelGroup = null;
		
		ChoiceBox<String> moleculeChoiceBox = new ChoiceBox();
		moleculeChoiceBox.getItems().addAll(
				"Ethane",		
				"Ethene",			
				"Phenol",		
				"Benzalehyde",		
				"Cyclopropene",						
				"Canonical Cyclohexene",						
				"Dinitrogen",
				"Ciprofloxacin",		  
				"Canonical Ciprofloxacin",														 
				"Methyl isocyanate",														
				"Copper(II) sulfate",			
				"Vanillin",						
				"Melatonin",			
				"Flavopereirin",						
				"Nicotine",			
				"Oenanthotoxin",				
				"Pyrethrin II",			
				"Canonical Aflatoxin",		
				"Isomeric Glucose",
				"Bergenin",				
				"A pheromone of the Californian scale insect",							
				"(2S,5R)-Chalcogran",			
				"α-Thujone",			
				"Thiamine",	
				"Canonical Cephalostatin 1",
				"Isomeric Cephalostatin 1",		
				"Beta-carotene",
				"L-Alanine",
				"Caffeine",
				"Aspirin",
				"Isomeric Phenylalanine",
				
				"Canonical Phenylalanine",		
				"Atrazine",
				"Malonic acid",
				"Sodium stearate",
				"Clofenotane",
				"Morphine",
				"Progesterone",
				"Canonical Oxytocin",
				"Isomeric Oxytocin",
				"Acetaminophen",				
				"Acetylcholine",
				"Ibuprofen",
				"Canonical Androstenedione",
				"Isomeric Androstenedione",
				"Canonical Ascorbic acid",
				"Isomeric Ascorbic acid",
				"Canonical Aspartame",
				"Isomeric Aspartame",
				"Canonical Biotin",
				"Isomeric Biotin",
				
				"Canonical Chlorophyll",
				"Isomeric Chlorophyll",	
				"Canonical Cholecalciferol",
				"Isomeric Cholecalciferol",
				"Canonical Cholesterol",
				"Isomeric Cholesterol",
				"Canonical Codeine",
				"Isomeric Codeine",
				"Canonical Hydrocoritsone",
				"Isomeric Hydrocortisone",
				
				"Canonical Estradiol",
				"Isomeric Estradiol",
				"Clonazepam",
				"Haloperidol",
				"Canonical Testosterone",
				"Isomeric Testosterone",
				"Canonical Capsaicin",
				"Isomeric Capsaicin",
				"Canonical Tubocurarine",
				"Isomeric Tubocurarine",
				
				"Canonical Penicillin V",
				"Isomeric Penicillin V",
				"Canonical Lycopene",
				"Isomeric Lycopene",
				"Canonical Simvastatin",
				"Isomeric Simvastatin",
				"Amlodipine",
				"Canonical Empagliflozin",												 
				"Isomeric Empagliflozin",
				"Omeprazole",
				
				"Canonical Amoxicillin",
				"Isomeric Amoxicillin",
				"Canonical Raspberry ellagitannin",
				"Isomeric Raspberry ellagitannin",
				"Canonical Human Insulin",
				"Isomeric Human Insulin",
				"Canonical Hemoglobin alpha-chain fragments", 
				"Isomeric Hemoglobin alpha-chain fragments", 
				"Hydrogen Cyanide",
				"Formaldehyde",
				
				"Butanal", 
				"Canonical Protoheme IX", 
				"Copper;N-[morpholin-4-yl(phenyl)methyl]benzamide",
				"18-(2-Carboxyethyl)-20-(carboxymethyl)-12-ethenyl-7-ethyl-3,8,13,17-tetramethyl-17,18-dihydroporphyrin-22,24-diide-2-carboxylic acid;platinum(2+)", 
				"Canonical (2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-4-amino-2-[[2-[[(2S)-2-[[(2S)-2-amino-4-methylpentanoyl]amino]-4-methylpentanoyl]amino]acetyl]amino]-4-oxobutanoyl]amino]-3-methylbutanoyl]amino]-4-methylpentanoyl]amino]-3-methylbutanoyl]amino]-3-methylbutanoyl]amino]-3-methylbutanoyl]amino]-4-methylpentanoyl]amino]propanoyl]amino]-5-(diaminomethylideneamino)pentanoic acid",				
				"Isomeric (2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-2-[[(2S)-4-amino-2-[[2-[[(2S)-2-[[(2S)-2-amino-4-methylpentanoyl]amino]-4-methylpentanoyl]amino]acetyl]amino]-4-oxobutanoyl]amino]-3-methylbutanoyl]amino]-4-methylpentanoyl]amino]-3-methylbutanoyl]amino]-3-methylbutanoyl]amino]-3-methylbutanoyl]amino]-4-methylpentanoyl]amino]propanoyl]amino]-5-(diaminomethylideneamino)pentanoic acid",
				"Canonical Cellulose",
				"Isomeric Cellulose",
				"Canonical 2'-Deoxyadenosine",
				"Isomeric 2'-Deoxyadenosine",
				"Formaldehyde",
				"Butanal",
				"Phenylalanine",		
				"EGFR Inhibitor",
				"Canonical Kapanol",
				"Isomeric Kapanol",
				"2-Pentene",
				"2-Methyl propane",
				"2-Ethyl-2-pentane",				
				"Canonical Vitamin B12a hydrochloride, analytical standard",
				"Isomeric Vitamin B12a hydrochloride, analytical standard",						
				"Canonical Paclitaxel",
				"Isomeric Paclitaxel"); 

		
		moleculeGroup = new Group(moleculeChoiceBox);
		
		Text moleculeLabel = new Text("Molecule:"); 
		moleculeLabel.setStyle("-fx-font: normal bold 15px 'serif'");
		moleculeLabelGroup = new Group(moleculeLabel);		
		
		if (moleculeLabelGroup != null)
		{
			gridPane.add(moleculeLabelGroup, 0, 0);
		}
		
		if (moleculeGroup != null)
		{
			gridPane.add(moleculeGroup, 1, 0);
		}		
	}
}