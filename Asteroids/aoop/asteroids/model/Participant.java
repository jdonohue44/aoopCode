package aoop.asteroids.model;

import java.io.Serializable;
import javax.persistence.*;
 
@Entity
public class Participant implements Serializable {
    private static final long serialVersionUID = 1L;
 
    @Id @GeneratedValue
    private long id;
 
    private int highScore = 0;
    private String nickname;
 
    public Participant() {
    }
 
    public Participant(int highScore, String nickname) {
    	this.highScore = highScore;
    	this.nickname = nickname;
    }
 
    public Long getId() {
        return id;
    }

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String toString(){
		return "(Player: "+this.nickname +", high score: " + this.highScore +")";
	}
    
    
}
