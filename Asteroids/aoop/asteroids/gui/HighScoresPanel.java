package aoop.asteroids.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import aoop.asteroids.model.Participant;

public class HighScoresPanel extends JPanel {

	public HighScoresPanel() {
		this.setLayout(null);
		setBackground(Color.black);
		
		JLabel title = new JLabel("High Scores", SwingConstants.CENTER);
		title.setForeground(new Color(0,200,200));
		title.setBounds(100, 65, 600, 150);
		title.setFont(new Font("Impact", Font.BOLD, 100));
		this.add(title);
		
		EntityManagerFactory emf  = Persistence.createEntityManagerFactory("$objectdb/db/participantsTest.odb");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(new Participant(5,"Jared"));
        em.getTransaction().commit();
        Query query = em.createQuery("SELECT p FROM Participant p", Participant.class);
        List<Participant> participants = query.getResultList();
        int positionY = 100;
        for(Participant p : participants){
        	System.out.println(p);
//        	this.add(, new JLabel(p.toString(), SwingConstants.CENTER).setBounds(20, positionY, 100, 150));
        	positionY += 10;
        }
        em.close();
        emf.close();
	}

}
