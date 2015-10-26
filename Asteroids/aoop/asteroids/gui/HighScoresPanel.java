package aoop.asteroids.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import aoop.asteroids.model.Participant;

public class HighScoresPanel extends JPanel {


	DefaultListModel listModel;
	ArrayList<Participant> participantsList = new ArrayList<Participant>();
	
	public HighScoresPanel(ArrayList<Participant> participants) {
		this.setLayout(new BorderLayout(400, 600));
		this.setBackground(Color.black);
		
		JLabel title = new JLabel("High Scores", SwingConstants.CENTER);
		title.setForeground(new Color(0,200,200));
		title.setBounds(100, 0, 600, 150);
		title.setFont(new Font("Impact", Font.BOLD, 100));
		this.add(title);
		
		this.listModel = new DefaultListModel();
		JList list = new JList(listModel);
		JTextField scores = new JTextField(600);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setColumnHeaderView(title);
		this.add(scrollPane);

        for(Participant p : participants){
        	listModel.addElement(p);
        	}
		}
	

	public DefaultListModel<Participant> getListModel() {
		return listModel;
	}

	public void setListModel(DefaultListModel<Participant> listModel) {
		this.listModel = listModel;
	}
	
	public void setParticipants(ArrayList<Participant> participants){
		for(Participant p : participants){
			this.participantsList.add(p);
		}

	    for(Participant p : participants){
	        this.listModel.addElement(p);
	    }
	}

	public ArrayList<Participant> getParticipantsList() {
		return participantsList;
	}

	
}
