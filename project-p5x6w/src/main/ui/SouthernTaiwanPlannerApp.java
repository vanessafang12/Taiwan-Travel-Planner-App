package ui;

import persistence.JsonReader;
import persistence.JsonWriter;
import model.Location;
import model.Rating;
import model.Recommendation;
import model.Requirement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


import java.awt.*;



//A Travel Planner that allows user to generate a location based on their requirement,
//view location they already visit, add the location they visited by themselves, and
//rate this app after using the app
//Part of the code that relates to JSON will be from the sample 
//Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class SouthernTaiwanPlannerApp extends JFrame {

    private static final String Recommendation_JSON_STORE = "./data/recommendation.json";
    private static final String Rating_JSON_STORE = "./data/rating.json";

    private Recommendation recommendation;
    private Rating rating;
    private JsonWriter jsonWriterRecommendation;
    private JsonReader jsonReaderRecommendation;
    private JsonWriter jsonWriterRating;
    private JsonReader jsonReaderRating;
    
    private JButton meun;
    private JButton newLocation;
    private JButton viewLocation;
    private JButton addLocation;
    private JButton viewRating;
    private JButton save;
    private JButton load;
    private JButton exit;
    private JButton restart;

    private JTextArea instruction; 
    private JPanel mainPanel;
    private JLabel mrtImageLabel;
    private List<JButton> listOfButtons;
  
     //EFFECTS: Run the application is programRunning is true, and set up the ui for the app
    public SouthernTaiwanPlannerApp() throws FileNotFoundException {
        uiSetup();
        //Add Action to the button
        meun.addActionListener(e -> { 
            cleanInstruction(); 
            introduction(); 
        }); 
        buttonSetupLocation();
        viewRating.addActionListener(e -> { 
            cleanInstruction(); 
            instruction.append("Current Rating: " + rating.getRating() + "\n");
        });
        buttonSetupJson();
        restart.addActionListener(e -> {
            cleanInstruction(); 
            removeLocations();
        });
        exit.addActionListener(e -> System.exit(0));

        introduction();
        setVisible(true);
    }

    //MODIFIES: THIS
    //EFFECTS: apply action to location button
    private void buttonSetupLocation() {
        newLocation.addActionListener(e -> {
            cleanInstruction(); 
            addNewRequirement();
        });
        viewLocation.addActionListener(e -> {
            cleanInstruction(); 
            viewAlreadyVisitList();
        });
        addLocation.addActionListener(e -> {
            cleanInstruction(); 
            addLocation();
        });
    } 

    //MODIFIES: THIS
    //EFFECTS: apply action to json related button (save and load)
    private void buttonSetupJson() {
        save.addActionListener(e -> {
            cleanInstruction(); 
            savePreference();
        });
        load.addActionListener(e -> {
            cleanInstruction(); 
            loadHistory();
        });
    }
 

    //MODIFIES: THIS
    //EFFECTS: set up the basic setting for the ui 
    private void uiSetup() {
        //Set the basic ui 
        listOfButtons = new ArrayList<>();
        setTitle("Southern Taiwan Trip Planner");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUp();

        //set the instruction output 
        instruction = new JTextArea();
        instruction.setEditable(false);
        instruction.setBackground(new Color(200, 221, 230));
        add(new JScrollPane(instruction), BorderLayout.CENTER);

        //set the button
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));

        setButton();
        for (JButton b : listOfButtons) {
            buttonPanel.add(b);
        }
        mainPanel.add(buttonPanel, BorderLayout.NORTH); 

        add(mainPanel, BorderLayout.NORTH);
    }

    //MODIFIES: THIS
    //EFFECTS: Apply new button to each JButton
    private void setButton() {
        meun = new JButton("Main Menu");
        newLocation = new JButton("Generate New Location");
        viewLocation = new JButton("View Visited Locations");
        addLocation = new JButton("Add New Location");
        viewRating = new JButton("View Current Ratings");
        save = new JButton("Save Application");
        load = new JButton("Load Past Data");
        restart = new JButton("Clean visited list");
        exit = new JButton("Exit Application");
        listOfButtons.add(meun);
        listOfButtons.add(newLocation);
        listOfButtons.add(viewLocation);
        listOfButtons.add(addLocation);
        listOfButtons.add(viewRating);
        listOfButtons.add(save);
        listOfButtons.add(load);
        listOfButtons.add(restart);
        listOfButtons.add(exit); 
    }

    //MODIFIES: THIS
    //EFFECTS: Clean the current panel 
    private void cleanInstruction() {
        instruction.setText("");
        removeMRT();
    } 

    //MODIFIES: This
    //EFFECTS: The introduction that appears in the start of the program 
    private void introduction() {
        instruction.append("Welcome to Southern Taiwan Trip Planner App!\n" 
                + "Feel free to play around the app, add a location you visited,\n"
                + "generate a location, or view the location you visited");

        JLabel imageLabel = new JLabel();
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/photo/taiwanPic.jpg"));
            Image image = imageIcon.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        } catch (Exception e) {
            instruction.append("Failed to load the image.\n");
        }
        add(imageLabel, BorderLayout.SOUTH);
    }

    //MODIFIES: THIS
    //EFFECTS: display the mrt for taiwan
    private void travelGuide() {
        mrtImageLabel = new JLabel();
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/photo/MRT.png"));
            Image image = imageIcon.getImage().getScaledInstance(200, 450, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            mrtImageLabel.setIcon(imageIcon);
        } catch (Exception e) {
            instruction.append("Failed to load the image.\n");
        }
        add(mrtImageLabel, BorderLayout.EAST);  
        revalidate();
        repaint();  
    } 

    //MODIFIES: THIS
    //EFFECTS: remove MRT image from the ui 
    private void removeMRT() {
        if (mrtImageLabel != null) {
            remove(mrtImageLabel); 
            mrtImageLabel = null; 
            revalidate();
            repaint();
        }
    }

    //MODIFIES: THIS
    //EFFECTS: Add a new requirement city, type of location, and estimated spending, and print out the location 
    //ask the user to adjust the input requirement if the location resuit in null
    private void addNewRequirement() {
        String city = JOptionPane.showInputDialog(this, city());

        String type = JOptionPane.showInputDialog(this, type());

        String spending = JOptionPane.showInputDialog(this, 
                "Please provide me your estimated spending per person (NTD)");
        if (spending == null) {
            return;
        }

        try {
            int spendingInput = Integer.parseInt(spending.trim());
            Requirement requirement = new Requirement(city, type, spendingInput); 
            String location = recommendation.checkLocation(requirement);
            if (location == null) {
                instruction.append("\nSORRY, NO MATCHING LOCATION FOUND");
            } else {
                instruction.append("\nYOU SHOULD VISIT: " + location + "\n");
                instruction.append("\nThis is the MRT map, please google it online for better quality!!");
                travelGuide();
                rating();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid spending amount, Please enter a number.");
        } 
    }

    //MODIFIES: THIS
    //Check rather or not the using wants to save this location into the app file 
    private void savePreference() {
        saveRating();
        saveRecommendation();
        instruction.append("Successfully Saved!");
    }

    //MODIFIES: THIS
    //Choose rather or not to load the previous state of the problem. 
    private void loadHistory() {
        loadRecommendation();
        loadRating();
        instruction.append("Successfully Loaded!");
    }


    //MODIFIES: THIS
    //EFFECTS: View list of location name that is already visited
    //return "You haven't start your adventure😱" if listOfLocation is empty 
    private void viewAlreadyVisitList() {
        List<String> listOfLocation = recommendation.getAlreadyList();
        if (listOfLocation == null) {
            instruction.append("You haven't start your adventure😱");
        } else {
            instruction.append("You went to: \n");
            for (String location : listOfLocation) {
                instruction.append(location + "\n");
            }
        } 
    } 

    //MODIFIES: THIS
    //EFFECTS: Remove all location from the list
    private void removeLocations() {
        recommendation = new Recommendation();
        jsonWriterRecommendation = new JsonWriter(Recommendation_JSON_STORE);
        jsonReaderRecommendation = new JsonReader(Recommendation_JSON_STORE);
        instruction.append("Successfully reset location added");
    }

    //MODIFIES: THIS
    //EFFECTS: Add a location that the user already visited that's not generate by this app
    private void addLocation() {
        String name = JOptionPane.showInputDialog(this, "Please provide me name of this location");
        String city1 = JOptionPane.showInputDialog(this, city());
        String type1 = JOptionPane.showInputDialog(this, type());

        String spending1 = JOptionPane.showInputDialog(this, 
                "Please provide me your estimated spending per person (NTD)");
        if (spending1 == null) {
            return;
        }

        try {
            int spendingInput1 = Integer.parseInt(spending1.trim());
            Location location = new Location(name, city1, type1, spendingInput1);
            recommendation.visitedLocation(location); 
            instruction.append("Successfully Added!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid spending amount, Please enter a number.");
        }
    } 

    //REQUIRES: rating to be in between [1,10]
    //MODIFIES: THIS
    //EFFECTS: Ask the user to rate the app out of 10
    private void rating() {
        String scoreInput = JOptionPane.showInputDialog(this, "Please rate this location out of 10: ");
        if (scoreInput != null) {
            try {
                int score = Integer.parseInt(scoreInput.trim());
                rating.changeRating(score);
                instruction.append("Thank you for your feedback~~");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid rating, please enter a number.");
            }
        }
    } 

    //EFFECTS: set up the basic requirement to start the program
    private void setUp() {
        recommendation = new Recommendation();
        rating = new Rating(); 
        jsonWriterRecommendation = new JsonWriter(Recommendation_JSON_STORE);
        jsonReaderRecommendation = new JsonReader(Recommendation_JSON_STORE);

        jsonWriterRating = new JsonWriter(Rating_JSON_STORE);
        jsonReaderRating = new JsonReader(Rating_JSON_STORE);
    } 

    // EFFECTS: saves the recommendation (already visited location) to file
    private void saveRecommendation() {
        try {
            jsonWriterRecommendation.open();
            jsonWriterRecommendation.write(recommendation);
            jsonWriterRecommendation.close();
        } catch (FileNotFoundException e) {
            instruction.append("You haven't input any data yet.");
        }
    }

    // EFFECTS: saves the rating to file
    private void saveRating() {
        try {
            jsonWriterRating.open();
            jsonWriterRating.writeRating(rating);
            jsonWriterRating.close(); 
        } catch (FileNotFoundException e) {
            instruction.append("You haven't input any data yet.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads recommendation (already visited location) from file
    private void loadRecommendation() {
        try {
            recommendation = jsonReaderRecommendation.read();
        } catch (IOException e) {
            instruction.append("Nothing has been saved before.");
            recommendation = new Recommendation();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads rating from file
    private void loadRating() {
        try {
            rating = jsonReaderRating.check();
        } catch (IOException e) {
            instruction.append("Nothing has been saved before.");
        }
    }


    //EFFECTS: return city instruction 
    private String city() {
        return "Please provide me the city of the location:\n"
                + "Options: Taipei/NewTaipei";
    }

    //EFFECTS: return type instruction
    private String type() {
        return "Please provide me the type of location:" 
                 + "\nOptions: Landmark/Historical Landmark/Park/Beach/Museum/Shopping Center/Night Market";
    }
} 

